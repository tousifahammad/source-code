package com.example.motionlayoutdemo.util

import android.app.Activity
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.util.Patterns
import androidx.annotation.IntRange
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.sign

fun Date?.convertToString(
    outputFormat: String = "HH:mm:ss",
    localeId: Locale = Locale.getDefault()
): String {
    if (this != null) {
        val requiredFormat = SimpleDateFormat(outputFormat, localeId)
        try {
            return requiredFormat.format(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return ""
}

fun Date?.format(outputFormat: String = "yyyy-MM-dd HH:mm:ss",
                 localeId: Locale = Locale.getDefault()
):Date?{
    var cdate: Date? = null
    if (this != null) {
        val requiredFormat = SimpleDateFormat(outputFormat, localeId)
        try {
            cdate = requiredFormat.parse(requiredFormat.format(this))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return cdate
}

fun String.convertToDate(format: String = "yyyy-MM-dd", localeId: Locale = Locale.getDefault()): Date? {
    try {
        return SimpleDateFormat(format, localeId).parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun Boolean.convertToInt(): Int {
    return if (this) 1 else 0
}

fun String.isNotNull(): Boolean {
    return this != "null"
}

fun Float.isNegative(): Boolean {
    return this < 0
}

fun Float.toDecimal(): Float {
    /*val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.UP
    return df.format(this).toFloat()*/
    /*val factor = 10.0.pow(2.toDouble())
    return ((this * factor).roundToInt() / factor).toFloat()*/
    return "%.${2}f".format(Locale.ENGLISH, this).toFloat()
}

fun Float.toCeiling(): Float {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toFloat()
}

fun Activity.hasPermission(vararg permissions: String): Boolean {
    return permissions.all { singlePermission ->
        applicationContext.checkSelfPermission(singlePermission) == PackageManager.PERMISSION_GRANTED
    }
}

fun Activity.askPermission(vararg permissions: String, @IntRange(from = 0) requestCode: Int) =
    ActivityCompat.requestPermissions(this, permissions, requestCode)

fun Activity.isUserCheckNeverAskAgain(vararg permissions: String): Boolean {
    return permissions.all { singlePermission ->
        !ActivityCompat.shouldShowRequestPermissionRationale(this, singlePermission)
    }
}

/**Create Factory Class at runtime*/
inline fun <reified T : ViewModel> Fragment.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null)
        ViewModelProvider(this).get(T::class.java)
    else
        ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null)
        ViewModelProvider(this).get(T::class.java)
    else
        ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
}

/**Validate String Email*/
fun String.isValidEmail(): Boolean = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

/** Log functions with Tag as caller class name */
inline fun <reified T : Any> T.logD(text: String) {
    Log.d(T::class.java.simpleName, "TISHA ===> $text")
}

/**Float To String*/
val Float.counterToString
    get() = if (this > toInt()) toString() else toInt().toString()

/**Check Connectivity*/
val Context.isConnected: Boolean
    get() {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val nw = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
            else -> {
                // Use depreciated methods only on older devices
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                nwInfo.isConnected
            }
        }
    }

/**Check Internet*/
val Context.isNetworkConnectionAvailable: Boolean
    get() {
        return if (isConnected) {
            try {
                val command = "ping -c 1 google.com"
                Runtime.getRuntime().exec(command).waitFor() == 0
            } catch (e: java.lang.Exception) {
                false
            }
        } else {
            false
        }
    }

/**Extension method for vibration.*/
fun Context.vibrate(duration: Long) {
    val vib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vib.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vib.vibrate(duration)
    }
}


/**Ext for Wifi Manager*/
inline val Context.wifiManager: WifiManager
    get() = getSystemService(Context.WIFI_SERVICE) as WifiManager

/**Extension method to get connectivityManager for Context.*/
inline val Context.connectivityManager: ConnectivityManager?
    get() = getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager


/**Float Conversion*/
val Float.conversion: String
    get() {
        return when (sign) {
            1.0F -> {
                /*Positive*/
                if (this > toInt()) toString() else toInt().toString()
            }
            -1.0F -> {
                /*Negative*/
                if (this < toInt()) toString() else toInt().toString()
            }
            0F -> {
                /*Zero*/
                toInt().toString()
            }
            else -> "?"
        }
    }