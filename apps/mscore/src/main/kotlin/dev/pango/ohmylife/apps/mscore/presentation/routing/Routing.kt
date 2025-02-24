package dev.pango.ohmylife.apps.mscore.presentation.routing


import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    taskRoutes()
//    routing {
//        get("/") {
//            call.respondText("Hello World!")
//        }
//    }
}
