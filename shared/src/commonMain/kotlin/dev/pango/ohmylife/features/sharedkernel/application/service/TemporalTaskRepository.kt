package dev.pango.ohmylife.features.sharedkernel.application.service

import dev.pango.ohmylife.features.sharedkernel.application.dto.TaskDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class TemporalTaskRepository(private val client: HttpClient) {
    suspend fun getTasks(): List<TaskDto> {
        val response: HttpResponse = client.get("https://core.ohmylife.development.pango.dev/tasks")
        return try {
            if (response.status == HttpStatusCode.OK) {
                response.body<List<TaskDto>>()
            } else {
                println("Error on response: ${response.status}")
                emptyList()
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            emptyList()
        }
    }

    suspend fun createTask(task: TaskDto): Boolean {
        return try {
            val response: HttpResponse = client.post("https://core.ohmylife.development.pango.dev/tasks") {
                contentType(ContentType.Application.Json)
                setBody(task)
            }

            if (response.status == HttpStatusCode.OK) {
                println("Task created successfully")
                true
            } else {
                println("Error on response: ${response.status}")
                false
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            false
        }
    }




    suspend fun playTask(taskId: String) {
        val response: HttpResponse = client.post("https://core.ohmylife.development.pango.dev/tasks/${taskId}/play")
    }

    suspend fun pauseTask(taskId: String) {
        val response: HttpResponse = client.post("https://core.ohmylife.development.pango.dev/tasks/${taskId}/pause")
    }

    suspend fun stopTask(taskId: String) {
        val response: HttpResponse = client.post("https://core.ohmylife.development.pango.dev/tasks/${taskId}/stop")
    }

}