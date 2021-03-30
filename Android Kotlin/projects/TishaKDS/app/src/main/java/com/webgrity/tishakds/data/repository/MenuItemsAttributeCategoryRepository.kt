package com.webgrity.tishakds.data.repository

import android.util.Log
import com.webgrity.tishakds.app.MyApplication
import com.webgrity.tishakds.data.db.AppDatabase
import com.webgrity.tishakds.data.entities.MenusItemsAttributeCategory
import com.webgrity.tishakds.app.AppData
import com.webgrity.tishakds.data.constants.Status
import com.webgrity.tishakds.utilities.extensions.logd
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class MenuItemsAttributeCategoryRepository {

    fun insertOrUpdateToRealm(res: MenusItemsAttributeCategory, boolean: Boolean = false, realmIns: Realm? = null): Int {
        var id = 0
        val realmInstance = realmIns ?: AppDatabase.getRealmInstance()
        try {
            realmInstance.executeTransaction { realm ->
                val rest: MenusItemsAttributeCategory? = getDataById(res.id)
                if (rest == null) {
                    val results: RealmResults<MenusItemsAttributeCategory> =
                        realm.where(MenusItemsAttributeCategory::class.java).findAll()
                    id = if (results.size == 0) 1 else {
                        Objects.requireNonNull<Number>(
                            realm.where(MenusItemsAttributeCategory::class.java).max("id")
                        ).toInt() + 1
                    }
                    res.id = id
                }
                //if(res.restaurantId == 0) res.restaurantId = MyApplication.restaurantId
                if (!boolean) res.backupFlag = false
                realm.insertOrUpdate(res)
                //Log.d(AppData.TAG, "One row inserted : MenusItemsAttributeCategory id : $id")
            }
        } catch (error: Exception) {
            id = 0
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            if (realmIns == null) realmInstance.close()
        }
        return id
    }

    fun insertList(list: MutableList<MenusItemsAttributeCategory>, realmIns: Realm? = null) {
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

    fun getAllData(): ArrayList<MenusItemsAttributeCategory>? {
        var resdata: ArrayList<MenusItemsAttributeCategory>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val allData: RealmResults<MenusItemsAttributeCategory> = realm.where(MenusItemsAttributeCategory::class.java)
                //.equalTo("restaurantId", MyApplication.restaurantId)
                .equalTo("status", Status.active)
                .findAll()
            resdata = realm.copyFromRealm(allData) as ArrayList<MenusItemsAttributeCategory>?
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }


    fun getDataById(id: Int): MenusItemsAttributeCategory? {
        val realm = AppDatabase.getRealmInstance()
        var miacategory: MenusItemsAttributeCategory? = null
        try {
            val mMiacategory: MenusItemsAttributeCategory? = realm.where<MenusItemsAttributeCategory>().equalTo("id", id).findFirst()
            if (mMiacategory != null) {
                miacategory = realm.copyFromRealm(mMiacategory)
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return miacategory
    }


    suspend fun getAllActiveDataByField(fieldName: String, fieldValue: Int): MutableList<MenusItemsAttributeCategory> {
        var resdata: MutableList<MenusItemsAttributeCategory> = mutableListOf()
        withContext(Dispatchers.Main) {
            val realm = AppDatabase.getRealmInstance()
            try {
                val allData: RealmResults<MenusItemsAttributeCategory> = realm
                    .where(MenusItemsAttributeCategory::class.java)
                    .equalTo("restaurantId", MyApplication.restaurantId)
                    .equalTo(fieldName, fieldValue)
                    .equalTo("status", Status.active)
                    .findAllAsync()
                resdata = realm.copyFromRealm(allData) as MutableList<MenusItemsAttributeCategory>
            } catch (error: Exception) {
                Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
            } finally {
                realm.close()
            }
        }
        return resdata
    }


    /*fun getAllActiveDataByField(fieldName: String, fieldValue: Int): MutableList<MenusItemsAttributeCategory> {
        var resdata: MutableList<MenusItemsAttributeCategory> = mutableListOf()
        val realm = AppDatabase.getRealmInstance()
        try {
            val allData: RealmResults<MenusItemsAttributeCategory> = realm
                .where(MenusItemsAttributeCategory::class.java)
                //.equalTo("restaurantId", MyApplication.restaurantId)
                .equalTo(fieldName, fieldValue)
                .and()
                .equalTo("status", Status.active)
                .findAll()
            resdata = realm.copyFromRealm(allData)
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }*/


    fun isDataPresent(menuItemsId: Int, attributeCategoryId: Int): MenusItemsAttributeCategory? {
        val realm = AppDatabase.getRealmInstance()
        var mMenuItemPrinter: MenusItemsAttributeCategory? = null
        try {
            mMenuItemPrinter = realm
                .where<MenusItemsAttributeCategory>()
                //.equalTo("restaurantId", MyApplication.restaurantId)
                .and()
                .equalTo("menuItemsId", menuItemsId)
                .and()
                .equalTo("attributeCategoryId", attributeCategoryId)
                .and()
                .equalTo("status", Status.active)
                .findFirst()
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return mMenuItemPrinter
    }

    fun deleteMenuItemAttributeCategory(menuItemAttributeCategory: MenusItemsAttributeCategory) {
        val realm = AppDatabase.getRealmInstance()
        try {
            realm.executeTransaction {
                menuItemAttributeCategory.deleteFromRealm()
            }
        } catch (error: Exception) {
        } finally {
            realm.close()
        }
    }

    fun deleteMenuItemAttributeCategory(menuItemAttributeCategoryId: Int) {
        val realm = AppDatabase.getRealmInstance()
        try {
            val menuItemAttributeCategory = realm.where(MenusItemsAttributeCategory::class.java).equalTo("id", menuItemAttributeCategoryId).findFirst()
            realm.executeTransaction {
                menuItemAttributeCategory?.let {
                    it.status = Status.deleted
                    it.backupFlag = false
                    realm.insertOrUpdate(it)
                }
            }
        } catch (error: Exception) {
        } finally {
            realm.close()
        }
    }

    fun deleteAllData() {
        val realm = AppDatabase.getRealmInstance()
        try {
            val allData = realm.where(MenusItemsAttributeCategory::class.java).findAll()
            realm.executeTransaction { allData.deleteAllFromRealm() }
        } catch (error: Exception) {
            logd("Error --> $error")
        } finally {
            realm.close()
        }
    }

}