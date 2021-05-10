package com.webgrity.tishalite.data.api

import com.webgrity.tishalite.data.model.Login
import com.webgrity.tishalite.data.model.LoginResult
import retrofit2.Response

interface ApiHelper {

    suspend fun getLoginResult(login: Login): Response<LoginResult>
}