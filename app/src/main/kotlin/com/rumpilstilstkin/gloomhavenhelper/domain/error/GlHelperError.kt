package com.rumpilstilstkin.gloomhavenhelper.domain.error

/**
 * Base class for all errors in the GloomhavenHalper application.
 * This provides a consistent way to handle errors across all layers.
 */
sealed class GlHelperError(): Exception() {
    /**
     * Error that occurs when a resource is not found.
     */
    data class NotFound(
        override val message: String = "Resource not found",
        override val cause: Throwable? = null
    ) : GlHelperError() {
        constructor(cause: Throwable) : this(message = cause.message ?: "Resource not found", cause = cause)
    }

    /**
     * Error that occurs during database operations.
     */
    data class Database(
        override val message: String = "Database error occurred",
        override val cause: Throwable? = null
    ) : GlHelperError(){
        constructor(cause: Throwable) : this(message = cause.message ?: "Database error occurred", cause = cause)
    }

    /**
     * Error that occurs during network operations.
     */
    data class Network(
        override val message: String = "Network error occurred",
        override val cause: Throwable? = null
    ) : GlHelperError(){
        constructor(cause: Throwable) : this(message = cause.message ?: "Network error occurred", cause = cause)
    }

    /**
     * Error that occurs due to invalid input.
     */
    data class InvalidInput(
        override val message: String = "Invalid input provided",
        override val cause: Throwable? = null
    ) : GlHelperError() {
        constructor(cause: Throwable) : this(message = cause.message ?: "Invalid input provided", cause = cause)
    }

    /**
     * Unexpected error that doesn't fit into other categories.
     */
    data class Unexpected(
        override val message: String = "An unexpected error occurred",
        override val cause: Throwable? = null
    ) : GlHelperError() {
        constructor(cause: Throwable) : this(message = cause.message ?: "An unexpected error occurred", cause = cause)
    }
}