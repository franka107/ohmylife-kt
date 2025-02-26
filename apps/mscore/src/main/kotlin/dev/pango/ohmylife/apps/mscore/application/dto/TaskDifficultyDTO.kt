package dev.pango.ohmylife.apps.mscore.application.dto

import kotlinx.serialization.Serializable


@Serializable
data class TaskDifficultyDTO(
    val difficultyPoints: Int?,
    val difficultyReason: String?,
)