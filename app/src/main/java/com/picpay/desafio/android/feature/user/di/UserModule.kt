package com.picpay.desafio.android.feature.user.di

import com.picpay.desafio.android.commom.data.database.AppDatabase
import com.picpay.desafio.android.feature.user.data.datasource.local.UserLocalDao
import com.picpay.desafio.android.feature.user.data.datasource.remote.PicPayService
import com.picpay.desafio.android.feature.user.data.repository.GetUsersRepositoryImp
import com.picpay.desafio.android.feature.user.domain.GetUsersUseCase
import com.picpay.desafio.android.feature.user.domain.repository.GetUsersRepository
import com.picpay.desafio.android.feature.user.ui.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

object UserModule {
    val graph = module {
        single {
            provideUserService(retrofit = get())
        }
        factory {
            provideUserLocalDao(appDatabase = get())
        }

        factory {
            provideUserRepository(userLocalDao = get(), service = get())
        } bind GetUsersRepository::class

        factory {
            GetUsersUseCase(repository = get())
        }

        viewModel {
            UsersViewModel(getUsersUseCase = get())
        }
    }

    private fun provideUserService(retrofit: Retrofit): PicPayService =
        retrofit.create(PicPayService::class.java)

    private fun provideUserLocalDao(appDatabase: AppDatabase): UserLocalDao =
        appDatabase.UserLocalDao()

    private fun provideUserRepository(userLocalDao: UserLocalDao, service: PicPayService) =
        GetUsersRepositoryImp(userLocalDao, service)
}