package com.picpay.desafio.android.feature.user.ui

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.commom.data.model.State
import com.picpay.desafio.android.feature.user.data.model.UserData

interface MainActivityInteractor {

    interface View {
        fun configureView()
    }

    interface ViewModel {
       fun getUsersLiveData(): LiveData<State<List<UserData>>>
    }
}