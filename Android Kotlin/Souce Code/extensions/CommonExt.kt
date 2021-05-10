package com.webgrity.tisha.util.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IntRange
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.webgrity.tisha.R
import com.webgrity.tisha.app.MyApplication
import com.webgrity.tisha.data.types.Status
import com.webgrity.tisha.util.Alert
import com.webgrity.tisha.util.BaseViewModelFactory
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/** Log functions with Tag as caller class name */
inline fun <reified T : Any> T.logD(text: String) {
    Log.d(T::class.java.simpleName, "TISHA ===> $text")
}

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

fun Activity.isHasPermission(vararg permissions: String): Boolean {
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

/**Validate String Email*/
fun String.isValidEmail(): Boolean = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

/** Log functions with Tag as caller class name */
inline fun <reified T : Any> T.logd(text: String) {
    Log.d("TISHA ===> ${T::class.java.simpleName}", text)
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

fun String.removeLast(): String {
    if (this.isNotEmpty()) {
        return this.trim().substring(0, this.length - 1)
    }
    return this
}

fun String.removeFirstAndLast(): String {
    if (this.isNotEmpty()) {
        return this.trim().substring(1, this.length - 1)
    }
    return this
}


fun Context.isWifiConnected(): Boolean {
    try {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    } catch (error: Exception) {
        error.printStackTrace()
    }
    return false
}

fun AppCompatActivity.isWifiConnected(showAlert: Boolean = false): Boolean {
    var isWifiConnected = false
    try {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        isWifiConnected = networkInfo != null && networkInfo.isConnectedOrConnecting
        if (!isWifiConnected && showAlert) Alert(this, Alert.error, getString(R.string.Wifi_not_connected))
    } catch (error: Exception) {
        error.printStackTrace()
    }
    return isWifiConnected
}

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

fun Activity.checkRotation() {
    try {
        this.requestedOrientation = when (MyApplication.type) {
            Status.table -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            Status.waiter -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
            else -> ActivityInfo.SCREEN_ORIENTATION_USER
        }
    } catch (e: Exception) {
        e.printStackTrace()
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

/**Extension method to get connectivityManager for Context.*/
inline val Context.connectivityManager: ConnectivityManager?
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

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

fun FragmentActivity.onBackPressed(function: (() -> Unit)) {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            function()
        }
    })
}

inline fun trycatch(function: (() -> Unit)) {
    try {
        function()
    } catch (error: Exception) {
        error.printStackTrace()
    }
}