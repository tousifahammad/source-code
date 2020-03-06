package com.app.theshineindia.secret_code;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.app.theshineindia.app_locker.LockApplication;

public class OnScreenOffReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            Log.d("1111", "OnScreenOffReceiver : SCREEN_OFF");

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
        Log.d("1111", "wakeLock : acquired");

        // ... and release again
        wakeLock.release();
        Log.d("1111", "wakeLock : released");
    }


}