package com.picpay.desafio.android.application

import android.app.Application
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.di.FeatureModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            if (BuildConfig.DEBUG)
                androidLogger(level = Level.DEBUG)
            androidContext(applicationContext)
            loadKoinModules(FeatureModules.modules)
        }
    }
}