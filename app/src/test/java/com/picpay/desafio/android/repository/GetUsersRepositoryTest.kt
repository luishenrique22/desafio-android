package com.picpay.desafio.android.repository


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.picpay.desafio.android.commom.data.model.State
import com.picpay.desafio.android.feature.user.data.datasource.local.UserLocalDao
import com.picpay.desafio.android.feature.user.data.datasource.remote.PicPayService
import com.picpay.desafio.android.feature.user.data.model.UserData
import com.picpay.desafio.android.feature.user.data.model.UserPayload
import com.picpay.desafio.android.feature.user.data.repository.GetUsersRepositoryImp
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class GetUsersRepositoryTest {

    private lateinit var getUsersRepository: GetUsersRepositoryImp

    @Mock
    private lateinit var service: PicPayService

    @Mock
    private lateinit var localDao: UserLocalDao

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        MockitoAnnotations.initMocks(this)
        getUsersRepository = GetUsersRepositoryImp(localDao, service)
    }

    @Test
    fun `GIVEN a call GetUsersRepository SHOULD call database`() {
        getUsersRepository.getUsers()
        verify(localDao).getUsers()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN a call GetUsersRepository SHOULD call remote`() {
        runBlockingTest {
            val users: List<UserPayload> = listOf(
                UserPayload(id = 1, name = "Luis", username = "@luis", img = "01"),
                UserPayload(id = 2, name = "Carlos", username = "@carlos", img = "02"),
                UserPayload(id = 3, name = "Joao", username = "@joao", img = "03")
            )
            val dbData = MutableLiveData<List<UserData>>()
            Mockito.`when`(localDao.getUsers()).thenReturn(dbData)
            val api = mock<PicPayService> {
                onBlocking { getUsers() } doReturn users
            }
            getUsersRepository.getUsers()
            verify(service).getUsers()
        }
    }

}