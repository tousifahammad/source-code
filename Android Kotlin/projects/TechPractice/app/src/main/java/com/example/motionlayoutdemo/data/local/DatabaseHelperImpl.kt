package com.example.motionlayoutdemo.data.local

import com.example.motionlayoutdemo.data.local.entity.User

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getUsers(): List<User> = appDatabase.userDao().getAll()

    override suspend fun insertAll(users: List<User>) = appDatabase.userDao().insertAll(users)

    override suspend fun delete(user: User) = appDatabase.userDao().delete(user)

    override suspend fun deleteAll(users: List<User>) {}

}