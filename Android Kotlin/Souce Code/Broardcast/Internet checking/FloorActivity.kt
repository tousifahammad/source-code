

<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />



class FloorActivity : AppCompatActivity(), InternetListener {


    //==================== Internet Connectivity ===========================
    override fun onResume() {
        super.onResume()

        registerReceiver(
            InternetReceiver(this),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(InternetReceiver(this))
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            tv_internet.text = "Connected"
        }else{
			tv_internet.text = "Not Connected"
        }
    }
    //==================== Internet Connectivity ===========================

}
