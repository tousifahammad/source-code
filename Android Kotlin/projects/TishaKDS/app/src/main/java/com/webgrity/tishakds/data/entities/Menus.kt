package com.webgrity.tishakds.data.entities

import androidx.annotation.Keep
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@Keep
@RealmClass
open class Menus : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var restaurantId:Int = 0
    var name: String = ""
    var menuStatus:Boolean = false
    var scheduleStatus:Boolean = false
    var startTime1:String? = ""
    var endTime1:String? = ""
    var sun1: String = ""
    var mon1: String = ""
    var tue1: String = ""
    var wed1: String = ""
    var thu1: String = ""
    var fri1: String = ""
    var sat1: String = ""
    var startTime2:String? = ""
    var endTime2:String? = ""
    var sun2: String = ""
    var mon2: String = ""
    var tue2: String = ""
    var wed2: String = ""
    var thu2: String = ""
    var fri2: String = ""
    var sat2: String = ""
    var onlineOnly:Boolean = false
    var status: Int = 1
    var backupFlag:Boolean = false
}