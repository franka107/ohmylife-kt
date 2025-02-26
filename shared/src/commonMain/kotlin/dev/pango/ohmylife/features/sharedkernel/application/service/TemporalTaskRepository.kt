package dev.pango.ohmylife.features.sharedkernel.application.service

import dev.pango.ohmylife.features.sharedkernel.application.dto.TaskDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class TemporalTaskRepository(private val client: HttpClient) {
    suspend fun getTasks(): List<TaskDto> {
        return try {
            val response: HttpResponse = client.get("http://localhost:8080/tasks")
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}