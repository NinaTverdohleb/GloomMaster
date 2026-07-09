package com.rumpilstilstkin.gloommaster.utils

inline fun <R, E : Throwable> Result<R>.mapLeft(transform: (Throwable) -> E): Result<R> =
    when {
        isSuccess -> this
        else -> Result.failure(transform(exceptionOrNull()!!))
    }

inline fun <R, T> Result<R>.flatMap(transform: (R) -> Result<T>): Result<T> =
    when {
        isSuccess -> transform(getOrThrow())
        else -> Result.failure(exceptionOrNull()!!)
    }

inline fun <T, R> Result<T>.mapSuccess(transform: (T) -> R): Result<R> = map(transform)

inline fun <T> Result<T>.recoverIf(
    predicate: (Throwable) -> Boolean,
    recovery: (Throwable) -> T,
): Result<T> =
    when {
        isSuccess -> {
            this
        }

        else -> {
            val exception = exceptionOrNull()!!
            if (predicate(exception)) {
                Result.success(recovery(exception))
            } else {
                this
            }
        }
    }

inline fun <T, R, V> Result<T>.zip(
    other: Result<R>,
    combine: (T, R) -> V,
): Result<V> =
    when {
        isSuccess && other.isSuccess -> Result.success(combine(getOrThrow(), other.getOrThrow()))
        isFailure -> Result.failure(exceptionOrNull()!!)
        else -> Result.failure(other.exceptionOrNull()!!)
    }

fun <T : Any> T?.toResult(): Result<T> = this?.let { Result.success(it) } ?: Result.failure(NullPointerException("Value is null"))
