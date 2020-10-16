package com.example.kmmtemplate.shared

sealed class NetworkResult<out T> {
  data class Success<T>(val value: T) : NetworkResult<T>()
  data class Error(val exception: Throwable) : NetworkResult<Nothing>()
}