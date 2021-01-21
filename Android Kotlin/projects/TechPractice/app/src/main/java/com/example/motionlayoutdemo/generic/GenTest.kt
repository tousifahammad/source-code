package com.example.motionlayoutdemo.generic

import android.util.Log

class GenTest {

    fun generic1(data: Int) {
        Log.d("1111", "GenTest generic1: $data")
    }

    fun <T> generic2(data: T) {
        Log.d("1111", "GenTest generic2: ${data.toString()}")
    }

    fun <T> generic3(data: T): T {
        Log.d("1111", "GenTest generic2: ${data.toString()}")
        return data
    }

    fun <T> genericList(list: List<T>): List<T> {
        /*list as ArrayList<T>
        list.add()
        list.add()
        list.add()
        list.add()*/
        return list
    }

}