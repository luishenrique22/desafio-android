package com.picpay.desafio.android.feature.user.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.feature.user.data.model.UserData

@Dao
interface UserLocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserData>)

    @Query("SELECT * FROM UserData")
    fun getUsers(): LiveData<List<UserData>>
}
