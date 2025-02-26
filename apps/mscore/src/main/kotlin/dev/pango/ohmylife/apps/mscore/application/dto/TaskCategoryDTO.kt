package dev.pango.ohmylife.apps.mscore.application.dto

import kotlinx.serialization.Serializable



@Serializable
data class TaskCategoryDTO(
    val categoryReason: String,
    val categoryType: String,
)
