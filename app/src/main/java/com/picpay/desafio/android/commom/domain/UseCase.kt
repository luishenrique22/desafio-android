package com.picpay.desafio.android.commom.domain

abstract class UseCase<in Params, out Type> where Type : Any {

    abstract fun execute(params: Params): Type
}
