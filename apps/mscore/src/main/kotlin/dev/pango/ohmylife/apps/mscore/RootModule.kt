package dev.pango.ohmylife.apps.mscore

import dev.pango.ohmylife.apps.mscore.application.service.TaskService
import dev.pango.ohmylife.apps.mscore.infraestructure.database.repository.TaskRepositorySqldelight
import dev.pango.ohmylife.features.sharedkernel.domain.repository.TaskRepository
import org.koin.dsl.module


val rootModule = module {
    single<TaskRepository> { TaskRepositorySqldelight(get()) }
    single { TaskService(get(), get()) }
}
