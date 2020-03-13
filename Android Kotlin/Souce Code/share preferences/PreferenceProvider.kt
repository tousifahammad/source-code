package net.simplifiedcoding.mvvmsampleapp.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


class PreferenceProvider(context: Context) {
    private val appContext = context.applicationContext
    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    companion object {
        private const val CURRENT_USER_ID = "CURRENT_USER_ID"
    }


    fun saveString(key: String, value: String) {
        preference.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return preference.getString(key, null)
    }


    fun saveBoolean(key: String, value: Boolean) {
        preference.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean? {
        return preference.getBoolean(key, false)
    }

    fun saveInt(key: String, value: Int) {
        preference.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int? {
        return preference.getInt(key, 0)
    }


    fun saveFloat(key: String, value: Float) {
        preference.edit().putFloat(key, value).apply()
    }

    fun getFloat(key: String): Float? {
        return preference.getFloat(key, 0f)
    }


    fun saveLong(key: String, value: Long) {
        preference.edit().putLong(key, value).apply()
    }

    fun getLong(key: String): Long? {
        return preference.getLong(key, 0)
    }

}