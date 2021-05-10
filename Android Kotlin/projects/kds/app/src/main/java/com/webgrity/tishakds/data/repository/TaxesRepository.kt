package com.webgrity.tishakds.data.repository

import android.util.Log
import com.webgrity.tishakds.app.MyApplication
import com.webgrity.tishakds.data.db.AppDatabase
import com.webgrity.tishakds.data.entities.Taxes
import com.webgrity.tishakds.app.AppData
import com.webgrity.tishakds.data.constants.Status
import com.webgrity.tishakds.utilities.extensions.logd
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class TaxesRepository {

    fun insertOrUpdateToRealm(res: Taxes, backupFlag: Boolean = false, realmIns: Realm? = null): Int {
        var id = 0
        val realm = realmIns ?: AppDatabase.getRealmInstance()
        try {
            realm.executeTransaction {
                val rest: Taxes? = getDataById(res.id)
                if (rest == null && res.id == 0) {
                    val results: RealmResults<Taxes> =
                        it.where(Taxes::class.java).findAll()
                    id = if (results.size == 0) 1 else {
                        Objects.requireNonNull<Number>(
                            it.where(Taxes::class.java).max("id")
                        ).toInt() + 1
                    }
                    res.id = id
                }
                if (res.restaurantId == 0) res.restaurantId = MyApplication.restaurantId
                res.backupFlag = backupFlag
                it.insertOrUpdate(res)
                //Log.d(AppData.TAG, "One row inserted")
            }
        } catch (error: Exception) {
            id = 0
            //Log.d(AppData.TAG, "Error insertOrUpdateToRealm:", error)
            error.printStackTrace()
        } finally {
            if (realmIns == null) realm.close()
        }
        return id
    }

    fun insertList(list: MutableList<Taxes>, realmIns: Realm? = null) {
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


    fun getAllData(restaurantId: Int = 0): ArrayList<Taxes>? {
        var resdata: ArrayList<Taxes>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<Taxes> = realm.where(Taxes::class.java)
                .equalTo("restaurantId", if (restaurantId == 0) MyApplication.restaurantId else restaurantId)
                .equalTo("status", Status.active)
                .findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<Taxes>?
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error :", error)
        } finally {
            realm.close()
        }

        return resdata
    }

    fun getFirstInactiveData(): Taxes? {
        var resdata: Taxes? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val data = realm.where(Taxes::class.java)
                .equalTo("restaurantId", MyApplication.restaurantId)
                .equalTo("status", Status.deleted)
                .findFirst()
            data?.let { resdata = realm.copyFromRealm(data) }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : getFirstInactiveData() :", error)
        } finally {
            realm.close()
        }

        return resdata
    }

    fun getDataById(id: Int): Taxes? {
        val realm = AppDatabase.getRealmInstance()
        var taxes: Taxes? = null
        try {
            val mTaxes: Taxes? = realm.where<Taxes>().equalTo("id", id).findFirst()
            if (mTaxes != null) {
                taxes = realm.copyFromRealm(mTaxes)
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error :", error)
        } finally {
            realm.close()
        }
        return taxes
    }

    fun getDataByRestaurantId(id: Int): ArrayList<Taxes>? {
        val realm = AppDatabase.getRealmInstance()
        var taxes: ArrayList<Taxes>? = null
        try {
            val mTaxes: RealmResults<Taxes>? = realm.where<Taxes>()
                .equalTo("restaurantId", id)
                //.equalTo("status", Status.active)
                .findAll()
            if (mTaxes != null) {
                taxes = realm.copyFromRealm(mTaxes) as ArrayList<Taxes>
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error :", error)
        } finally {
            realm.close()
        }
        return taxes
    }

    fun restoreDataFromServer(taxJson: JSONObject) {
        try {
            val tax = Taxes()
            tax.id = taxJson.getInt("id")
            tax.restaurantId = taxJson.getInt("restaurantId")
            tax.name = taxJson.getString("name")
            tax.percent = taxJson.getString("percent").toFloat()
            tax.registration = taxJson.getString("registration")
            tax.status = taxJson.getInt("status")

            insertOrUpdateToRealm(tax, true)

        } catch (exp: JSONException) {
            Log.d("tishakds==>>", "$exp")
        }
    }

    fun deleteAllData() {
        val realm = AppDatabase.getRealmInstance()
        try {
            val allData = realm.where(Taxes::class.java).findAll()
            realm.executeTransaction { allData.deleteAllFromRealm() }
        } catch (error: Exception) {
            logd("Error --> $error")
        } finally {
            realm.close()
        }
    }

}