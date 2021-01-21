package com.example.motionlayoutdemo.realm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.motionlayoutdemo.R
import com.example.motionlayoutdemo.data.db.AppDatabase
import com.example.motionlayoutdemo.data.realm_objects.Invoice
import com.example.motionlayoutdemo.data.models.Status
import com.example.motionlayoutdemo.util.asLiveData
import com.example.motionlayoutdemo.data.db.RealmLiveData
import io.realm.Realm
import kotlinx.coroutines.launch

class ReamActivity : AppCompatActivity() {
    var realm: Realm = AppDatabase.getRealmInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ream)

        observeInvoiceByTable(1).observe(this) { list ->
            lifecycleScope.launch {
                if (list.size > 0 && list[0] != null) {
                    //updateInvoiceData(list[0]!!)
                }
            }
        }
    }

    private fun observeInvoiceByTable(tableId: Int): RealmLiveData<Invoice> {
        return realm.where(Invoice::class.java)
            .equalTo("tablesId", tableId)
            .equalTo("status", Status.seated)
            .beginGroup()
            .equalTo("orderTypeId", Status.seated)
            .or()
            .equalTo("orderTypeId", Status.onlineSeated)
            .or()
            .equalTo("orderTypeId", Status.onlineWebSeated)
            .endGroup()
            .findAllAsync()
            .asLiveData()
    }
}