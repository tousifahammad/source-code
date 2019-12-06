package com.app.theshineindia.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

import com.app.theshineindia.baseclasses.SharedMethods;
import com.app.theshineindia.utils.AppData;


public class ChargingBR extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

        if (plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
            AppData.Sensor_Type.is_charger_plugged_in = true;

        } else if (AppData.Sensor_Type.is_charger_detection_on) {
            AppData.Sensor_Type.is_charger_detection_on = false;    //to detect only one time
            AppData.Sensor_Type.is_charger_plugged_in = false;

            Toast.makeText(context, "Charger disconnected", Toast.LENGTH_SHORT).show();
            SharedMethods.playAlarm(context, AppData.Sensor_Name.charger);
        }
    }
}
