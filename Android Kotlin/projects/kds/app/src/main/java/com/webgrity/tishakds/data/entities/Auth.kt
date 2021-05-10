package com.webgrity.tishakds.data.entities

import androidx.annotation.Keep
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

@Keep
@RealmClass
open class Auth : RealmObject(), Serializable {
    @PrimaryKey
    var id: Int = 1
    var merchantKey: String = ""
    var serverIp: String = ""
    var restaurantId: Int = 0
    var restaurantName: String = ""
    var tableId: Int = 0
    var type: Int = 0
    var image: ByteArray? = null
    var textColor: Int = 0
    var headerColor: Int = 0
}