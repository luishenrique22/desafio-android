package com.picpay.desafio.android.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.picpay.desafio.android.commom.data.model.Error
import com.picpay.desafio.android.commom.data.model.State
import com.picpay.desafio.android.di.FeatureModules
import com.picpay.desafio.android.feature.user.data.model.UserData
import com.picpay.desafio.android.feature.user.domain.GetUserParam
import com.picpay.desafio.android.feature.user.domain.GetUsersUseCase
import com.picpay.desafio.android.feature.user.ui.UsersViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.*
import org.junit.rules.TestRule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.MockitoAnnotations


class UsersViewModelTest : KoinTest {
    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()


    private val viewModel: UsersViewModel by inject()

    private val getUsersUseCase: GetUsersUseCase = mockk()

    @MockK
    private lateinit var context: Context

    @MockK(relaxed = true)
    private lateinit var observerResult: Observer<State<List<UserData>>>
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
            MutableLiveData(State.Success(users))

        val observer = mock<Observer<State<List<UserData>>>>()
        every { getUsersUseCase.execute(any()) } returns expectedResult
        viewModel.getUsersLiveData().observeForever(observer)
        getUsersUseCase.execute(GetUserParam())
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
            MutableLiveData(State.Loading(users))

        val observer = mock<Observer<State<List<UserData>>>>()
        every { getUsersUseCase.execute(any()) } returns expectedResult
        viewModel.getUsersLiveData().observeForever(observer)
        getUsersUseCase.execute(GetUserParam())
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
            )

        val observer = mock<Observer<State<List<UserData>>>>()
        every { getUsersUseCase.execute(any()) } returns expectedResult
        viewModel.getUsersLiveData().observeForever(observer)
        getUsersUseCase.execute(GetUserParam())
        Assert.assertEquals(expectedResult.value, viewModel.getUsersLiveData().value)
    }

    @Test
    fun `GIVEN an valid call viewModel SHOULD return a valid LiveData with state Loading and Success`() {
        val users: List<UserData> = listOf(
            UserData(id = 1, name = "Luis", userName = "@luis", image = "01"),
            UserData(id = 2, name = "Carlos", userName = "@carlos", image = "02"),
            UserData(id = 3, name = "Joao", userName = "@joao", image = "03")
        )
        val expectedResult = MutableLiveData<State<List<UserData>>>()
        every { getUsersUseCase.execute(any()) } returns expectedResult
        viewModel.getUsersLiveData().observeForever(observerResult)
        getUsersUseCase.execute(GetUserParam())
        expectedResult.value = State.Loading()
        expectedResult.value = State.Success(users)
        verify { observerResult.onChanged(State.Loading()) }
        verify { observerResult.onChanged(State.Success(users)) }
    }

    @Test
    fun `GIVEN an valid call viewModel SHOULD return a valid LiveData with state Loading and Error`() {
        val expectedResult = MutableLiveData<State<List<UserData>>>()
        every { getUsersUseCase.execute(any()) } returns expectedResult
        viewModel.getUsersLiveData().observeForever(observerResult)
        getUsersUseCase.execute(GetUserParam())
        expectedResult.value = State.Loading()
        expectedResult.value = State.Failure(Error.NetworkError, null)
        verify { observerResult.onChanged(State.Loading()) }
        verify { observerResult.onChanged(State.Failure(Error.NetworkError, null)) }
    }

    @After
    fun tearDown(){
        stopKoin()
    }
}