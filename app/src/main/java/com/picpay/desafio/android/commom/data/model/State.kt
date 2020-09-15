package com.picpay.desafio.android.commom.data.model

sealed class State<out T> {
    data class Loading<T>(val data: T? = null) : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
    data class Failure<T>(val error: Error, val data: T?) : State<Nothing>()
}
