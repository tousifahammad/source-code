package com.webgrity.tisha.app

import android.util.Log
import android.view.View
import android.view.ViewGroup


object SharedMethods {

    //get all child view from group view
    fun getAllChildrenFromViewGroup(view: View): List<View>? {
        val viewList: ArrayList<View> = ArrayList()
        try {
            if (view !is ViewGroup) {
                viewList.add(view)
                return viewList
            }

            for (i in 0 until view.childCount) {
                val childView: View = view.getChildAt(i)
                //Do not add any parents, just add child elements
                getAllChildrenFromViewGroup(childView)?.let { viewList.addAll(it) };
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }

        return viewList
    }
}