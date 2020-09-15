package com.picpay.desafio.android.feature.user.data.model

import androidx.room.Entity


@Entity(primaryKeys = ["id", "userName"])
data class UserLocalData(
    val id: Int,
    val image: String,
    val name: String,
    val userName: String)
