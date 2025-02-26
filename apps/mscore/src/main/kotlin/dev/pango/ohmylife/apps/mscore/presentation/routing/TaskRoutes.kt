package dev.pango.ohmylife.apps.mscore.presentation.routing

import dev.pango.ohmylife.features.sharedkernel.application.dto.TaskDto
import dev.pango.ohmylife.apps.mscore.application.service.TaskService
import dev.pango.ohmylife.features.sharedkernel.mapper.toDto
import dev.pango.ohmylife.features.sharedkernel.domain.repository.TaskRepository
import org.koin.ktor.ext.inject
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.taskRoutes() {
    val taskService by inject<TaskService>()
    val taskRepository by inject<TaskRepository>()

    routing {
        get("/tasks") {
            val result = taskRepository.getTaskList()
            result.fold(
                ifLeft = { call.respond(mapOf("error" to it.message, "cause" to it.cause?.message)) },
                ifRight = { call.respond(it.toDto()) }
            )
        }

        post("/tasks") {
            val request = call.receive<TaskDto>()
            val result = taskService.createTask(request)
            result.fold(
                ifLeft = { call.respond(mapOf("error" to it.message, "cause" to it.cause?.message)) },
                ifRight = { call.respond(mapOf("message" to "Task created successfully")) }
            )
        }

        post("/tasks/{id}/play") {
            val id = call.parameters["id"] ?: return@post call.respond(mapOf("error" to "Missing task ID"))
            val result = taskService.playTask(id)
            result.fold(
                ifLeft = { call.respond(mapOf("error" to it.message, "cause" to it.cause?.message)) },
                ifRight = { call.respond(mapOf("message" to "Task played successfully")) }
            )
        }

        post("/tasks/{id}/pause") {
            val id = call.parameters["id"] ?: return@post call.respond(mapOf("error" to "Missing task ID"))
            val result = taskService.pauseTask(id)
            result.fold(
                ifLeft = { call.respond(mapOf("error" to it.message, "cause" to it.cause?.message)) },
                ifRight = { call.respond(mapOf("message" to "Task paused successfully")) }
            )
        }

        post("/tasks/{id}/stop") {
            val id = call.parameters["id"] ?: return@post call.respond(mapOf("error" to "Missing task ID"))
            val result = taskService.stopTask(id)
            result.fold(
                ifLeft = { call.respond(mapOf("error" to it.message, "cause" to it.cause?.message)) },
                ifRight = { call.respond(mapOf("message" to "Task stoped successfully")) }
            )
        }


    }
}