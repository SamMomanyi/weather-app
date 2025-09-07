package com.example.realtimeweather.api
//sealed class with type out T
//T refers to weather model
sealed class NetworkResponse<out T> {
    data class Success<out T>(val data : T): NetworkResponse<T>()
    data class Error(val message : String): NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()
}