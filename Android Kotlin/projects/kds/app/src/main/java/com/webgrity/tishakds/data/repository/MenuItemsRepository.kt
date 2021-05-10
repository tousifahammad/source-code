package com.webgrity.tishakds.data.repository

import android.util.Log
import com.webgrity.tishakds.app.MyApplication
import com.webgrity.tishakds.data.db.AppDatabase
import com.webgrity.tishakds.data.entities.MenuItems
import com.webgrity.tishakds.app.AppData
import com.webgrity.tishakds.data.constants.Status
import com.webgrity.tishakds.utilities.extensions.logd
import io.realm.Case
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class MenuItemsRepository {

    //auto increament id and sortby
    fun insertOrUpdateToRealm(res: MenuItems, realmIns: Realm? = null): Int {
        var id = 0
        val realm = realmIns ?: AppDatabase.getRealmInstance()
        try {
            realm.executeTransaction { realm ->
                val rest: MenuItems? = getActiveDataById(res.id)
                if (rest == null) {
                    val results: RealmResults<MenuItems> = realm.where(MenuItems::class.java).findAll()
                    id = if (results.size == 0) 1 else {
                        Objects.requireNonNull<Number>(realm.where(MenuItems::class.java).max("id")).toInt() + 1
                    }
                    res.id = id
                    res.sortBy = id
                }
                realm.insertOrUpdate(res)
            }
        } catch (error: Exception) {
            id = 0
        } finally {
            if (realmIns == null) realm.close()
        }
        return id
    }


    fun insertList(list: MutableList<MenuItems>, realmIns: Realm? = null) {
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

    fun updateToRealm(res: MenuItems) {
        val realm = AppDatabase.getRealmInstance()
        try {
            realm.executeTransaction { realm ->
                realm.insertOrUpdate(res)
//                Log.d(AppData.TAG, "One row updated")
            }
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
    }

    fun getAllData(realmIns: Realm? = null): ArrayList<MenuItems>? {
        var resdata: ArrayList<MenuItems>? = null
        val realm = realmIns ?: AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<MenuItems>? = realm.where(MenuItems::class.java).findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<MenuItems>?
        } catch (error: Exception) {
            error.printStackTrace()
        } finally {
            if (realmIns == null) realm.close()
        }

        return resdata
    }

    fun getAllActiveSortedData(): ArrayList<MenuItems>? {
        var resdata: ArrayList<MenuItems>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<MenuItems>? = realm
                .where(MenuItems::class.java)
                .equalTo("status", Status.active)
                .sort("sortBy")
                .findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<MenuItems>?
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }


    fun getActiveDataById(id: Int, realmIns: Realm? = null): MenuItems? {
        val realm = realmIns ?: AppDatabase.getRealmInstance()
        var mMenuItem: MenuItems? = null
        try {
            mMenuItem = realm.where<MenuItems>()
                .equalTo("id", id)
                .and()
                .equalTo("status", Status.active)
                .findFirst()
            if (mMenuItem != null) {
                mMenuItem = realm.copyFromRealm(mMenuItem)
            }
        } catch (error: Exception) {
            error.printStackTrace()
        } finally {
            if (realmIns == null) realm.close()
        }
        return mMenuItem
    }

    suspend fun geDataByIdAsync(id: Int, realmIns: Realm? = null): MenuItems? {
        return withContext(Dispatchers.Main) {
            val realm = realmIns ?: AppDatabase.getRealmInstance()
            var menuItems: MenuItems? = null
            try {
                val realmObject: MenuItems = realm.where<MenuItems>()
                    .equalTo("restaurantId", MyApplication.restaurantId)
                    .equalTo("id", id)
                    .equalTo("status", Status.active)
                    .findFirstAsync()
                realmObject.load()
                if (realmObject.isLoaded && realmObject.isValid) menuItems = realm.copyFromRealm(realmObject)
            } catch (error: Exception) {
                Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
            } finally {
                if (realmIns == null) realm.close()
            }
            menuItems
        }
    }


    fun getDataById(id: Int): MenuItems? {
        val realm = AppDatabase.getRealmInstance()
        var mMenuItem: MenuItems? = null
        try {
            mMenuItem = realm.where<MenuItems>()
                .equalTo("id", id)
                .and()
                .equalTo("status", Status.active)
                .findFirst()
            if (mMenuItem != null) {
                mMenuItem = realm.copyFromRealm(mMenuItem)
            }
        } catch (error: Exception) {
            error.printStackTrace()
        } finally {
            realm.close()
        }
        return mMenuItem
    }

    fun getAllDataByField(fieldName: String, fieldValue: Int): ArrayList<MenuItems> {
        var list = ArrayList<MenuItems>()
        val realm = AppDatabase.getRealmInstance()
        try {
            val allData = realm.where(MenuItems::class.java)
                .equalTo(fieldName, fieldValue)
                .equalTo("restaurantId", MyApplication.restaurantId)
                .equalTo("status", Status.active)
                .findAll()

            allData?.let { list = realm.copyFromRealm(allData) as ArrayList<MenuItems> }

        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : getAllDataByField() ", error)
        } finally {
            realm.close()
        }

        return list
    }

    suspend fun getAllItemByField(fieldName: String, fieldValue: Int): ArrayList<MenuItems> {
        var list = ArrayList<MenuItems>()
        withContext(Dispatchers.IO) {
            val realm = AppDatabase.getRealmInstance()
            try {
                val allData = realm.where(MenuItems::class.java)
                    .equalTo(fieldName, fieldValue)
                    .equalTo("restaurantId", MyApplication.restaurantId)
                    .equalTo("status", Status.active)
                    .findAll()

                allData?.let { list = realm.copyFromRealm(allData) as ArrayList<MenuItems> }

            } catch (error: Exception) {
                Log.d(AppData.TAG, "Error : getAllDataByField() ", error)
            } finally {
                realm.close()
            }
        }
        return list
    }

    suspend fun getAllItemByFieldAsync(fieldName: String, fieldValue: Int): ArrayList<MenuItems>? {
        var list: ArrayList<MenuItems>? = null
        withContext(Dispatchers.Main) {
            val realm = AppDatabase.getRealmInstance()
            try {
                val allData = realm.where(MenuItems::class.java)
                    .equalTo(fieldName, fieldValue)
                    .equalTo("restaurantId", MyApplication.restaurantId)
                    .equalTo("status", Status.active)
                    .findAllAsync()

                //allData?.let { list = realm.copyFromRealm(allData) as ArrayList<MenuItems> }
                list = realm.copyFromRealm(allData) as ArrayList<MenuItems>?

            } catch (error: Exception) {
                Log.d(AppData.TAG, "Error : getAllDataByField() ", error)
            } finally {
                realm.close()
            }
        }
        return list
    }


    fun getActiveDataBySearch(fieldName: String, value: String): ArrayList<MenuItems>? {
        var resdata: ArrayList<MenuItems>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<MenuItems>? = realm
                .where(MenuItems::class.java)
                .equalTo("status", Status.active)
                .contains(fieldName, value, Case.INSENSITIVE).findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<MenuItems>?
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
            val result = realm.where<MenuItems>()
                .equalTo(fieldName, value)
                .and()
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

    fun deleteAllData() {
        val realm = AppDatabase.getRealmInstance()
        try {
            val allData = realm.where(MenuItems::class.java).findAll()
            realm.executeTransaction { allData.deleteAllFromRealm() }
        } catch (error: Exception) {
            logd("Error --> $error")
        } finally {
            realm.close()
        }
    }

}