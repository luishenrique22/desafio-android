package com.picpay.desafio.android.feature.user.data.datasource.remote

import com.picpay.desafio.android.feature.user.data.model.UserPayload
import retrofit2.Call
import retrofit2.http.GET


interface PicPayService {

    @GET("users")
    suspend fun getUsers(): List<UserPayload>
}