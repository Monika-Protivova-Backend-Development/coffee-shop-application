package com.motycka.edu.order

import org.jetbrains.exposed.sql.transactions.transaction

class OrderRepositoryImpl : OrderRepository {

    override fun selectAll(): List<OrderDTO> = transaction {
        OrderDAO.all().map { it.toDTO() }
    }

    override fun selectById(id: OrderId): OrderDTO? = transaction {
        OrderDAO.findById(id)?.toDTO()
    }

    override fun create(order: OrderDTO): OrderDTO = transaction {
        OrderDAO.new {
            userId = order.customerId
            status = order.status
        }.toDTO()
    }

    override fun update(order: OrderDTO): OrderDTO = transaction {
        val orderEntity = OrderDAO.findById(order.id!!)
            ?: throw IllegalArgumentException("Order with id ${order.id} not found")

        orderEntity.userId = order.customerId
        orderEntity.status = order.status

        orderEntity.toDTO()
    }
}
