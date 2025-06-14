package com.motycka.edu.order

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object OrderTable : LongIdTable("order") {
    val customerId = long("customer_id")
    val status = enumerationByName("status", 50, OrderStatus::class)
}

class OrderDAO(id: EntityID<Long>) : LongEntity(id) {
    var userId by OrderTable.customerId
    var status by OrderTable.status

    companion object : LongEntityClass<OrderDAO>(OrderTable)

    fun toDTO(): OrderDTO {
        return OrderDTO(
            id = id.value,
            customerId = userId,
            status = status
        )
    }
}


object OrderItemTable : LongIdTable("order_item") {
    val orderId = long("order_id").references(OrderTable.id)
    val menuItemId = long("menu_item_id")
    val quantity = integer("quantity")
}

class OrderItemDAO(id: EntityID<Long>) : LongEntity(id) {
    var orderId by OrderItemTable.orderId
    var menuItemId by OrderItemTable.menuItemId
    var quantity by OrderItemTable.quantity

    companion object : LongEntityClass<OrderItemDAO>(OrderItemTable)

    fun toDTO(): OrderItemDTO {
        return OrderItemDTO(
            id = id.value,
            orderId = orderId,
            menuItemId = menuItemId,
            quantity = quantity
        )
    }
}
