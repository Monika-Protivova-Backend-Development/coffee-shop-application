package com.motycka.edu.order

import com.motycka.edu.security.getUserIdentity
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val logger = KotlinLogging.logger {}

private const val ORDER_NOT_FOUND = "Order not found"
private const val INVALID_ID = "Invalid ID format"

fun Route.orderRoutes(
    orderService: OrderService,
    basePath: String
) {
    route("$basePath/orders") {

        get("/{id}") {
            val idParam = call.parameters["id"]
            logger.info { "GET request received for order with id: $idParam" }

            val id = idParam?.toLongOrNull() ?: run {
                logger.warn { "Invalid ID format: $idParam" }
                call.respond(HttpStatusCode.BadRequest, INVALID_ID)
                return@get
            }

            try {
                val order = orderService.findById(
                    orderId = id,
                    identity = getUserIdentity()
                )

                if (order != null) {
                    logger.debug { "Responding with order: $order" }
                    call.respond(order)
                } else {
                    logger.warn { "Order with id: $id not found" }
                    call.respond(HttpStatusCode.NotFound, ORDER_NOT_FOUND)
                }
            } catch (e: Exception) {
                logger.error { "Error retrieving order: ${e.message}" }
                call.respond(HttpStatusCode.InternalServerError, "Error retrieving order: ${e.message}")
            }
        }

        post {
            logger.info { "POST request received to create a new order" }

            try {
                val request = call.receive<OrderRequest>()
                logger.debug { "Creating order with ${request.items.size} items" }

                val createdOrder = orderService.create(
                    order = request,
                    identity = getUserIdentity()
                )

                logger.info { "Order created successfully with id: ${createdOrder.id}" }
                call.respond(HttpStatusCode.Created, createdOrder)
            } catch (e: Exception) {
                logger.error { "Error creating order: ${e.message}" }
                call.respond(HttpStatusCode.InternalServerError, "Error creating order: ${e.message}")
            }
        }

        put("/{id}") {
            val idParam = call.parameters["id"]
            logger.info { "PUT request received to update order with id: $idParam" }

            val id = idParam?.toLongOrNull() ?: run {
                logger.warn { "Invalid ID format: $idParam" }
                call.respond(HttpStatusCode.BadRequest, INVALID_ID)
                return@put
            }

            try {
                val request = call.receive<OrderUpdateRequest>()
                logger.debug { "Updating order status to: ${request.status}" }

                val updatedOrder = orderService.updateStatus(
                    id = id,
                    orderUpdate = request,
                    identity = getUserIdentity()
                )

                if (updatedOrder != null) {
                    logger.info { "Order updated successfully with id: $id" }
                    call.respond(updatedOrder)
                } else {
                    logger.warn { "Order with id: $id not found for update" }
                    call.respond(HttpStatusCode.NotFound, ORDER_NOT_FOUND)
                }
            } catch (e: Exception) {
                logger.error { "Error updating order: ${e.message}" }
                call.respond(HttpStatusCode.InternalServerError, "Error updating order: ${e.message}")
            }
        }
    }
}
