package dev.pango.ohmylife.apps.mscore

import dev.pango.ohmylife.apps.mscore.di.configureDI
import dev.pango.ohmylife.apps.mscore.infraestructure.database.configureDatabase
import dev.pango.ohmylife.apps.mscore.infraestructure.rabbitmq.configureRabbitmq
import dev.pango.ohmylife.apps.mscore.infraestructure.serialization.configureSerialization
import dev.pango.ohmylife.apps.mscore.presentation.routing.configureRouting
import io.ktor.server.application.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDI()
    configureSerialization()
    configureRouting()
    configureDatabase()
    configureRabbitmq()

}

