package com.app.theshineindia.app_locker;

import android.content.Context;
import android.os.PowerManager;

import com.app.theshineindia.app_locker.activities.lock.GestureUnlockActivity;
import com.app.theshineindia.app_locker.base.BaseActivity;
import com.app.theshineindia.app_locker.utils.SpUtil;
import com.app.theshineindia.secret_code.AppContext;
import com.app.theshineindia.secret_code.OnScreenOffReceiver;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;


public class LockApplication extends LitePalApplication {

    private static LockApplication application;
    private PowerManager.WakeLock wakeLock;


    public static LockApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }



    public PowerManager.WakeLock getWakeLock() {
        if (wakeLock == null) {
            // lazy loading: first call, create wakeLock via PowerManager.
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, ":wakeup");
        }
        return wakeLock;
    }
}
