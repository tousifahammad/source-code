package com.webgrity.tishakds.data.repository

import android.util.Log
import com.webgrity.tishakds.app.MyApplication
import com.webgrity.tishakds.data.db.AppDatabase
import com.webgrity.tishakds.data.entities.MenuCategoryTaxes
import com.webgrity.tishakds.app.AppData
import com.webgrity.tishakds.data.constants.Status
import com.webgrity.tishakds.utilities.extensions.logd
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import java.util.*

class MenuCategoryTaxesRepository {

    fun insertOrUpdateToRealm(res: MenuCategoryTaxes, backupFlag: Boolean = false, realmIns: Realm? = null): Int {
        var id = 0
        val realm = realmIns ?: AppDatabase.getRealmInstance()
        try {
            realm.executeTransaction {
                val rest: MenuCategoryTaxes? = getDataById(res.id)
                if (rest == null && res.id == 0) {
                    val results: RealmResults<MenuCategoryTaxes> =
                        it.where(MenuCategoryTaxes::class.java).findAll()
                    id = if (results.size == 0) 1 else {
                        Objects.requireNonNull<Number>(
                            it.where(MenuCategoryTaxes::class.java).max("id")
                        ).toInt() + 1
                    }
                    res.id = id
                } else
                    id = res.id

                if (res.restaurantId == 0) res.restaurantId = MyApplication.restaurantId
                res.backupFlag = backupFlag
                it.insertOrUpdate(res)
                //Log.d(AppData.TAG, "One row inserted")
            }
        } catch (error: Exception) {
            id = 0
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            if (realmIns == null) realm.close()
        }
        return id
    }

    fun insertList(list: MutableList<MenuCategoryTaxes>, realmIns: Realm? = null) {
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


    fun getAllData(restaurantId: Int = 0): MutableList<MenuCategoryTaxes>? {
        var resdata: MutableList<MenuCategoryTaxes>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<MenuCategoryTaxes> = realm.where(MenuCategoryTaxes::class.java)
                .equalTo("restaurantId", if (restaurantId == 0) MyApplication.restaurantId else restaurantId)
                .findAll()
            resdata = realm.copyFromRealm(alldata)
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error :", error)
        } finally {
            realm.close()
        }

        return resdata
    }

    fun isFieldPresent(fieldName: String, value: String, id: Int = 0): Boolean {
        val realm = AppDatabase.getRealmInstance()
        val result: MenuCategoryTaxes?
        try {
            result = if (id == 0)
                realm.where<MenuCategoryTaxes>()
                    .equalTo("restaurantId", MyApplication.restaurantId)
                    .equalTo(fieldName, value)
                    .equalTo("status", Status.active)
                    .findFirst()
            else
                realm.where<MenuCategoryTaxes>()
                    .equalTo("restaurantId", MyApplication.restaurantId)
                    .equalTo(fieldName, value)
                    .equalTo("status", Status.active)
                    .notEqualTo("id", id)
                    .findFirst()
            return result != null
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return false
    }

    fun getAllDataById(menuCategoryId: Int): ArrayList<MenuCategoryTaxes>? {
        var resdata: ArrayList<MenuCategoryTaxes>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<MenuCategoryTaxes> = realm.where(MenuCategoryTaxes::class.java)
                .equalTo("restaurantId", MyApplication.restaurantId)
                .equalTo("menuCategoryId", menuCategoryId)
                .equalTo("status", Status.active)
                .findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<MenuCategoryTaxes>?
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }

    fun getDataById(id: Int): MenuCategoryTaxes? {
        val realm = AppDatabase.getRealmInstance()
        var menuCategoryTaxes: MenuCategoryTaxes? = null
        try {
            val mMenuCategoryTaxes: MenuCategoryTaxes? = realm.where<MenuCategoryTaxes>().equalTo("id", id).findFirst()
            if (mMenuCategoryTaxes != null) {
                menuCategoryTaxes = realm.copyFromRealm(mMenuCategoryTaxes)
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return menuCategoryTaxes
    }

    fun getData(menuCategoryId: Int, taxesId: Int): MenuCategoryTaxes? {
        val realm = AppDatabase.getRealmInstance()
        var menuCategoryTaxes: MenuCategoryTaxes? = null
        try {
            val mMenuCategoryTaxes: MenuCategoryTaxes? = realm.where<MenuCategoryTaxes>()
                .equalTo("restaurantId", MyApplication.restaurantId)
                .equalTo("menuCategoryId", menuCategoryId)
                .equalTo("taxesId", taxesId)
                .equalTo("status", Status.active)
                .findFirst()
            if (mMenuCategoryTaxes != null) {
                menuCategoryTaxes = realm.copyFromRealm(mMenuCategoryTaxes)
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return menuCategoryTaxes
    }

    fun deleteData(menuCategoryId: Int, taxesId: Int): Boolean {
        val realm = AppDatabase.getRealmInstance()
        val bool: Boolean
        bool = try {
            val mMenuCategoryTaxes: MenuCategoryTaxes? = realm.where<MenuCategoryTaxes>()
                .equalTo("restaurantId", MyApplication.restaurantId)
                .equalTo("menuCategoryId", menuCategoryId)
                .equalTo("taxesId", taxesId)
                .equalTo("status", Status.active)
                .findFirst()
            realm.executeTransaction {
                mMenuCategoryTaxes?.let {
                    it.status = Status.deleted
                    it.backupFlag = false
                    realm.insertOrUpdate(it)
                }
            }
            true
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
            false
        } finally {
            realm.close()
        }
        return bool
    }

    fun checkTaxApplicability(menuCategoryId: Int, taxesId: Int): Boolean {
        val realm = AppDatabase.getRealmInstance()
        val bool: Boolean
        bool = try {
            val mMenuCategoryTaxes: MenuCategoryTaxes? = realm.where<MenuCategoryTaxes>()
                .equalTo("restaurantId", MyApplication.restaurantId)
                .equalTo("menuCategoryId", menuCategoryId)
                .equalTo("taxesId", taxesId)
                .equalTo("status", Status.active)
                .findFirst()
            mMenuCategoryTaxes != null
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
            false
        } finally {
            realm.close()
        }
        return bool
    }


    fun deleteAllData() {
        val realm = AppDatabase.getRealmInstance()
        try {
            val allData = realm.where(MenuCategoryTaxes::class.java).findAll()
            realm.executeTransaction { allData.deleteAllFromRealm() }
        } catch (error: Exception) {
            logd("Error --> $error")
        } finally {
            realm.close()
        }
    }

}