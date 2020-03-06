package net.app.mvvmsampleapp.floor.design.add_table

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class Table() : RealmObject() {
    @PrimaryKey
    var id: String = ""
    var number: String = ""
    var shape: String = ""
    var width: Int = 0
    var height: Int = 0
    var position_x: Float = 0f
    var position_y: Float = 0f
    var createdAt: Date = Date()
}