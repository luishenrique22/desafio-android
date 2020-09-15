package com.picpay.desafio.android

import com.picpay.desafio.android.feature.user.data.datasource.remote.PicPayService
import com.picpay.desafio.android.feature.user.data.model.UserPayload

class ExampleService(
    private val service: PicPayService
) {

    fun example(): List<UserPayload> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}