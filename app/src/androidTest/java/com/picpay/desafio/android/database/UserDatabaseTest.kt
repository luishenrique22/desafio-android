package com.picpay.desafio.android.database


import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.picpay.desafio.android.application.AppApplication
import com.picpay.desafio.android.commom.data.database.AppDatabase
import com.picpay.desafio.android.feature.user.data.datasource.local.UserLocalDao
import com.picpay.desafio.android.feature.user.data.model.UserData
import org.junit.*
import org.junit.runner.RunWith
import java.util.*
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class UserDatabaseTest {
    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var userLocalDao: UserLocalDao


    @Before
    fun setup() {
       val context = ApplicationProvider.getApplicationContext<AppApplication>()
        appDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        userLocalDao = appDatabase.UserLocalDao()
    }

    @Test
    fun testInsert() {
        val user = UserData(id = 1, name = "Luis", userName = "@luis", image = "01")
        userLocalDao.insert(user)
        val result = LiveDataUtil.getResult(userLocalDao.getUsers())
        Assert.assertTrue(Collections.singletonList(user) == result)
    }
    @Test
    fun testInsertAll() {
        val users: List<UserData> = listOf(
            UserData(id = 1, name = "Luis", userName = "@luis", image = "01"),
            UserData(id = 2, name = "Carlos", userName = "@carlos", image = "02"),
            UserData(id = 3, name = "Joao", userName = "@joao", image = "03")
        )
        userLocalDao.insertAll(users)
        val result = LiveDataUtil.getResult(userLocalDao.getUsers())
        Assert.assertTrue(users == result)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        appDatabase.close()
    }
}