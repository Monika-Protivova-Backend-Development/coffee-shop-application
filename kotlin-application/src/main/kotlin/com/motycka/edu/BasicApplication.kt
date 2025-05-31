package com.motycka.edu

import com.motycka.edu.menu.MenuRepository
import com.motycka.edu.menu.MenuService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json

private val menuService = MenuService(
    menuRepository = MenuRepository()
)

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }

        routing {

            route("/api") {

                route("/menu") {

                    get {
                        call.respond(menuService.getMenuItems())
                    }

                    get("{$ID}") {
                        val id = call.parameters[ID]?.toIntOrNull()
                        if (id != null) {
                            val item = menuService.getMenuItemById(id)
                            if (item != null) {
                                call.respond(item)
                            } else {
                                call.respond(HttpStatusCode.NotFound, "Item not found")
                            }
                        } else {
                            call.respond(HttpStatusCode.BadRequest, "Invalid item ID")
                        }
                    }
                }
            }
        }
    }.start(wait = true)
}

const val ID = "id"


