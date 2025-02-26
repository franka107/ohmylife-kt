package dev.pango.ohmylife.apps.mscore.infraestructure.rabbitmq

import dev.pango.ohmylife.apps.mscore.trash.TaskConsumer
import dev.pango.ohmylife.features.sharedkernel.domain.repository.TaskRepository
import io.ktor.server.application.Application
import org.koin.ktor.ext.get


fun Application.configureRabbitmq() {
    val taskRepository = get<TaskRepository>()
    val taskConsumer = TaskConsumer(taskRepository)
    taskConsumer.startListening()


}
