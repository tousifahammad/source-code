package com.example.motionlayoutdemo.data.local

import com.example.motionlayoutdemo.data.local.entity.User

interface DatabaseHelper {

    suspend fun getUsers(): List<User>

    suspend fun insertAll(users: List<User>)

    suspend fun delete(user: User)

    suspend fun deleteAll(users: List<User>)

}