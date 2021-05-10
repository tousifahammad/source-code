package com.webgrity.tishakds.data.entities

import androidx.annotation.Keep
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@Keep
@RealmClass
open class MenusMenuCategory() : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var restaurantId:Int = 0
    var menusId: Int = 0
    var menuCategoryId: Int = 0
    var status: Int = 1
    var backupFlag:Boolean = false
}