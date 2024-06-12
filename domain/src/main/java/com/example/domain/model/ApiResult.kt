package com.example.domain.model

sealed class ApiResult {
    data class Success<T>(val data: T) : ApiResult()
    data class Error(val message: String?) : ApiResult()
}
