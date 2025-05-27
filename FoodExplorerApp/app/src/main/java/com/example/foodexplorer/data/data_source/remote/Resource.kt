package com.example.foodexplorer.data.data_source.remote

sealed class Resource<out T> {
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val message: String): Resource<Nothing>()
}