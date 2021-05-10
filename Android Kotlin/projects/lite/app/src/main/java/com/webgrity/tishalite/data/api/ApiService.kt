package com.webgrity.tishalite.data.api

import com.webgrity.tishalite.data.model.Login
import com.webgrity.tishalite.data.model.LoginResult
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("key:464EHSfN0Z")
    @POST("api/userLogin")
    suspend fun getLoginResult(@Body login: Login): Response<LoginResult>


}