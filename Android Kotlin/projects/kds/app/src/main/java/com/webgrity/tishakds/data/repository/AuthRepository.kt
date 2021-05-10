package com.webgrity.tishakds.data.repository

import com.webgrity.tishakds.data.db.AppDatabase
import com.webgrity.tishakds.data.entities.Auth
import io.realm.RealmResults
import io.realm.kotlin.where

class AuthRepository {

    fun insertOrUpdateToRealm(res: Auth): Boolean {
        val realm = AppDatabase.getRealmInstance()
        try {
            realm.executeTransaction {
                it.insertOrUpdate(res)
            }
        } catch (error: Exception) {
            error.printStackTrace()
        } finally {
            realm.close()
        }
        return true
    }

    fun getAuthData(): Auth {
        val realm = AppDatabase.getRealmInstance()
        var auth = Auth()
        try {
            realm.where<Auth>().findFirst()?.let {
                auth = realm.copyFromRealm(it)
            }
        } catch (error: Exception) {
            error.printStackTrace()
        } finally {
            realm.close()
        }
        return auth
    }


    fun deleteAllData() {
        val realm = AppDatabase.getRealmInstance()
        try {
            val allData: RealmResults<Auth>? = realm.where(Auth::class.java).findAll()
            realm.executeTransaction { allData?.deleteAllFromRealm() }
        } catch (error: Exception) {
            error.printStackTrace()
        } finally {
            realm.close()
        }
    }

}