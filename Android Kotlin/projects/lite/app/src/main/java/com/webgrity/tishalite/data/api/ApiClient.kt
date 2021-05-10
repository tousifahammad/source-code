package com.webgrity.tishalite.data.api

import com.webgrity.tishalite.data.model.Login
import javax.inject.Inject

class ApiClient @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers(login: Login) =  apiHelper.getLoginResult(login)

}