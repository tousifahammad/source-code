package com.webgrity.tishakds.data.entities

import androidx.annotation.Keep
import com.webgrity.tishakds.data.constants.Status
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable
import java.util.*

@Keep
@RealmClass
open class Restaurant() : RealmObject(),Serializable {
    @PrimaryKey
    var id: Int = 0
    var merchantKey: String = ""
    var name: String = ""
    var address:String = ""
    var city:String = ""
    var state:String = ""
    var country:String = ""
    var pincode:String = ""
    var phoneno:String = ""
    var email:String = ""
    var website:String = ""
    var facebook:String = ""
    var instagram:String = ""
    var twitter:String = ""
    var licensenumber:String = ""
    var userId:Int = 0
    var uuid:String? = ""
    var deviceInfo:String? = ""
    var expiryDate:String? = ""
    var businessRegistrationDetails:String? = ""
    var createdAt: Date = Date()
    var status: Int = Status.activeRestaurant
    var backupFlag:Boolean = false
}