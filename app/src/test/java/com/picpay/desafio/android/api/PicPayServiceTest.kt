package com.picpay.desafio.android.api

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.application.AppApplication
import com.picpay.desafio.android.di.CommomModule
import com.picpay.desafio.android.di.FeatureModules
import com.picpay.desafio.android.feature.user.data.datasource.remote.PicPayService
import com.picpay.desafio.android.feature.user.data.model.UserPayload
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class PicPayServiceTest {

    private lateinit var service: PicPayService
    private val server = MockWebServer()
    private var error = false

    @Before
    fun setup() {
        service = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(PicPayService::class.java)
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> {
                        if (error)
                            errorResponse
                        else
                            successResponse
                    }
                    else -> errorResponse
                }
            }
        }
    }

    companion object {

        private val successResponse by lazy {
            val body =
                "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}]"

            MockResponse()
                .setResponseCode(200)
                .setBody(body)
        }

        private val errorResponse by lazy { MockResponse().setResponseCode(404) }
    }


    @Test
    fun `GIVEN a call getUsers method SHOULD return a valid UserPayload`() {
        error = false
        val expectedPayload = listOf(
            UserPayload(
                id = 1001,
                name = "Eduardo Santos",
                username = "@eduardo.santos",
                img = "https://randomuser.me/api/portraits/men/9.jpg"
            )
        )
        runBlocking {
            val payload = service.getUsers()
            Assert.assertEquals(expectedPayload, payload)
        }
    }

    @Test(expected = Exception::class)
    fun `GIVEN a wrong call getUsers method SHOULD return exception`() {
        error = true
        val expectedPayload = listOf(
            UserPayload(
                id = 1001,
                name = "Eduardo Santos",
                username = "@eduardo.santos",
                img = "https://randomuser.me/api/portraits/men/9.jpg"
            )
        )
        runBlocking {
            val payload = service.getUsers()
            Assert.assertEquals(expectedPayload, payload)
        }
    }
}