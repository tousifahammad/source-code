package com.webgrity.tishakds.data.repository

import com.webgrity.tishakds.data.db.AppDatabase
import com.webgrity.tishakds.data.entities.AttributeCategoryValues
import com.webgrity.tishakds.data.constants.Status
import com.webgrity.tishakds.utilities.extensions.logd
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import java.util.*

class AttributeCategoryValuesRepository() {

    fun insertOrUpdateToRealm(res: AttributeCategoryValues, realmIns: Realm? = null): Int {
        var id: Int = 0
        val realm = realmIns ?: AppDatabase.getRealmInstance()
        try {
            realm.executeTransaction { realm1 ->
                val rest: AttributeCategoryValues? = getDataById(res.id)
                if (rest == null) {
                    val results: RealmResults<AttributeCategoryValues> =
                        realm1.where(AttributeCategoryValues::class.java).findAll()
                    id = if (results.size == 0) 1 else {
                        Objects.requireNonNull<Number>(
                            realm1.where(AttributeCategoryValues::class.java).max("id")
                        ).toInt() + 1
                    }
                    res.id = id
                }
                realm1.insertOrUpdate(res)
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

    fun insertList(list: MutableList<AttributeCategoryValues>, realmIns: Realm? = null) {
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

    fun getAllData(fieldName: String = "", value: Int = 0): ArrayList<AttributeCategoryValues>? {
        var resdata: ArrayList<AttributeCategoryValues>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            var alldata: RealmResults<AttributeCategoryValues>? = null
            if (fieldName == "")
                alldata = realm.where(AttributeCategoryValues::class.java)
                    .equalTo("status", Status.active)
                    .findAll()
            else
                alldata = realm.where(AttributeCategoryValues::class.java)
                    .equalTo(fieldName, value)
                    .equalTo("status", Status.active)
                    .findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<AttributeCategoryValues>?
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }

    fun isFieldPresent(fieldName: String, value: String): Boolean {
        val realm = AppDatabase.getRealmInstance()
        try {
            val result = realm.where<AttributeCategoryValues>()
                .equalTo(fieldName, value)
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

    fun getDataById(id: Int): AttributeCategoryValues? {
        val realm = AppDatabase.getRealmInstance()
        var attributeCategoryValues: AttributeCategoryValues? = null
        try {
            val mAttributeCategoryValues: AttributeCategoryValues? = realm.where<AttributeCategoryValues>().equalTo("id", id).findFirst()
            if (mAttributeCategoryValues != null) {
                attributeCategoryValues = realm.copyFromRealm(mAttributeCategoryValues)
            }
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return attributeCategoryValues
    }

    fun deleteAllData() {
        val realm = AppDatabase.getRealmInstance()
        try {
            val allData = realm.where(AttributeCategoryValues::class.java).findAll()
            realm.executeTransaction { allData.deleteAllFromRealm() }
        } catch (error: Exception) {
            logd("Error --> $error")
        } finally {
            realm.close()
        }
    }

}