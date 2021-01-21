package com.example.motionlayoutdemo.util

import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build

/**Extension method: to another activity*/
fun Context.gotToActivity(cls: Class<*>) {
    try {
        Intent(this, cls).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            this.startActivity(it)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**Extension method: to another activity without stack. it clear all previous stack*/
fun Context.gotToActivityWithoutStack(cls: Class<*>) {
    try {
        Intent(this, cls).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.startActivity(it)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**Extension method: to another activity with parameters.*/
fun Context.gotToActivityWithHashMap(cls: Class<*>?, myMap: HashMap<String, String>) {
    try {
        Intent(this, cls).also {
            for (key in myMap.keys) {
                it.putExtra(key, myMap[key])
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            this.startActivity(it)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}



/**Extension method: to play notification sound.*/
fun Context.playNotificationSound() {
    try {
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(this, notification)
        r.play()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}