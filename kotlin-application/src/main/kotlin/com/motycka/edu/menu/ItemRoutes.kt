package com.motycka.edu.menu

import com.motycka.edu.menu.models.MenuItem
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.itemRoutes(itemRepository: MenuRepository, basePath: String = "/api/menu") {
    route(basePath) {
        // Get all items
        get {
            call.respond(itemRepository.getAllItems())
        }

        // Get item by id
        get("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                return@get
            }

            val item = itemRepository.getItem(id)
            if (item == null) {
                call.respond(HttpStatusCode.NotFound, "Item not found")
            } else {
                call.respond(item)
            }
        }

        // Create new item
        post {
            val menuItem = call.receive<MenuItem>()
            val newItem = itemRepository.addItem(menuItem)
            call.respond(HttpStatusCode.Created, newItem)
        }

        // Update existing item
        put("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                return@put
            }

            val menuItem = call.receive<MenuItem>()
            val updatedItem = itemRepository.updateItem(id, menuItem)

            if (updatedItem == null) {
                call.respond(HttpStatusCode.NotFound, "Item not found")
            } else {
                call.respond(updatedItem)
            }
        }

        // Delete item
        delete("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                return@delete
            }

            val deleted = itemRepository.deleteItem(id)
            if (deleted) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, "Item not found")
            }
        }
    }
}
