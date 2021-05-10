package com.webgrity.tishalite.data.api

import com.webgrity.tishalite.data.model.Login
import com.webgrity.tishalite.data.model.LoginResult
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getLoginResult(login: Login): Response<LoginResult> = apiService.getLoginResult(login)

}