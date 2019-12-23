package com.app.theshineindia.theft_detection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.widget.Toast;

public class Detector {

    public static boolean isChargerConnected(Context context) {
        try {
            Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            return plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS;
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public static boolean isHeadsetConnected(Context context) {
        boolean isHeadsetConnected = false;
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            isHeadsetConnected = audioManager.isWiredHeadsetOn();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return isHeadsetConnected;
    }
}
