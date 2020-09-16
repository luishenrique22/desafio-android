package com.picpay.desafio.android.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.commom.data.database.AppDatabase
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CommomModule {

    private const val dataBaseName = "picpay-database"
    private const val url = "http://careers.picpay.com/tests/mobdev/"

    val databaseModule = module {
        single {
           return@single Room.databaseBuilder(androidApplication(), AppDatabase::class.java, dataBaseName)
                .build()
        }
    }

    val remoteModule = module {
        single {
            OkHttpClient.Builder().build()
        }
        single {
            GsonBuilder().create()
        }
        single {
            Retrofit.Builder()
                .baseUrl(url)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .build()
        }
    }
}