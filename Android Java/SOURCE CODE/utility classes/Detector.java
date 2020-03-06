package com.app.theshineindia.theft_detection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.widget.Toast;

public class Detector {

    
    ==================================================================================
    public static boolean isChargerConnected(Context context) {
        try {
            Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            return plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB || plugged ==           BatteryManager.BATTERY_PLUGGED_WIRELESS;
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return false;
    }


    ==================================================================================

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



    ==================================================================================

    public static void isLocationEnable(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setMessage("GPS network not enabled")
                    .setPositiveButton("Enable", (paramDialogInterface, paramInt) -> context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    //.setNegativeButton("Cancel", null)
                    .show();
        }
    }

