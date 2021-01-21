package com.example.motionlayoutdemo.util

import com.example.motionlayoutdemo.data.db.RealmLiveData
import com.example.motionlayoutdemo.data.db.RealmObjectLiveData
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmResults


fun <T : RealmModel> RealmResults<T>.asLiveData() = RealmLiveData<T>(this)

fun <T : RealmObject> T.getAsLiveData() = RealmObjectLiveData(this)

/**Realm Query Extension*/
/*fun <T : RealmModel> get(ofType: Class<T>, realm: Realm): List<T>? {
    return realm.where(ofType).equalTo("restaurantId", MyApplication.restaurantId)
        .and()
        .equalTo("backupFlag", false).findAll()
}*/