package com.picpay.desafio.android.di

import com.picpay.desafio.android.feature.user.di.UserModule

object FeatureModules {
    val modules = listOf(CommomModule.databaseModule, CommomModule.remoteModule, UserModule.graph)
}
