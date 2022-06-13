package com.anadolstudio.core.tasks

sealed interface Result<T> {

    data class Success<T>(val data: T) : Result<T>

    data class Error<T>(val error: Throwable) : Result<T>

    class Loading<T> : Result<T>

    class Empty<T> : Result<T>

}
