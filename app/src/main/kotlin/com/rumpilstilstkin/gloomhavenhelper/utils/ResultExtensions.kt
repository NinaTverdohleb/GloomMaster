package com.rumpilstilstkin.gloomhavenhelper.utils

/**
 * Extension functions for kotlin.Result class.
 * These extensions provide additional functionality for working with Result objects.
 */

/**
 * Maps a failure result to a different failure type.
 * Similar to map, but operates on the failure case instead of the success case.
 *
 * @param transform Function that transforms the original exception to a new exception
 * @return A new Result with either the original success value or the transformed failure
 */
inline fun <R, E : Throwable> Result<R>.mapLeft(transform: (Throwable) -> E): Result<R> {
    return when {
        isSuccess -> this
        else -> Result.failure(transform(exceptionOrNull()!!))
    }
}

/**
 * Similar to flatMap in functional programming, this allows chaining operations that return Result.
 * If the original Result is a success, applies the transform function to the success value.
 * If the original Result is a failure, returns the original failure.
 *
 * @param transform Function that takes the success value and returns a new Result
 * @return The Result returned by the transform function or the original failure
 */
inline fun <R, T> Result<R>.flatMap(transform: (R) -> Result<T>): Result<T> {
    return when {
        isSuccess -> transform(getOrThrow())
        else -> Result.failure(exceptionOrNull()!!)
    }
}

/**
 * Converts a Result<T> to a Result<R> by applying the transform function to the success value.
 * This is similar to the built-in map function but with a more descriptive name.
 *
 * @param transform Function to transform the success value
 * @return A new Result with the transformed success value or the original failure
 */
inline fun <T, R> Result<T>.mapSuccess(transform: (T) -> R): Result<R> {
    return map(transform)
}

/**
 * Recovers from specific exceptions by applying a recovery function.
 * If the Result is a failure and the exception matches the specified type,
 * applies the recovery function to get a new value.
 *
 * @param predicate Function that determines if the exception should be recovered from
 * @param recovery Function that provides a recovery value
 * @return A new Result with either the original success value or the recovery value
 */
inline fun <T> Result<T>.recoverIf(
    predicate: (Throwable) -> Boolean,
    recovery: (Throwable) -> T
): Result<T> {
    return when {
        isSuccess -> this
        else -> {
            val exception = exceptionOrNull()!!
            if (predicate(exception)) {
                Result.success(recovery(exception))
            } else {
                this
            }
        }
    }
}

/**
 * Combines two Results. If both are successful, applies the combine function to both success values.
 * If either is a failure, returns the first failure.
 *
 * @param other Another Result to combine with this one
 * @param combine Function that combines the two success values
 * @return A new Result with the combined success value or the first failure
 */
inline fun <T, R, V> Result<T>.zip(
    other: Result<R>,
    combine: (T, R) -> V
): Result<V> {
    return when {
        isSuccess && other.isSuccess -> Result.success(combine(getOrThrow(), other.getOrThrow()))
        isFailure -> Result.failure(exceptionOrNull()!!)
        else -> Result.failure(other.exceptionOrNull()!!)
    }
}

/**
 * Converts a nullable value to a Result.
 * If the value is null, returns a failure with NullPointerException.
 * Otherwise, returns a success with the non-null value.
 *
 * @return A Result containing the non-null value or a failure with NullPointerException
 */
fun <T : Any> T?.toResult(): Result<T> {
    return this?.let { Result.success(it) } ?: Result.failure(NullPointerException("Value is null"))
}