package com.webgrity.tishalite.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.webgrity.tishalite.data.room.dao.RestaurantDAO
import com.webgrity.tishalite.data.room.entities.Restaurant

@Database(
    entities = [Restaurant::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract fun restaurantDAO() : RestaurantDAO

}