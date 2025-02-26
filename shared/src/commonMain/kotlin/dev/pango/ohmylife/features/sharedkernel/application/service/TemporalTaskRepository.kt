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
}