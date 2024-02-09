package com.example.jetpack_compose_clearn_architecture.utils

data class Resource<T>(val status: Status, val data: T?, val message: String) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, "Success")
        }

        fun <T> Error(message: String): Resource<T> {
            return Resource(Status.ERROR, null, message)
        }

        fun <T> Loading(): Resource<T> {
            return Resource(Status.LOADING, null, "Please Wait")
        }
    }
}