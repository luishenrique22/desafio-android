package com.picpay.desafio.android.repository


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.picpay.desafio.android.feature.user.data.datasource.local.UserLocalDao
import com.picpay.desafio.android.feature.user.data.datasource.remote.PicPayService
import com.picpay.desafio.android.feature.user.data.repository.GetUsersRepositoryImp

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
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
        MockitoAnnotations.initMocks(this)
        getUsersRepository = GetUsersRepositoryImp(localDao, service)
    }

    @Test
    fun `GIVEN a call GetUsersRepository SHOULD call database`() {
        getUsersRepository.getUsers()
        verify(localDao).getUsers()
    }


}