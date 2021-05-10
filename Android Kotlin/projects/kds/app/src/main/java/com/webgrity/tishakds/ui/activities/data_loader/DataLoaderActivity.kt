package com.webgrity.tishakds.ui.activities.data_loader

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.webgrity.tishakds.R
import com.webgrity.tishakds.app.MyApplication
import com.webgrity.tishakds.app.SharedMethods
import com.webgrity.tishakds.data.db.AppDatabase
import com.webgrity.tishakds.databinding.ActivityDataLoaderBinding
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_data_loader.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class DataLoaderActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    val viewModel: DataLoaderViewModel by instance()
    private lateinit var binding: ActivityDataLoaderBinding
    private var realm: Realm? = null
    private var menuItemPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_loader)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        SharedMethods.setStatusBarColor(this, MyApplication.statusBarColor)
        realm = AppDatabase.getRealmInstance()
        progressBar4.indeterminateTintList = ColorStateList.valueOf(MyApplication.textColor)

        initUI()
        viewModel.deleteAndFetchData()
    }

    override fun onBackPressed() {
        //do nothing
    }

    private fun initUI() {
        if (MyApplication.restaurantName.isNotEmpty()) {
            binding.tvRestaurantName.text = MyApplication.restaurantName
            binding.tvRestaurantName.setTextColor(MyApplication.textColor)
        }
    }

}