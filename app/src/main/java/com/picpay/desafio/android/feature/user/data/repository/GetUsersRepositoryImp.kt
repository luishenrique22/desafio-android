package com.picpay.desafio.android.feature.user.data.repository

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.commom.data.NetworkBoundResource
import com.picpay.desafio.android.commom.data.model.State
import com.picpay.desafio.android.feature.user.data.datasource.local.UserLocalDao
import com.picpay.desafio.android.feature.user.data.datasource.remote.PicPayService
import com.picpay.desafio.android.feature.user.data.extensions.mapToUserData
import com.picpay.desafio.android.feature.user.data.model.UserData
import com.picpay.desafio.android.feature.user.data.model.UserPayload
import com.picpay.desafio.android.feature.user.domain.repository.GetUsersRepository

class GetUsersRepositoryImp(private val localDao: UserLocalDao, private val service: PicPayService) : GetUsersRepository {
    override fun getUsers(): LiveData<State<List<UserData>>> {
        return object : NetworkBoundResource<List<UserPayload>, List<UserData>>() {
            override fun loadFromDb(): LiveData<List<UserData>> {
                return localDao.getUsers()
            }

            override fun shouldFetch(data: List<UserData>?): Boolean {
               return true
            }

            override suspend fun makeApiCall(): List<UserPayload> {
               return service.getUsers()
            }

            override suspend fun saveCallResult(data: List<UserPayload>) {
                localDao.insertAll(data.map { item -> item.mapToUserData() })
            }
        }.asLiveData()

    }
}
