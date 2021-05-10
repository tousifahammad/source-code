package com.webgrity.tishalite.data.model

data class LoginResult(
    val isSuccessful: Boolean,
    val message: String,
    val restaurantList: List<RestaurantResponse>,
    val user: User
)