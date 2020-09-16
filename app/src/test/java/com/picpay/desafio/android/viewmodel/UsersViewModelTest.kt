package com.picpay.desafio.android.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.picpay.desafio.android.commom.data.model.Error
import com.picpay.desafio.android.commom.data.model.State
import com.picpay.desafio.android.di.CommomModule
import com.picpay.desafio.android.di.FeatureModules
import com.picpay.desafio.android.feature.user.data.model.UserData
import com.picpay.desafio.android.feature.user.di.UserModule
import com.picpay.desafio.android.feature.user.domain.GetUserParam
import com.picpay.desafio.android.feature.user.domain.GetUsersUseCase
import com.picpay.desafio.android.feature.user.ui.UsersViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class UsersViewModelTest : KoinTest {
    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()


    private val viewModel: UsersViewModel by inject()

    private val getUsersUseCase: GetUsersUseCase = mockk()

    @MockK
    private lateinit var context: Context

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        MockKAnnotations.init(this)

        startKoin {
            androidContext(context)
            modules(FeatureModules.modules)
            val userModule = module {
                factory(override = true) { getUsersUseCase }
            }
            loadKoinModules(userModule)
        }
    }

    @Test
    fun `GIVEN an valid call viewModel SHOULD return a valid LiveData with state Success`() {
        val users: List<UserData> = listOf(
            UserData(id = 1, name = "Luis", userName = "@luis", image = "01"),
            UserData(id = 2, name = "Carlos", userName = "@carlos", image = "02"),
            UserData(id = 3, name = "Joao", userName = "@joao", image = "03")
        )
        val expectedResult: LiveData<State<List<UserData>>> =
            MutableLiveData(State.Success(users)) as LiveData<State<List<UserData>>>

        val observer = mock<Observer<State<List<UserData>>>>()
        every { getUsersUseCase.execute(any()) } returns expectedResult
        viewModel.getUsersLiveData().observeForever(observer)
        getUsersUseCase.execute(any())
        Assert.assertEquals(expectedResult.value, viewModel.getUsersLiveData().value)
    }

    @Test
    fun `GIVEN an valid call viewModel SHOULD return a valid LiveData with state Loading`() {
        val users: List<UserData> = listOf(
            UserData(id = 1, name = "Luis", userName = "@luis", image = "01"),
            UserData(id = 2, name = "Carlos", userName = "@carlos", image = "02"),
            UserData(id = 3, name = "Joao", userName = "@joao", image = "03")
        )
        val expectedResult: LiveData<State<List<UserData>>> =
            MutableLiveData(State.Loading(users)) as LiveData<State<List<UserData>>>

        val observer = mock<Observer<State<List<UserData>>>>()
        every { getUsersUseCase.execute(any()) } returns expectedResult
        viewModel.getUsersLiveData().observeForever(observer)
        getUsersUseCase.execute(any())
        Assert.assertEquals(expectedResult.value, viewModel.getUsersLiveData().value)
    }

    @Test
    fun `GIVEN an valid call viewModel SHOULD return a valid LiveData with state Error`() {
        val users: List<UserData> = listOf(
            UserData(id = 1, name = "Luis", userName = "@luis", image = "01"),
            UserData(id = 2, name = "Carlos", userName = "@carlos", image = "02"),
            UserData(id = 3, name = "Joao", userName = "@joao", image = "03")
        )
        val expectedResult: LiveData<State<List<UserData>>> =
            MutableLiveData(
                State.Failure(
                    Error.NetworkError,
                    users
                )
            ) as LiveData<State<List<UserData>>>

        val observer = mock<Observer<State<List<UserData>>>>()
        every { getUsersUseCase.execute(any()) } returns expectedResult
        viewModel.getUsersLiveData().observeForever(observer)
        getUsersUseCase.execute(any())
        Assert.assertEquals(expectedResult.value, viewModel.getUsersLiveData().value)
    }
}