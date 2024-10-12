package com.karem.postly.core

import androidx.annotation.StringRes

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(
        val code: Int? = null,
        val message: String? = null,
        @StringRes val messageRes: Int? = null,
        val exception: Exception? = null
    ) : Resource<Nothing>()

    data object Loading : Resource<Nothing>()
}