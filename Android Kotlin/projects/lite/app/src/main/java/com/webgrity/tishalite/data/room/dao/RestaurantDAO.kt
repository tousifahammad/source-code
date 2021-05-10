package com.webgrity.tishalite.data.room.dao

import androidx.room.*
import com.webgrity.tishalite.data.room.entities.Restaurant
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(restaurant: Restaurant)

    @Update
    fun update(restaurant: Restaurant)

    //@Delete
    //fun delete(customEntity: CustomerEntity)

    @Query("DELETE FROM restaurant WHERE Id = :id")
    fun delete(id : Int)

    /*@Query("DELETE FROM customer")
    fun deleteAll()*/

    @Query("SELECT * FROM restaurant WHERE id = :id")
    fun getRestaurant(id:Int) : Restaurant?

    /*@Query("SELECT * FROM customer LIMIT 10 OFFSET :pageIndex")
    fun getAllByPageIndex(pageIndex:Int) : List<CustomerEntity>*/
}