package dev.pango.ohmylife.features.sharedkernel.domain.failure


sealed class AppFailure(val errorCode: String, val message: String, open val cause: Throwable? = null) {

    data object NetworkFailure : AppFailure(
        errorCode = "NETWORK_ERROR",
        message = "Connection issue"
    ) {
        override fun toString() = "NetworkFailure: $message (Code: $errorCode)"
    }

    data class DatabaseFailure(
        override val cause: Throwable
    ) : AppFailure(
        errorCode = "DATABASE_ERROR",
        message = "Issue with database",
        cause = cause
    ) {
        override fun toString() = "DatabaseFailure: $message (Code: $errorCode)"
    }

    data class ValidationFailure(val field: String, val validationMessage: String) :
        AppFailure(
            errorCode = "VALIDATION_ERROR",
            message = "Invalid input in field [$field]: $validationMessage"
        ) {
        override fun toString() = "ValidationFailure: $message (Code: $errorCode)"
    }

    data class UnknownFailure(override val cause: Throwable) :
        AppFailure(
            errorCode = "UNKNOWN_ERROR",
            message = cause.message ?: "An unknown error occurred"
        ) {
        override fun toString() = "UnknownFailure: $message (Code: $errorCode)"
    }
}