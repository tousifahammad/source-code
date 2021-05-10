package com.webgrity.tishakds.data.entities

import androidx.annotation.Keep
import com.webgrity.tishakds.data.constants.Status
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@Keep
@RealmClass
open class Tables() : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var restaurantId:Int = 0
    var floorId: Int = 0
    var name: String = ""
    var password: String = ""
    var description: String = ""
    var seatingCapacity: Int = 0
    var shape: Int = 0
    var disable: String = ""
    var staffId: Int = 0
    var status: Int = Status.active
    var backupFlag: Boolean = false
    var width: Int = 0
    var height: Int = 0
    var positionX: Float = 0f
    var positionY: Float = 0f
    var rotation: Float = 0f
    var createdAt: Date = Date()
}