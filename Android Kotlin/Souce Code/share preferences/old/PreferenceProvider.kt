package com.webgrity.tishakds.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import java.lang.reflect.Type


class PreferenceProvider(context: Context) {
    private val appContext = context.applicationContext
    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    companion object {
        const val SERVER_IP_ADDRESS = "SERVER_IP_ADDRESS"
        const val WARNING_TIME = "WARNING_TIME"
        const val ALERT_TIME = "ALERT_TIME"
        const val COOKING_TIME_INDICATOR = "COOKING_TIME_INDICATOR"
        const val LAST_LOGIN_DATE = "LAST_LOGIN_DATE"
        const val LAST_INVOICE_SIZE = "LAST_INVOICE_SIZE"
        const val MODIFIER_IN_RED = "MODIFIER_IN_RED"
        const val IP_ADDRESS = "IP_ADDRESS"
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
        val type: Type = object : com.google.gson.reflect.TypeToken<ArrayList<String>>() {}.type
        preference.getString(key, null)?.let {
            list = Gson().fromJson(it, type)
        }
        return list
    }

}