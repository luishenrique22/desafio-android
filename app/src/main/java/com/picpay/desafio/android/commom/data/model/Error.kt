package com.picpay.desafio.android.commom.data.model

sealed class Error {

    object NetworkError : Error()

    data class ServerError<T>(val data: T) : Error()

    data class FeatureError<T>(val data: T) : Error()
}
