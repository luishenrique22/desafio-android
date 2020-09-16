package com.picpay.desafio.android.feature.user.domain.extensions

import com.picpay.desafio.android.feature.user.data.model.UserData
import com.picpay.desafio.android.feature.user.domain.model.UserModel

fun UserData.mapToUserModel(): UserModel = UserModel(
    name = this.name,
    userName = this.userName,
    image =  this.image
)