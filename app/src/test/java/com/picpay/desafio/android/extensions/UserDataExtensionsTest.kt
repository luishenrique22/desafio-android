package com.picpay.desafio.android.extensions

import com.picpay.desafio.android.feature.user.data.extensions.mapToUserData
import com.picpay.desafio.android.feature.user.data.model.UserData
import com.picpay.desafio.android.feature.user.data.model.UserPayload
import com.picpay.desafio.android.feature.user.domain.extensions.mapToUserModel
import com.picpay.desafio.android.feature.user.domain.model.UserModel
import org.junit.Assert
import org.junit.Test

class UserDataExtensionsTest {

    @Test
    fun `GIVEN an UserPayload mapToUserData method SHOULD return a valid UserData`() {
        val expectedResult = UserData(id = 1, name = "Luis", userName = "@luis", image = "123456789")
        val userPayload = UserPayload(id = 1, name = "Luis", username = "@luis", img = "123456789")
        Assert.assertTrue(userPayload.mapToUserData() == expectedResult)
    }

    @Test
    fun `GIVEN a different UserPayload mapToUserData method SHOULD return wrong UserData`() {
        val expectedResult = UserData(id = 1, name = "Luis", userName = "@luis", image = "123456789")
        val userPayload = UserPayload(id = 3, name = "Carlos", username = "@carlos", img = "000000")
        Assert.assertFalse(userPayload.mapToUserData() == expectedResult)
    }


    @Test
    fun `GIVEN an UserData mapToUserModel method SHOULD return a valid UserModel`() {
        val expectedResult = UserModel(name = "Luis", userName = "@luis", image = "123456789")
        val userData = UserData(id = 1, name = "Luis", userName = "@luis", image = "123456789")
        Assert.assertTrue(userData.mapToUserModel() == expectedResult)
    }

    @Test
    fun `GIVEN a different UserData mapToUserModel method SHOULD return wrong UserModel`() {
        val expectedResult = UserModel(name = "Luis", userName = "@luis", image = "123456789")
        val userData = UserData(id = 3, name = "Carlos", userName = "@carlos", image = "000000")
        Assert.assertFalse(userData.mapToUserModel() == expectedResult)
    }
}