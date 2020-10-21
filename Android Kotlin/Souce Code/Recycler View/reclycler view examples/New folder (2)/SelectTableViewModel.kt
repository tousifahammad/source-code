package com.webgrity.tisha.ui.activities.select_table

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.webgrity.tisha.app.AppData
import com.webgrity.tisha.data.models.FloorTable
import com.webgrity.tisha.data.repository.AuthRepository
import com.webgrity.tisha.data.socket.Message
import com.webgrity.tisha.data.socketmodels.FloorData
import com.webgrity.tisha.data.socketmodels.TablesData
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SelectTableViewModel(private val application: Application) : ViewModel(), KodeinAware {
    override val kodein by kodein { application }
    private val authRepository: AuthRepository by instance()

    var restaurantId = 0
    var restaurantName = ""
    var floorTableList = ArrayList<FloorTable>()
    var floorTableAdapter: FloorTableAdapter? = null

    fun getRestaurant() {
        authRepository.getAuthData()?.let {
            restaurantId = it.restaurantId
            restaurantName = it.restaurantName
        }
    }

    fun fetchResponse(message: Message) {
        floorTableList.clear()
        try {
            message.floorDetails.forEach { floorData ->
                addFloor(floorData)
                message.tableDetails.filter { it.floorId == floorData.id }.forEach {
                    addTable(it)
                }
            }

        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : autoCreateTable()", error)
        }
    }

    private fun addFloor(floorData: FloorData) {
        FloorTable().apply {
            this.type = "floor"
            this.floorId = floorData.id
            this.floorName = floorData.name
            floorTableList.add(this)
        }
    }

    private fun addTable(tableData: TablesData) {
        FloorTable().apply {
            this.type = "table"
            this.tableId = tableData.id
            this.tableName = tableData.name
            this.seatingCapacity = tableData.seatingCapacity
            floorTableList.add(this)
        }
    }
}