package com.webgrity.tishakds.data.entities

import androidx.annotation.Keep
import com.webgrity.tishakds.data.constants.Status
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@Keep
@RealmClass
open class MenuCategoryTaxes() : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var restaurantId:Int = 0
    var menuCategoryId: Int = 0
    var taxesId: Int = 0
    var status: Int = Status.active
    var backupFlag:Boolean = false
}