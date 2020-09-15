package com.picpay.desafio.android.feature.user.domain.repository

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.commom.data.model.State
import com.picpay.desafio.android.feature.user.data.model.UserLocalData

interface GetUsersRepository {
    fun getUsers(): LiveData<State<List<UserLocalData>>>
}