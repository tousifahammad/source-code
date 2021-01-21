package com.example.motionlayoutdemo.data.realm_objects

import androidx.annotation.Keep
import com.example.motionlayoutdemo.data.models.Status
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@Keep
@RealmClass
open class Invoice() : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var restaurantId:Int = 0
    var orderId:Int = 0
    var splitId:Int = 0
    var splitCount:Int = 0
    var splited:Boolean = false
    var orderTypeId:Int = 0
    var staffId:Int = 0
    var tablesId:Int = 0
    var totalPerson:Int = 0
    var mobile: String = ""
    var name: String = ""
    var email:String? = ""
    var address: String = ""
    var city: String = ""
    var pinCode: String = ""
    var subtotal:Float = 0F
    var discount:Float = 0F
    //var cancel:Boolean = false
    //var cancelReasonId:Int = 0
    var total:Float = 0F
    var tipAmount:Float = 0F
    var date: Date? = null
    var seatedTime: Date? = null
    var closedTime: Date? = null
    var note: String = ""
    var roundOff:Float = 0f
    var billStatus:Int = Status.billDefault
    var refundStatus:Int = Status.noRefund
    var status: Int = Status.active
    var backupFlag:Boolean = false
}