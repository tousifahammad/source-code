package com.webgrity.tishakds.ui.activities.launcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.webgrity.tishakds.R
import com.webgrity.tishakds.app.MyApplication
import com.webgrity.tishakds.app.SharedMethods
import com.webgrity.tishakds.data.repository.AuthRepository
import com.webgrity.tishakds.data.constants.Status
import com.webgrity.tishakds.networking.API
import com.webgrity.tishakds.utilities.extensions.logd
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LauncherActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val authRepository: AuthRepository by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        SharedMethods.setStatusBarColor(this, MyApplication.statusBarColor)
        try {
            checkActivity()
//            gotToActivityWithoutStack(FeedbackActivity::class.java)
        } catch (error: Exception) {
            logd("Error:  ==> $error")
        }

    }

    private fun checkActivity() {
        authRepository.getAuthData().also {
            if (it.serverIp.isEmpty() || it.restaurantId == Status.none || it.restaurantName.isEmpty() || it.type == Status.none) {
                //gotToActivityWithoutStack(IpActivity::class.java)

            } else {
                API.serverIp = it.serverIp
                MyApplication.restaurantId = it.restaurantId
                MyApplication.restaurantName = it.restaurantName
                MyApplication.type = it.type
                MyApplication.tableId = it.tableId
                if (it.headerColor != 0) MyApplication.statusBarColor = it.headerColor
                if (it.textColor != 0) MyApplication.textColor = it.textColor

                //gotToActivityWithoutStack(DataLoaderActivity::class.java)
            }
        }
    }
}