package com.picpay.desafio.android.feature.user.data

import com.picpay.desafio.android.feature.user.data.model.UserLocalData
import com.picpay.desafio.android.feature.user.data.model.UserPayload

fun UserPayload.mapToUserData() = UserLocalData(
    id = this.id,
    image = this.img,
    name = this.name,
    userName = this.username
)
