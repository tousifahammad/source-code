package com.webgrity.tishalite.data.room.repositories

import com.webgrity.tishalite.data.room.dao.RestaurantDAO
import com.webgrity.tishalite.data.room.entities.Restaurant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantRepository @Inject constructor(private val restaurantDAO: RestaurantDAO) {

    fun getRestaurant(id:Int): Restaurant? = restaurantDAO.getRestaurant(id)

}