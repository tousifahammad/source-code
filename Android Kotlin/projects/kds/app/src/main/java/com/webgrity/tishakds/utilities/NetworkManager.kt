package com.webgrity.tishakds.utilities

import android.content.Context


object NetworkManager {

    fun isWifiEnabled(applicationContext: Context): Boolean {
        /*val connMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connMgr ?: return false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network: Network = connMgr.activeNetwork ?: return false
            val capabilities = connMgr.getNetworkCapabilities(network)
            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            val networkInfo = connMgr.activeNetworkInfo ?: return false
            return networkInfo.isConnected && networkInfo.type == ConnectivityManager.TYPE_WIFI
        }*/
        return false
    }
}