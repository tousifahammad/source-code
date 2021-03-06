package com.example.motionlayoutdemo.util

import android.content.Context
import android.content.Intent


object ActivityController {

    fun gotToActivity(context: Context, cls: Class<*>?) {
        try {
            Intent(context, cls).also { context.startActivity(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun gotToActivityWithoutStack(context: Context, cls: Class<*>?) {
        try {
            Intent(context, cls).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun gotToActivityWithHashMap(context: Context, cls: Class<*>?, myMap: HashMap<String, String>) {
        try {
            Intent(context, cls).also {
                for (key in myMap.keys) {
                    it.putExtra(key, myMap[key])
                }
                context.startActivity(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}