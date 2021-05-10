package com.experiments.preferencehelper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.experiments.preferencehelper.PreferenceHelper.get
import com.experiments.preferencehelper.PreferenceHelper.set

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get default prefs
        val prefs = PreferenceHelper.defaultPrefs(this)

        //set any type of value in prefs
        prefs[Constants.PREF_NAME] = "name"

        //get any value from prefs
        val name: String? = prefs[Constants.PREF_NAME]

        //get value from prefs (with default value)
        val age: Int? = prefs[Constants.PREF_AGE, 23]

        Log.d(TAG,"name : $name")
        Log.d(TAG,"age : $age")
    }
}
