package com.webgrity.tisha.ui.activities.select_table

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webgrity.tisha.R
import com.webgrity.tisha.app.API
import com.webgrity.tisha.data.socket.SocketListener
import com.webgrity.tisha.data.socket.Message
import com.webgrity.tisha.data.socket.SocketClient
import com.webgrity.tisha.databinding.ActivitySelectTableBinding
import com.webgrity.tisha.ui.listener.ClickListener
import kotlinx.android.synthetic.main.activity_select_table.*
import util.Alert
import util.Coroutines

class SelectTableActivity : AppCompatActivity(), SocketListener {
    lateinit var viewModel: SelectTableViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySelectTableBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_table)
        viewModel = ViewModelProvider(this, SelectTableFactory(this.application)).get(SelectTableViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setUpRecyclerView()
        getIntentData()
    }

    private fun setUpRecyclerView() {
        viewModel.floorTableAdapter = FloorTableAdapter(viewModel)
        rv_floor_table.adapter = viewModel.floorTableAdapter
    }

    private fun getIntentData() {
        viewModel.getRestaurant()
        tv_restaurant_name.text = viewModel.restaurantName

        requestGetTables()
    }

    private fun requestGetTables() {
        Message().also {
            it.requestType = API.GET_TABLE
            it.restaurantId = viewModel.restaurantId
            SocketClient(this, it).start()
        }
    }


    override fun callback(message: Message?) {
        if (API.isRequestSuccess(this, message)) {
            viewModel.fetchResponse(message!!)
        }
    }


    override fun errorResponse(error: String?) {
        Coroutines.main {
            Alert.showError(this, error.toString())
        }
    }

}