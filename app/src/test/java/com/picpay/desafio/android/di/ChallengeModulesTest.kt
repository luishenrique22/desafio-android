package com.picpay.desafio.android.di

import android.content.Context
import com.picpay.desafio.android.application.AppApplication
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.mockito.Mockito

class ChallengeModulesTest: KoinTest {
    private val context: Context = Mockito.mock(AppApplication::class.java)

    @Before
    fun setup() {
        startKoin {
            androidContext(context)
        }
    }

    @Test
    fun checkModulesTest() = getKoin().checkModules()

    @After
    fun tearDown() = stopKoin()
}