package com.example.hitchhikerace

import timber.log.Timber

inline fun <R> tryOrNull(block: () -> R): R? {
    return try {
        block()
    } catch (e: Exception) {
        Timber.e(e)
        return null
    }
}