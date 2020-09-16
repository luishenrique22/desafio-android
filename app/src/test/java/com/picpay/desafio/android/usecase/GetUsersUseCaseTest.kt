package com.picpay.desafio.android.usecase


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.picpay.desafio.android.commom.data.model.Error
import com.picpay.desafio.android.commom.data.model.State
import com.picpay.desafio.android.feature.user.data.datasource.local.UserLocalDao
import com.picpay.desafio.android.feature.user.data.datasource.remote.PicPayService
import com.picpay.desafio.android.feature.user.data.model.UserData
import com.picpay.desafio.android.feature.user.domain.GetUserParam
import com.picpay.desafio.android.feature.user.domain.GetUsersUseCase
import com.picpay.desafio.android.feature.user.domain.repository.GetUsersRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetUsersUseCaseTest {

    private lateinit var getUsersUseCase: GetUsersUseCase

    @Mock
    private lateinit var getUsersRepository: GetUsersRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getUsersUseCase = GetUsersUseCase(getUsersRepository)

    }

    @Test
    fun `GIVEN an valid call GetUsersCase SHOULD return a valid LiveData with state Success`() {
        val users: List<UserData> = listOf(
            UserData(id = 1, name = "Luis", userName = "@luis", image = "01"),
            UserData(id = 2, name = "Carlos", userName = "@carlos", image = "02"),
            UserData(id = 3, name = "Joao", userName = "@joao", image = "03")
        )
        val expectedResult: LiveData<State<List<UserData>>> =
            MutableLiveData(State.Success(users)) as LiveData<State<List<UserData>>>
        Mockito.`when`(getUsersRepository.getUsers()).thenReturn(expectedResult)
        Assert.assertTrue(expectedResult == getUsersUseCase.execute(GetUserParam()))
    }

    @Test
    fun `GIVEN an valid call GetUsersCase SHOULD return a valid LiveData with state Loading`() {
        val users: List<UserData> = listOf(
            UserData(id = 1, name = "Luis", userName = "@luis", image = "01"),
            UserData(id = 2, name = "Carlos", userName = "@carlos", image = "02"),
            UserData(id = 3, name = "Joao", userName = "@joao", image = "03")
        )
        val expectedResult: LiveData<State<List<UserData>>> =
            MutableLiveData(State.Loading(users)) as LiveData<State<List<UserData>>>
        Mockito.`when`(getUsersRepository.getUsers()).thenReturn(expectedResult)
        Assert.assertTrue(expectedResult == getUsersUseCase.execute(GetUserParam()))
    }

    @Test
    fun `GIVEN an valid call GetUsersCase SHOULD return a valid LiveData with state Error`() {
        val users: List<UserData> = listOf(
            UserData(id = 1, name = "Luis", userName = "@luis", image = "01"),
            UserData(id = 2, name = "Carlos", userName = "@carlos", image = "02"),
            UserData(id = 3, name = "Joao", userName = "@joao", image = "03")
        )
        val expectedResult: LiveData<State<List<UserData>>> = MutableLiveData(
            State.Failure(
                Error.NetworkError,
                users
            )
        ) as LiveData<State<List<UserData>>>
        Mockito.`when`(getUsersRepository.getUsers()).thenReturn(expectedResult)
        Assert.assertTrue(expectedResult == getUsersUseCase.execute(GetUserParam()))
    }
}