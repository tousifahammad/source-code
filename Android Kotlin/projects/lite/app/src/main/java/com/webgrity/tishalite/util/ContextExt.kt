package com.webgrity.tishalite.util

import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.DisplayMetrics

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

/**Check Connectivity*/
val Context.isConnected: Boolean
    get() {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val nw = connectivityManager?.activeNetwork ?: return false
                val actNw = connectivityManager?.getNetworkCapabilities(nw) ?: return false
                when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
            else -> {
                // Use depreciated methods only on older devices
                val nwInfo = connectivityManager?.activeNetworkInfo ?: return false
                nwInfo.isConnected
            }
        }
    }

/**Check if the device has an internet connection
 * @return True if the device is connected to a network which also gives it access to the internet.
 * False otherwise.
 */
val Context.isInternetConnectionAvailable: Boolean
    get() {
        return if (isConnected) {
            val nw = connectivityManager?.activeNetwork ?: return false
            val actNw = connectivityManager?.getNetworkCapabilities(nw) ?: return false
            /*If we check only for "NET_CAPABILITY_INTERNET", we get "true" if we are connected to a wifi
            which has no access to the internet. "NET_CAPABILITY_VALIDATED" also verifies that we are online*/
            when {
                actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) -> true
                else -> false
            }
        } else {
            false
        }
    }

/**Ext for Wifi Manager*/
inline val Context.wifiManager: WifiManager
    get() = getSystemService(Context.WIFI_SERVICE) as WifiManager

/**Extension method to get connectivityManager for Context.*/
inline val Context.connectivityManager: ConnectivityManager?
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
private fun Context.convertDpToPixel(dp: Float): Float {
    return dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}