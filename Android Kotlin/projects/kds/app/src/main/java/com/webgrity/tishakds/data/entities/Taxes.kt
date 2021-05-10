package com.webgrity.tishakds.data.entities

import androidx.annotation.Keep
import com.webgrity.tishakds.data.constants.Status
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@Keep
@RealmClass
open class Taxes() : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var restaurantId:Int = 0
    var name:String = ""
    var percent:Float = 0F
    var status: Int = Status.active
    var registration:String = ""
    var backupFlag:Boolean = false
}