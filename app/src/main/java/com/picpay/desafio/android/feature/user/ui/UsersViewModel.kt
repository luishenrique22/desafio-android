package com.picpay.desafio.android.feature.user.ui

import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.feature.user.domain.GetUserParam
import com.picpay.desafio.android.feature.user.domain.GetUsersUseCase

class UsersViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    val getUsersLiveData = getUsersUseCase.execute(GetUserParam())
}