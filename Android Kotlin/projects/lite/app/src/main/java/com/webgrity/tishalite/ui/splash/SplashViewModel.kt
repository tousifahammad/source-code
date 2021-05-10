package com.webgrity.tishalite.ui.splash

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.webgrity.tishalite.data.api.ApiClient
import com.webgrity.tishalite.data.model.Login
import com.webgrity.tishalite.data.model.LoginResult
import com.webgrity.tishalite.data.room.entities.Restaurant
import com.webgrity.tishalite.data.room.repositories.RestaurantRepository
import com.webgrity.tishalite.util.Resource
import com.webgrity.tishalite.util.isInternetConnectionAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SplashViewModel @ViewModelInject constructor(@ApplicationContext private val context: Context,
                                                   private val restaurantRepository: RestaurantRepository,
                                                   private val apiClient: ApiClient):ViewModel() {

    suspend fun getRestaurant(id:Int): Restaurant? = withContext(Dispatchers.IO){restaurantRepository.getRestaurant(id)}

    fun login(login: Login): LiveData<Resource<LoginResult>> {
        return liveData {
            emit(Resource.loading(null))
            if(context.isInternetConnectionAvailable) {
                apiClient.getUsers(login).let {
                    if (it.isSuccessful) emit(Resource.success(it.body()))
                    else emit(Resource.error(it.errorBody().toString(), null))
                }
            } else emit(Resource.error("No internet connection", null))
        }
    }

}