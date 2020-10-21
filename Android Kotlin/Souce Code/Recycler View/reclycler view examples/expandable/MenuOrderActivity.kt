package com.webgrity.tisha.ui.activities.menuOrder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.webgrity.tisha.R
import com.webgrity.tisha.app.API
import com.webgrity.tisha.app.AppData
import com.webgrity.tisha.app.SharedMethods
import com.webgrity.tisha.data.socket.Message
import com.webgrity.tisha.data.socket.SocketClient
import com.webgrity.tisha.data.socket.SocketListener
import com.webgrity.tisha.databinding.ActivityMenuOrderBinding
import kotlinx.android.synthetic.main.activity_menu_order.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import util.Alert
import java.util.*


class MenuOrderActivity : AppCompatActivity(), KodeinAware, SocketListener {
    override val kodein by kodein()
    private val viewModel: MenuOrderViewModel by instance()
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMenuOrderBinding = DataBindingUtil.setContentView(this, R.layout.activity_menu_order)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setUpRecyclerView()
        initObservers()
        getStoredData()
        mytest()
    }

    private fun mytest() {
        //some predefined values:
        val parent1 = Parent(0)
        val childItems1 = ArrayList<Child>()
        childItems1.add(Child(parent1, 0, "test 1"))
        childItems1.add(Child(parent1, 1, "test 2"))
        childItems1.add(Child(parent1, 2, "test 3"))
        childItems1.add(Child(parent1, 3, "test 4"))
        parent1.childItems.clear()
        parent1.childItems.addAll(childItems1)

        val parent2 = Parent(1)
        val childItems2 = ArrayList<Child>()
        childItems2.add(Child(parent2, 9, "test 5"))
        childItems2.add(Child(parent2, 10, "test 6"))
        childItems2.add(Child(parent2, 11, "test 7"))
        parent2.childItems.clear()
        parent2.childItems.addAll(childItems2)

        val parent3 = Parent(2)
        val childItems3 = ArrayList<Child>()
        childItems3.add(Child(parent3, 12, "test 8"))
        childItems3.add(Child(parent3, 13, "test 9"))
        childItems3.add(Child(parent3, 14, "test 10"))
        parent3.childItems.clear()
        parent3.childItems.addAll(childItems3)

        val itemList = ArrayList<Item>()
        itemList.add(parent1)
        itemList.add(parent2)
        itemList.add(parent3)

        rv_menu_category_center.adapter = OnlyOneOpenedAdapter(itemList)
    }


    private fun initObservers() {
        viewModel.lastClickedMcIdLD.observe(this, Observer {
            //requestForMenu(it)
            Log.d(AppData.TAG, "lastClickedMenuCategoryIdLD: $it")
        })

        viewModel.nextRequestLD.observe(this, Observer {
            requestForMenu(it)
        })

        viewModel.showProgressLD.observe(this, Observer { showDialog ->
            try {
                if (alertDialog == null) alertDialog = SharedMethods.getAlertDialog(this)

                alertDialog?.let {
                    if (showDialog && !it.isShowing) it.show()
                    else if (it.isShowing) it.dismiss()
                }
            } catch (error: Exception) {
            }
        })
    }

    private fun setUpRecyclerView() {
        viewModel.menuCategoryAdapter = MenuCategoryAdapter(viewModel)
        rv_menu_category_left.adapter = viewModel.menuCategoryAdapter
    }

    private fun getStoredData() {
        viewModel.getAuthData()
        tv_restaurant_name.text = viewModel.restaurantName
        viewModel.getMenuCategories()
    }


    override fun onSocketCallback(message: Message?) {
        if (API.isRequestSuccess(this, message)) {

            when (message!!.requestType.toLowerCase(Locale.ROOT)) {

                API.login.toLowerCase(Locale.ROOT) -> {
                    //login verified
                    requestForMenu(API.menu_categories)
                }
                API.menu_categories.toLowerCase(Locale.ROOT) -> {
                    //viewModel.fetchMenuCategories(message.menuCategories)
                }
                API.sales_categories.toLowerCase(Locale.ROOT) -> {
                    viewModel.fetchSalesCategories(message.salesCategories)
                }
                API.menu_items.toLowerCase(Locale.ROOT) -> {
                    viewModel.fetchMenuItems(message.menuItems)
                }
                API.attribute_categories.toLowerCase(Locale.ROOT) -> {
                    viewModel.fetchAttributeCategories(message.attributeCategories)
                }
                API.attribute_categories_values.toLowerCase(Locale.ROOT) -> {
                    viewModel.fetchAttributeCategoriesValues(message.attributeCategoriesValues)
                }
            }

            viewModel.showProgressLD.value = false
        }
    }


    override fun onSocketError(error: String?) {
        viewModel.showProgressLD.value = false
        Alert.showError(this, error.toString())
    }


    private fun requestForMenu(requestType: String) {
        viewModel.showProgressLD.value = true

        Message().also {
            it.requestType = requestType
            it.restaurantId = viewModel.restaurantId
            SocketClient(this, it).start()
        }
    }


}