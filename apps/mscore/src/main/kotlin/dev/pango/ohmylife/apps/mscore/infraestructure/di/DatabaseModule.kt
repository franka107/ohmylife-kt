package dev.pango.ohmylife.apps.mscore.infraestructure.di


import dev.pango.ohmylife.apps.mscore.infraestructure.database.DatabaseFactory
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseFactory.createDatabase() }
}