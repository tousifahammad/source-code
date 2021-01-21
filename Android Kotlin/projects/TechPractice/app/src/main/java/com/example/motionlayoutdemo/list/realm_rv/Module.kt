package com.example.motionlayoutdemo.list.realm_rv

import androidx.annotation.Keep
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@Keep
@RealmClass
open class Module : RealmObject() {
    var id: Int = 0
    var name: String = ""
}