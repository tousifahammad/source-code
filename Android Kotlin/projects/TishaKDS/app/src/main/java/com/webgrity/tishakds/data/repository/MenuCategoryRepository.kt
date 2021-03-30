package com.webgrity.tishakds.data.repository

import com.webgrity.tishakds.app.MyApplication
import com.webgrity.tishakds.data.db.AppDatabase
import com.webgrity.tishakds.data.entities.MenuCategory
import com.webgrity.tishakds.data.constants.Status
import com.webgrity.tishakds.utilities.extensions.logd
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import java.util.*

class MenuCategoryRepository() {

    fun insertOrUpdateToRealm(res: MenuCategory, realmIns: Realm? = null): Int {
        var id: Int = 0
        val realm = realmIns ?: AppDatabase.getRealmInstance()
        try {
            realm.executeTransaction {
                val rest: MenuCategory? = getDataById(res.id)
                if (rest == null) {
                    val results: RealmResults<MenuCategory> =
                        it.where(MenuCategory::class.java).findAll()
                    id = if (results.size == 0) 1 else {
                        Objects.requireNonNull<Number>(
                            it.where(MenuCategory::class.java).max("id")
                        ).toInt() + 1
                    }
                    res.id = id
                } else {
                    id = rest.id
                    res.updatedAt = Date()
                }
                it.insertOrUpdate(res)
                //Log.d("tishakds==>>>", "One row inserted in menu category repository")
            }
        } catch (error: Exception) {
            id = 0
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            if (realmIns == null) realm.close()
        }
        return id
    }

    fun insertList(list: MutableList<MenuCategory>, realmIns: Realm? = null) {
        val realm = realmIns ?: AppDatabase.getRealmInstance()
        try {
            realm.executeTransaction { realmTr ->
                realmTr.insertOrUpdate(list)
            }
        } catch (error: Exception) {
            error.printStackTrace()
        } finally {
            if (realmIns == null) realm.close()
        }
    }

    fun getLatest(): Date? {
        val realm = AppDatabase.getRealmInstance()
        try {
            val results: RealmResults<MenuCategory> =
                realm.where(MenuCategory::class.java).findAll()
            if (results.size > 0) {
                return realm.where(MenuCategory::class.java).maximumDate("updatedAt")
            }
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return null
    }

    fun isFieldPresent(fieldName: String, value: String, id: Int = 0): Boolean {
        val realm = AppDatabase.getRealmInstance()
        var result: MenuCategory? = null
        try {
            result = if (id == 0)
                realm.where<MenuCategory>()
                    .equalTo(fieldName, value)
                    .equalTo("status", Status.active)
                    .findFirst()
            else
                realm.where<MenuCategory>()
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
            val result = realm.where<MenuCategory>()
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

    fun isColorPresent(colorId: Int): Boolean {
        val realm = AppDatabase.getRealmInstance()
        try {
            val result = realm.where<MenuCategory>()
                .equalTo("colourId", colorId)
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

    fun getAllData(): ArrayList<MenuCategory>? {
        var resdata: ArrayList<MenuCategory>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<MenuCategory>? = realm.where(MenuCategory::class.java)
                .equalTo("restaurantId", MyApplication.restaurantId)
                .equalTo("status", Status.active)
                .findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<MenuCategory>?
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }

    fun getAllUpdatedData(date: Date): ArrayList<MenuCategory>? {
        var resdata: ArrayList<MenuCategory>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<MenuCategory>? = realm.where(MenuCategory::class.java).equalTo("status", Status.active)
                .greaterThanOrEqualTo("updatedAt", date).findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<MenuCategory>?
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }

    fun getDataById(id: Int): MenuCategory? {
        val realm = AppDatabase.getRealmInstance()
        var menuCategory: MenuCategory? = null
        try {
            val mMenuCategory: MenuCategory? = realm
                .where<MenuCategory>()
                .equalTo("id", id)
                .equalTo("restaurantId", MyApplication.restaurantId)
                .equalTo("status", Status.active)
                .findFirst()
            if (mMenuCategory != null) {
                menuCategory = realm.copyFromRealm(mMenuCategory)
            }
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return menuCategory
    }

    fun deleteAllData() {
        val realm = AppDatabase.getRealmInstance()
        try {
            val allData = realm.where(MenuCategory::class.java).findAll()
            realm.executeTransaction { allData.deleteAllFromRealm() }
        } catch (error: Exception) {
            logd("Error --> $error")
        } finally {
            realm.close()
        }
    }

}