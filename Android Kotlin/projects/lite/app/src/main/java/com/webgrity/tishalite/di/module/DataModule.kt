package com.webgrity.tishalite.di.module

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.webgrity.tishalite.data.room.Database
import com.webgrity.tishalite.data.room.dao.RestaurantDAO
import com.webgrity.tishalite.data.room.repositories.RestaurantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDataBase(application : Application, roomCallback : RoomDatabase.Callback) : Database {
        return Room.databaseBuilder(
            application.applicationContext,
            Database::class.java,
            "tisha_lite"
        )
            .fallbackToDestructiveMigration()
            .addCallback(roomCallback)
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomDatabaseCallback() : RoomDatabase.Callback {
        return object : RoomDatabase.Callback() {
            override fun onCreate(db : SupportSQLiteDatabase) {
                //Initialize Database if no database attached to the App
                super.onCreate(db)
            }

            override fun onOpen(db : SupportSQLiteDatabase) {
                //Re-open Database if it has database attached to the App
                super.onOpen(db)
            }
        }
    }

    @Provides
    @Singleton
    fun provideRestaurantDAO(database : Database) : RestaurantDAO {
        return database.restaurantDAO()
    }

    /*@Provides
    @Singleton
    fun provideRestaurantRepository(restaurantDAO: RestaurantDAO) : RestaurantRepository {
        return RestaurantRepository(restaurantDAO)
    }*/

}