package com.picpay.desafio.android.feature.user.domain

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.commom.data.model.State
import com.picpay.desafio.android.commom.domain.UseCase
import com.picpay.desafio.android.feature.user.data.model.UserData
import com.picpay.desafio.android.feature.user.domain.repository.GetUsersRepository


class GetUsersUseCase(private val repository: GetUsersRepository):
    UseCase<Nothing, LiveData<State<List<UserData>>>>() {
    override fun execute(params: Nothing): LiveData<State<List<UserData>>> {
       return repository.getUsers()
    }

}