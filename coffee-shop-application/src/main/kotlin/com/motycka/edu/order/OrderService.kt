package com.motycka.edu.order

import com.motycka.edu.customer.InternalCustomerService
import com.motycka.edu.menu.InternalMenuService
import com.motycka.edu.menu.MenuItemDTO
import com.motycka.edu.menu.MenuItemResponse
import com.motycka.edu.security.IdentityDTO
import com.motycka.edu.user.UserRole

class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val internalMenuService: InternalMenuService,
    private val customerService: InternalCustomerService
) {

    suspend fun findById(orderId: OrderId, identity: IdentityDTO): OrderResponse? {
        val order = when (identity.role) {
            UserRole.CUSTOMER,
            UserRole.STAFF -> {
                orderRepository.selectById(orderId)
            }
        }

        val (menuItems, orderItems) = getMenuItemsInOrder(orderId)

        return order?.toResponse(
            menuItems = menuItems,
            orderItems = orderItems,
            totalPrice = PriceCalculator.calculatePrice(
                menuItems = menuItems,
                discountInPercent = customerService.getDiscountPercent(identity.userId),
                orderItems = orderItems
            )
        )
    }

    suspend fun create(order: OrderRequest, identity: IdentityDTO): OrderResponse {
        val createdOrder = orderRepository.create(order.toDTO(identity))
        val orderItems = order.items.map { item ->
            OrderItemDTO(
                id = null, // This will be replaced by the database
                orderId = requireNotNull(createdOrder.id) { "Created order ID cannot be null" },
                menuItemId = item.menuItemId,
                quantity = item.quantity
            )
        }
        orderItemRepository.createOrderItems(orderItems)

        val (menuItems, createdOrderItems) = getMenuItemsInOrder(createdOrder.id!!)

        return createdOrder.toResponse(
            menuItems = menuItems,
            orderItems = createdOrderItems,
            totalPrice = PriceCalculator.calculatePrice(
                menuItems = menuItems,
                discountInPercent = customerService.getDiscountPercent(identity.userId),
                orderItems = createdOrderItems
            )
        )
    }

    suspend fun updateStatus(id: OrderId, orderUpdate: OrderUpdateRequest, identity: IdentityDTO): OrderResponse? {
        when (identity.role) {
            UserRole.STAFF -> {
                orderRepository.selectById(id)?.let { existingOrder ->
                    orderRepository.update(
                        order = existingOrder.copy(
                            status = orderUpdate.status,
                            customerId = existingOrder.customerId
                        )
                    )
                }
            }
            UserRole.CUSTOMER -> throw IllegalArgumentException("Unauthorized action for role: ${identity.role}")
        }

        val updatedOrder = orderRepository.selectById(id)
        if (updatedOrder != null) {
            val (menuItems, orderItems) = getMenuItemsInOrder(id)
            return updatedOrder.toResponse(
                menuItems = menuItems,
                orderItems = orderItems,
                totalPrice = PriceCalculator.calculatePrice(
                    menuItems = menuItems,
                    discountInPercent = customerService.getDiscountPercent(identity.userId),
                    orderItems = orderItems
                )
            )
        }
        return null
    }

    private suspend fun getMenuItemsInOrder(orderId: OrderId): Pair<List<MenuItemDTO>, List<OrderItemDTO>> {
        val orderItems = orderItemRepository.selectByOrderId(orderId = orderId)
        val menuItems = internalMenuService.getMenuItems(ids = orderItems.map { it.menuItemId }.toSet()).toList()
        return Pair(menuItems, orderItems)
    }

    private fun OrderDTO.toResponse(
        menuItems: List<MenuItemDTO>,
        orderItems: List<OrderItemDTO>,
        totalPrice: Double
    ): OrderResponse {
        val orderItemResponses = orderItems.mapNotNull { orderItem ->
            val menuItem = menuItems.find { it.id == orderItem.menuItemId }
            if (menuItem != null) {
                OrderItemResponse(
                    menuItem = MenuItemResponse(
                        id = menuItem.id!!,
                        name = menuItem.name,
                        description = menuItem.description,
                        price = menuItem.price
                    ),
                    quantity = orderItem.quantity
                )
            } else null
        }

        return OrderResponse(
            id = requireNotNull(id) { "OrderDTO id cannot be null" },
            menuItems = orderItemResponses,
            totalPrice = totalPrice,
            status = status
        )
    }

    private fun OrderRequest.toDTO(identity: IdentityDTO): OrderDTO {
        return OrderDTO(
            id = null,
            customerId = identity.customerId,
            status = OrderStatus.PENDING
        )
    }
}
