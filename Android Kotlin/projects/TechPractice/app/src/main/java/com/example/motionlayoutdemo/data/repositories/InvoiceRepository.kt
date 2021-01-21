package com.example.motionlayoutdemo.data.repositories

import com.example.motionlayoutdemo.app.MyApplication
import com.example.motionlayoutdemo.data.db.AppDatabase
import com.example.motionlayoutdemo.data.realm_objects.Invoice
import com.example.motionlayoutdemo.data.models.Status
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class InvoiceRepository {

    suspend fun insertOrUpdateToRealm(res: Invoice, backupFlag: Boolean = false, mRealm: Realm? = null): Invoice {
        return withContext(Dispatchers.Main) {
            val realm = mRealm ?: AppDatabase.getRealmInstance()
            try {
                realm.executeTransaction { realmTransaction ->
                    val rest: Invoice? = realmTransaction.where<Invoice>().equalTo("id", res.id).findFirst()
                    if (rest == null && res.id == 0) {
                        val results: RealmResults<Invoice> = realmTransaction.where(Invoice::class.java).findAll()
                        res.id = if (results.size == 0) 1 else Objects.requireNonNull<Number>(realmTransaction.where(Invoice::class.java).max("id")).toInt() + 1
                        if (res.orderId == 0) {
                            var maxOrderId = 0
                            val orderNumber = 111
                            realmTransaction.where(Invoice::class.java).max("orderId")?.let { num ->
                                maxOrderId = num.toInt()
                            }
                            res.orderId = if (results.size == 0) orderNumber else {
                                if (maxOrderId < orderNumber) orderNumber
                                else maxOrderId + 1
                            }
                        }
                    }
                    if (res.restaurantId == 0) res.restaurantId = MyApplication.restaurantId
                    res.backupFlag = backupFlag
                    realmTransaction.insertOrUpdate(res)
                }
            } catch (error: Exception) {

            } finally {
                if (mRealm == null) realm.close()
            }
            res
        }
    }

    suspend fun getAllData(): ArrayList<Invoice>? {
        return withContext(Dispatchers.Main) {
            var resdata: ArrayList<Invoice>? = null
            val realm = AppDatabase.getRealmInstance()
            try {
                val alldata: RealmResults<Invoice> = realm.where(Invoice::class.java)
                    .equalTo("restaurantId", MyApplication.restaurantId)
                    .notEqualTo("status", Status.deleted)
                    .findAllAsync()
                resdata = realm.copyFromRealm(alldata) as ArrayList<Invoice>?
            } catch (error: Exception) {
            } finally {
                realm.close()
            }
            resdata
        }
    }

    suspend fun getDataById(id: Int): Invoice? {
        return withContext(Dispatchers.Main) {
            val realm = AppDatabase.getRealmInstance()
            var invoiceCategory: Invoice? = null
            try {
                val mInvoiceCategory: Invoice = realm.where<Invoice>().equalTo("id", id).findFirstAsync()
                mInvoiceCategory.load()
                if (mInvoiceCategory.isLoaded && mInvoiceCategory.isValid) invoiceCategory = realm.copyFromRealm(mInvoiceCategory)
            } catch (error: Exception) {
            } finally {
                realm.close()
            }
            invoiceCategory
        }
    }

    suspend fun deleteById(id: Int) {
        withContext(Dispatchers.IO) {
            val realm = AppDatabase.getRealmInstance()
            try {
                val selectedData = realm.where(Invoice::class.java).equalTo("id", id).findFirst()
                realm.executeTransaction { realmTransaction ->
                    selectedData?.let {
                        it.status = Status.deleted
                        it.backupFlag = false
                        realmTransaction.insertOrUpdate(it)
                    }
                }
            } catch (error: Exception) {
            } finally {
                realm.close()
            }
        }
    }

    fun deleteAllForTest() {
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<Invoice>? = realm.where(Invoice::class.java).findAll()
            realm.executeTransaction { alldata?.deleteAllFromRealm() }
        } catch (error: Exception) {
        } finally {
            realm.close()
        }
    }
}