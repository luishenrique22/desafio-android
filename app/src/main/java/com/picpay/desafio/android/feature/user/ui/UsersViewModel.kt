package com.picpay.desafio.android.feature.user.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.commom.data.model.State
import com.picpay.desafio.android.feature.user.data.model.UserData
import com.picpay.desafio.android.feature.user.domain.GetUserParam
import com.picpay.desafio.android.feature.user.domain.GetUsersUseCase

class UsersViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel(), MainActivityInteractor.ViewModel {

    private val getUsersLiveData = getUsersUseCase.execute(GetUserParam())

    override fun getUsersLiveData(): LiveData<State<List<UserData>>> = getUsersLiveData
}