package com.picpay.desafio.android.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.picpay.desafio.android.commom.data.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object CommomModule {

    private const val dataBaseName = "picpay-database"

    val databaseModule = module {
        single {
            Room.databaseBuilder(androidApplication(), AppDatabase::class.java, dataBaseName)
                .build()
        }
    }
}