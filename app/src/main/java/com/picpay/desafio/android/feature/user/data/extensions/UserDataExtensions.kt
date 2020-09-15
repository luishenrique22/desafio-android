package com.picpay.desafio.android.feature.user.data.extensions

import com.picpay.desafio.android.feature.user.data.model.UserData
import com.picpay.desafio.android.feature.user.data.model.UserPayload

fun UserPayload.mapToUserData() = UserData(
    id = this.id,
    image = this.img,
    name = this.name,
    userName = this.username
)
