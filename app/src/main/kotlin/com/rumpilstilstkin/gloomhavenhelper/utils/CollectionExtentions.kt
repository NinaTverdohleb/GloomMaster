package com.rumpilstilstkin.gloomhavenhelper.utils

inline fun <T> Iterable<T>.mapIf(
    predicate: (T) -> Boolean,
    transform: (T) -> T,
): List<T> =
    this.map { item ->
        if (predicate(item)) transform(item) else item
    }
