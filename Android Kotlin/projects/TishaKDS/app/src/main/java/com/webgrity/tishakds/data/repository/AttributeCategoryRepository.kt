package com.webgrity.tishakds.data.repository

import android.util.Log
import com.webgrity.tishakds.data.db.AppDatabase
import com.webgrity.tishakds.data.entities.AttributeCategory
import com.webgrity.tishakds.app.AppData
import com.webgrity.tishakds.data.constants.Status
import com.webgrity.tishakds.utilities.extensions.logd
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import java.util.*

class AttributeCategoryRepository() {

    fun insertOrUpdateToRealm(res: AttributeCategory, realmIns: Realm? = null):Int {
        var id:Int = 0
        val realm = realmIns ?: AppDatabase.getRealmInstance()
        try{
            realm.executeTransaction { realm ->
                val rest:AttributeCategory? = getDataById(res.id)
                if(rest == null) {
                    val results: RealmResults<AttributeCategory> =
                        realm.where(AttributeCategory::class.java).findAll()
                    id = if (results.size == 0) 1 else {
                        Objects.requireNonNull<Number>(
                            realm.where(AttributeCategory::class.java).max("id")
                        ).toInt() + 1
                    }
                    res.id = id
                }
                else{
                    id = res.id
                }
                realm.insertOrUpdate(res)
//                Log.d(AppData.TAG, "One row inserted")
            }
        } catch (error: Exception) {
            id = 0
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            if (realmIns == null) realm.close()
        }
        return id
    }

    fun insertList(list: MutableList<AttributeCategory>, realmIns: Realm? = null) {
        val realm = realmIns ?: AppDatabase.getRealmInstance()
        try {
            realm.executeTransaction { realmTr ->
                realmTr.copyToRealm(list)
            }
        } catch (error: Exception) {
            error.printStackTrace()
        } finally {
            if (realmIns == null) realm.close()
        }
    }


    fun getAllData(): ArrayList<AttributeCategory>? {
        var resdata: ArrayList<AttributeCategory>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<AttributeCategory>? = realm.where(AttributeCategory::class.java).equalTo("status", Status.active).findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<AttributeCategory>?
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }

    fun isFieldPresent(fieldName: String, value: String, id:Int = 0): Boolean {
        val realm = AppDatabase.getRealmInstance()
        var result:AttributeCategory? = null
        try {
            if(id == 0)
                result = realm.where<AttributeCategory>()
                    .equalTo(fieldName, value)
                    .equalTo("status", Status.active)
                    .findFirst()
            else
                result = realm.where<AttributeCategory>()
                    .equalTo(fieldName, value)
                    .equalTo("status", Status.active)
                    .notEqualTo("id", id)
                    .findFirst()
            return result != null
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return false
    }

    fun isIconPresent(iconId: Int): Boolean {
        val realm = AppDatabase.getRealmInstance()
        try {
            val result = realm.where<AttributeCategory>()
                .equalTo("iconId", iconId)
                .equalTo("status", Status.active)
                .findFirst()
            return result != null
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return false
    }

    fun getDataById(id: Int): AttributeCategory? {
        val realm = AppDatabase.getRealmInstance()
        var attributeCategory: AttributeCategory? = null
        try {
            val mAttributeCategory:AttributeCategory? = realm
                .where<AttributeCategory>()
                .equalTo("id", id)
                .equalTo("status", Status.active)
                .findFirst()
            if(mAttributeCategory != null) {
                attributeCategory = realm.copyFromRealm(mAttributeCategory)
            }
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return attributeCategory
    }

    fun getDataById(id: Int, boolean: Boolean = false): AttributeCategory? {
        val realm = AppDatabase.getRealmInstance()
        var attributeCategory: AttributeCategory? = null
        try {
            val mAttributeCategory:AttributeCategory? = if(boolean)
                realm
                    .where<AttributeCategory>()
                    .equalTo("id", id)
                    .equalTo("status", Status.active)
                    .findFirst()
            else
                realm
                    .where<AttributeCategory>()
                    .equalTo("id", id)
                    .findFirst()
            if(mAttributeCategory != null) {
                attributeCategory = realm.copyFromRealm(mAttributeCategory)
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return attributeCategory
    }

    fun deleteAllData() {
        val realm = AppDatabase.getRealmInstance()
        try {
            val allData = realm.where(AttributeCategory::class.java).findAll()
            realm.executeTransaction { allData.deleteAllFromRealm() }
        } catch (error: Exception) {
            logd("Error $error")
        } finally {
            realm.close()
        }
    }

}