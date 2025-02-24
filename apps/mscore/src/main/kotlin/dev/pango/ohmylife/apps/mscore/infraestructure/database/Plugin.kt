package dev.pango.ohmylife.apps.mscore.infraestructure.database

import dev.pango.ohmylife.core.app_data.db.AppDataDatabase
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.engine.EmbeddedServer
import org.koin.ktor.ext.get


fun Application.configureDatabase() {
    val database = get<AppDataDatabase>()
    monitor.subscribe(ApplicationStarted) {
        println("✅ Database initialized with Koin !")
    }
}