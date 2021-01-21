package com.example.motionlayoutdemo.list.data_binding.recycler_view

import androidx.lifecycle.ViewModel

class MoviesViewModel : ViewModel(){
    init {

    }

    override fun onCleared() {
        try {

        } catch (error: Exception) {
            error.printStackTrace()
        }
        super.onCleared()
    }

}