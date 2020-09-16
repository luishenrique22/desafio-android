package com.picpay.desafio.android.feature.user.ui

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.commom.data.model.State
import com.picpay.desafio.android.feature.user.data.model.UserData
import com.picpay.desafio.android.feature.user.domain.model.UserModel

interface MainActivityInteractor {

    interface View {
        fun configureView()
        fun observeResults()
        fun handleLoading(result: State.Loading<List<UserData>>)
        fun handleSuccess(result: State.Success<List<UserData>>)
        fun handleError(result: State.Failure<List<UserData>>)
        fun showLoading()
        fun showData(users: List<UserModel>)
        fun showError()
    }

    interface ViewModel {
       fun getUsersLiveData(): LiveData<State<List<UserData>>>
    }
}