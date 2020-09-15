package com.picpay.desafio.android.feature.user.data.model

import com.google.gson.annotations.SerializedName


data class UserPayload(
    @SerializedName("img") val img: String,
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String
)