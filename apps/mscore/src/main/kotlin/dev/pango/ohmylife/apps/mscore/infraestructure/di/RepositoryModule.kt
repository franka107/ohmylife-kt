package dev.pango.ohmylife.apps.mscore.infraestructure.di

import dev.pango.ohmylife.apps.mscore.infraestructure.database.repository.TaskRepositorySqldelight
import dev.pango.ohmylife.features.sharedkernel.domain.repository.TaskRepository
import org.koin.dsl.module


val repositoryModule = module {
    single<TaskRepository> {TaskRepositorySqldelight(get()) }
}