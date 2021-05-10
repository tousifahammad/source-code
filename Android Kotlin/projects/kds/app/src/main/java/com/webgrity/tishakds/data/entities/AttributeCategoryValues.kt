package com.webgrity.tishakds.data.entities

import androidx.annotation.Keep
import com.webgrity.tishakds.data.constants.Status
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

@Keep
@RealmClass
open class AttributeCategoryValues() : RealmObject(), Serializable {
    @PrimaryKey
    var id: Int = 0
    var restaurantId:Int = 0
    var attributeCategoryId: Int = 0
    var name:String = ""
    var price:Float = 0F
    var menuItemsId:Int = 0
    var status: Int = Status.active
    var backupFlag:Boolean = false
}