package com.webgrity.tisha.background_process.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.webgrity.tisha.interfaces.InternetListener

class InternetReceiver(private var internetListener: InternetListener) : BroadcastReceiver() {

    override fun onReceive(context: Context, arg1: Intent) {
        internetListener.onNetworkConnectionChanged(isConnectedOrConnecting(context))
    }

    private fun isConnectedOrConnecting(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}