package com.motycka.edu

import kotlinx.serialization.Serializable

typealias OrderTestId = Long
typealias CustomerId = Long

enum class OrderStatus {
    PENDING,
    PAID,
    COMPLETED,
    CANCELLED
}

@Serializable
data class OrderTestResponse(
    val id: OrderTestId?,
    val customerId: CustomerId?,
    val items: List<MenuItemTestResponse>?,
    val totalPrice: Double?,
    val isPaid: Boolean?,
    val status: OrderStatus?
)

@Serializable
data class CreateOrderTestRequest(
    val customerId: CustomerId?,
    val items: List<MenuItemTestResponse>?
)

@Serializable
data class UpdateOrderTestRequest(
    val customerId: CustomerId?,
    val items: List<MenuItemTestResponse>?
)

@Serializable
data class CreateOrderTestResponse(
    val orderId: OrderTestId?,
    val totalPrice: Double?
)
