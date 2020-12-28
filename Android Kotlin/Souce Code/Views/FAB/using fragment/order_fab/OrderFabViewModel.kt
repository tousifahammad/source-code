package com.webgrity.tisha.ui.fragment.order_fab

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webgrity.tisha.R
import com.webgrity.tisha.app.MyApplication
import com.webgrity.tisha.data.db.AppDatabase
import com.webgrity.tisha.data.db.RealmLiveData
import com.webgrity.tisha.data.entities.Invoice
import com.webgrity.tisha.data.entities.OnlineInvoice
import com.webgrity.tisha.data.models.Status
import com.webgrity.tisha.data.preferences.PreferenceProvider
import com.webgrity.tisha.data.preferences.PreferenceProvider.Companion.LAST_ONLINE_ORDER_COUNT
import com.webgrity.tisha.data.repositories.TableRepository
import com.webgrity.tisha.ui.customview.fab.Fab
import com.webgrity.tisha.ui.customview.fab.FabAdapter
import com.webgrity.tisha.util.asLiveData
import com.webgrity.tisha.util.logD
import io.realm.Realm
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class OrderFabViewModel(mkodein: Kodein) : ViewModel(), KodeinAware {
    override val kodein = mkodein
    val preferenceProvider: PreferenceProvider by instance()
    val tableRepository: TableRepository by instance()

    var realm: Realm = AppDatabase.getRealmInstance()

    var fabAdapter: FabAdapter? = null
    var onlineFab: Fab
    val totalFabList = ArrayList<Fab>()
    private val billFabList = ArrayList<Fab>()

    var totalOrderCount: MutableLiveData<Int> = MutableLiveData()
    var fabVisibility: MutableLiveData<Boolean> = MutableLiveData()
    var playSound: MutableLiveData<Boolean> = MutableLiveData()

    init {
        onlineFab = Fab().also { fab ->
            fab.type = "order"
            fab.name = "Online Order"
            fab.count = 0
            fab.icon = R.drawable.ic_new_order
        }
    }

    override fun onCleared() {
        try {
            realm.removeAllChangeListeners()
            realm.close()
        } catch (error: Exception) {
            error.printStackTrace()
        }
        super.onCleared()
    }


    fun observeOnlineOrder(): RealmLiveData<OnlineInvoice> {
        return realm.where(OnlineInvoice::class.java)
            .equalTo("restaurantId", MyApplication.restaurantId)
            .equalTo("accepted", Status.pending)
            .findAllAsync()
            .asLiveData()
    }

    fun observeSeatedInvoice(): RealmLiveData<Invoice> {
        return realm.where(Invoice::class.java)
            .equalTo("restaurantId", MyApplication.restaurantId)
            .equalTo("status", Status.seated)
            .equalTo("billStatus", Status.billRequested)
            .findAllAsync()
            .asLiveData()
    }

    fun updateBills(invoiceList: MutableList<Invoice>) {
        try {
            viewModelScope.launch {
                try {
                    billFabList.clear()
                    invoiceList.forEach { invoice ->
                        TableRepository().getDataById(invoice.tablesId, realm)?.let { table ->
                            Fab().also { fab ->
                                fab.type = "bill"
                                fab.name = table.name
                                fab.count = 1
                                fab.invoiceId = invoice.id
                                fab.icon = R.drawable.ic_bill_printed
                                billFabList.add(fab)
                            }
                        }
                    }
                } catch (error: Exception) {
                    error.printStackTrace()
                } finally {
                    updateFabView()
                }
            }
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }

    fun updateFabView() {
        try {
            totalFabList.clear()
            if (onlineFab.count > 0) {
                totalFabList.add(onlineFab)
            }
            if (billFabList.size > 0) {
                totalFabList.addAll(billFabList)
            }
            fabAdapter?.notifyDataSetChanged()

            val count = totalFabList.map { it.count }.sum()
            totalOrderCount.value = count
            fabVisibility.value = count > 0

            if (count > preferenceProvider.getInt(LAST_ONLINE_ORDER_COUNT)) {
                playSound.value = true
            }
            preferenceProvider.saveInt(LAST_ONLINE_ORDER_COUNT, count)
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }
}