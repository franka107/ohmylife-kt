package dev.pango.ohmylife.apps.mscore.di

import dev.pango.ohmylife.apps.mscore.infraestructure.rabbitmq.rabbitMQModule
import dev.pango.ohmylife.apps.mscore.rootModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(
            rootModule,
            databaseModule,
            rabbitMQModule,
        )
    }
}
