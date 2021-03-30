package com.webgrity.tishakds.ui.activities.data_loader

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webgrity.tishakds.data.entities.MenuItems
import com.webgrity.tishakds.data.repository.*
import com.webgrity.tishakds.networking.API
import com.webgrity.tishakds.utilities.Alert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class DataLoaderViewModel(mKodein: Kodein) : ViewModel(), KodeinAware {
    override val kodein = mKodein
    private val authRepository: AuthRepository by instance()

    var nextRequestLD = MutableLiveData<String>()
    var requestImageLD = MutableLiveData<Int>()
    var showProgressLD = MutableLiveData<Boolean>()
    var alertMessageLD = MutableLiveData<Alert.Params>()

    var menuItemList: MutableList<MenuItems> = mutableListOf()


    fun deleteAndFetchData() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
            }
        } catch (error: Exception) {
            error.printStackTrace()

        } finally {
            showProgressLD.value = true
            nextRequestLD.value = API.menu_categories
        }
    }

}