package com.picpay.desafio.android.commom.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.feature.user.data.datasource.local.UserLocalDao
import com.picpay.desafio.android.feature.user.data.model.UserData

@Database(
    entities = [UserData::class],
    version = AppDatabase.databaseVersion,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val databaseVersion = 1
    }
    
    abstract fun UserLocalDao(): UserLocalDao
}