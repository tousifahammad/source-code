package com.example.motionlayoutdemo.list.realm_rv

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motionlayoutdemo.R
import com.example.motionlayoutdemo.data.db.AppDatabase
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_ream_rv.*


class RealmRvActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private var moduleList: MutableList<Module> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ream_rv)

        setupRv()
    }

    private fun setupRv() {
        val jsonStr = "[ { id:100, name:100 }, {id:200, name:200} , {id:200, name:200} , {id:200, name:200}, {id:200, name:200}, {id:200, name:200}, {id:200, name:200} ]"

        realm = AppDatabase.getRealmInstance()
        try {
            var modules = realm.where(Module::class.java).findAll()
            if (modules.size == 0) {
                realm.beginTransaction()
                realm.createAllFromJson(Module::class.java, jsonStr)
                realm.commitTransaction()
                modules = realm.where(Module::class.java).findAll()
            }

            moduleList = modules.toMutableList()

            recycler_view.setHasFixedSize(false)
            recycler_view.layoutManager = LinearLayoutManager(this)
            recycler_view.adapter = ModuleAdapter(modules)

        } catch (error: Exception) {
            realm.close()
            error.printStackTrace()
        }
    }

    fun addRealm(view: View) {
        realm = AppDatabase.getRealmInstance()
        try {
            val jsonStr = "[ { id:100, name:100 } ]"
            realm.beginTransaction()
            realm.createAllFromJson(Module::class.java, jsonStr)
            realm.commitTransaction()
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }

    fun deleteRealm(view: View) {
        if (moduleList.isNotEmpty()) {
            realm.executeTransaction {
                moduleList.last().deleteFromRealm()
                moduleList.removeLast()
            }
        }
    }

    fun updateRealm(view: View) {
        if (moduleList.isNotEmpty()) {
            realm.where(Module::class.java).findFirst()?.let {
                realm.executeTransaction { realm ->
                    it.id += 10
                    it.name += "  new"
                    realm.insertOrUpdate(it)
                }
            }

        }
    }


}