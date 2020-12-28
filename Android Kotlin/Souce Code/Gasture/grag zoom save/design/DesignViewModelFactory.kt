package net.app.mvvmsampleapp.floor.design

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.app.mvvmsampleapp.data.repositories.UserRepository

@Suppress("UNCHECKED_CAST")
class DesignViewModelFactory(
    private val repository: DesignRepository,
    private val context: Context
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DesignViewModel(repository, context) as T
    }
}