package com.picpay.desafio.android.commom.data.model

sealed class State<out T>(open val data: T?) {
    data class Loading<T>(override val data:  T? = null) : State<T>(data)
    data class Success<T>(override val data: T) : State<T>(data)
    data class Failure<T>(val error: Error, override val data: T?) : State<T>(data)
}
