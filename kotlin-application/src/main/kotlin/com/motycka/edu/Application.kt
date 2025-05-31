package com.motycka.edu

import com.motycka.edu.menu.MenuRepository
import com.motycka.edu.menu.itemRoutes
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.auth.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

// Create a singleton instance of ItemRepository
val itemRepository = MenuRepository()

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureAuthentication()
    configureSerialization()
    configureRouting()
}

fun Application.configureAuthentication() {
    install(Authentication) {
        basic("auth-basic") {
            realm = "Item API"
            validate { credentials ->
                // In a real application, you would validate against a database
                // For this example, we'll use hardcoded credentials
                if (credentials.name == "admin" && credentials.password == "password") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World! Welcome to the Item API. Try /items to see all items.")
        }

        // Public routes - no authentication required
        route("/public") {
            route("/items") {
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
            }
        }

        // Protected routes - authentication required
        authenticate("auth-basic") {
            // Register item routes that require authentication
            itemRoutes(itemRepository)
        }
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
}
