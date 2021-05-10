package com.webgrity.tisha.util.extensions

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.util.DisplayMetrics
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.webgrity.tisha.R

/**Extension method: to play notification sound.*/
fun Context.playNotificationSound() {
    trycatch {
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(this, notification)
        r.play()
    }
}


private fun Context.convertDpToPixel(dp: Float): Float {
    return dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}


inline fun Context.alertDialog(message: String = "", title: String = "", positiveMsg: String = "Yes", cancelable: Boolean = true, crossinline positiveFun: () -> Unit = {}, crossinline negativeFun: () -> Unit = {}) {
    MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme).apply {
        if (title.isNotEmpty()) setTitle(title)
        if (message.isNotEmpty()) setMessage(message)
        setCancelable(cancelable)
        setPositiveButton(positiveMsg) { _, _ ->
            positiveFun()
        }
        setNegativeButton("No") { _, _ ->
            negativeFun()
        }
        show()
    }
}