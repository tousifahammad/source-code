package com.example.motionlayoutdemo.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class PreferenceProvider(context: Context) {
    private val appContext = context.applicationContext
    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    companion object {
        const val USER_TYPE_ID = "USER_TYPE_ID"
        const val LAST_LOGGED_IN_RESTAURANT_ID = "LAST_LOGGED_IN_RESTAURANT_ID"
        const val POS_MENU_CATEGORY_VIEW_TYPE = "POS_MENU_CATEGORY_VIEW_TYPE"
        const val POS_MENUS_VIEW_TYPE = "POS_PAGE_MENUS_VIEW_TYPE"
        const val LOGGED_IN_USER = "LOGGED_IN_USER"
        const val APP_OPENING_TIME = "APP_OPENING_TIME"
        const val QUICK_SETUP_SKIPPED_RESTAURANT_IDS = "QUICK_SETUP_SKIPPED_RESTAURANT_IDS"
        const val COMPLETED_QUICK_SETUP_STEPS = "COMPLETED_QUICK_SETUP_STEPS"
        const val LAST_ONLINE_ORDER_COUNT = "LAST_ONLINE_ORDER_COUNT"
    }


    fun saveString(key: String, value: String) {
        preference.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return preference.getString(key, null)
    }

    fun deleteKey(key: String) {
        preference.edit().remove(key).apply()
    }


    fun saveBoolean(key: String, value: Boolean) {
        preference.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return preference.getBoolean(key, false)
    }

    fun saveInt(key: String, value: Int) {
        preference.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return preference.getInt(key, 0)
    }


    fun saveFloat(key: String, value: Float) {
        preference.edit().putFloat(key, value).apply()
    }

    fun getFloat(key: String): Float {
        return preference.getFloat(key, 0f)
    }


    fun saveLong(key: String, value: Long) {
        preference.edit().putLong(key, value).apply()
    }

    fun getLong(key: String): Long {
        return preference.getLong(key, 0)
    }

    fun saveArrayList(key: String, list: ArrayList<String>) {
        val json: String = Gson().toJson(list)
        preference.edit().putString(key, json).apply()
    }

    fun getArrayList(key: String): ArrayList<String> {
        var list = ArrayList<String>()
        val type: Type = object : TypeToken<ArrayList<String>>() {}.type
        preference.getString(key, null)?.let {
            list = Gson().fromJson(it, type)
        }
        return list
    }

}