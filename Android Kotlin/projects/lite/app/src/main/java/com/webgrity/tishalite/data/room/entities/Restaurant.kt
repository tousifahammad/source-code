package com.webgrity.tishalite.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.webgrity.tishalite.util.Status
import java.util.*

@Entity(tableName = "restaurant")
data class Restaurant (
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var merchantKey: String = "",
    var name: String = "",
    var address:String = "",
    var city:String = "",
    var state:String = "",
    var country:String = "",
    var countryCode: String = "",
    var timezone: String = "",
    var pincode:String = "",
    var phoneno:String = "",
    var email:String = "",
    var website:String = "",
    var facebook:String = "",
    var instagram:String = "",
    var twitter:String = "",
    var licensenumber:String = "",
    var userId:Int = 0,
    var uuid:String = "",
    var deviceInfo:String = "",
    var expiryDate:String = "",
    var businessRegistrationDetails:String = "",
    var menuPreference:Int = 0,
    var createdAt: Date = Date(),
    var status: Int = Status.activeRestaurant,
    var backupFlag:Boolean = false,
)