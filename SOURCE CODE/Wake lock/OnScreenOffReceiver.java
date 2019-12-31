package com.app.theshineindia.secret_code;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.app.theshineindia.app_locker.LockApplication;
import com.app.theshineindia.secret_code.AppContext;

public class OnScreenOffReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            LockApplication ctx = (LockApplication) context.getApplicationContext();
            wakeUpDevice(ctx);

        }
    }

    private void wakeUpDevice(LockApplication context) {
        PowerManager.WakeLock wakeLock = context.getWakeLock(); // get WakeLock reference via AppContext
        if (wakeLock.isHeld()) {
            wakeLock.release(); // release old wake lock
        }

        // create a new wake lock...
        wakeLock.acquire();

        // ... and release again
        wakeLock.release();
    }


}