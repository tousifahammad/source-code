package com.webgrity.tisha.data.entities

import androidx.annotation.Keep
import com.webgrity.tisha.data.models.Status
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable
import java.util.*

@Keep
@RealmClass
open class MenuCategory : RealmObject(), Serializable {
    @PrimaryKey
    var id: Int = 0
    var restaurantId:Int = 0
    var name: String = ""
    var colourId: Int = 0
    var iconId: Int = 0
    var salesCategoryId:Int = 0
    var course:String = ""
    var orderBy:String = ""
    var status: Int = Status.active
    var updatedAt: Date = Date()
    var backupFlag:Boolean = false
    var selected:Boolean = false
}