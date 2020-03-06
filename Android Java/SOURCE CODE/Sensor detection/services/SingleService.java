package com.app.theshineindia.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.app.theshineindia.baseclasses.SharedMethods;
import com.app.theshineindia.utils.AppData;
import com.app.theshineindia.utils.SP;

public class SingleService extends Service implements SensorEventListener {
    SensorManager sensorManager;
    private long lastUpdate = -1;
    private float last_x, last_y, last_z;
    private boolean isShaked = false;

    //ChargingBR chargingBR;
    //MediaPlayer mp;

    TelephonyManager telMgr;
    long prev_time = 0;
    long current_time = 0;
    Handler handler;
    boolean should_check = true;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                String CHANNEL_ID = "my_channel_01";
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        "The Shine India",
                        NotificationManager.IMPORTANCE_DEFAULT);
                ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

                Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("")
                        .setContentText("").build();

                startForeground(1, notification);
            }

            if (sensorManager == null) {
                sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            }

            if (sensorManager != null) {
                boolean is_register = sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                if (!is_register) {
                    sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
                }

                is_register = sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
                if (!is_register) {
                    sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
                }
            } else {
                Toast.makeText(this, "Error getting sensor manager", Toast.LENGTH_SHORT).show();
            }

//            chargingBR = new ChargingBR();
//            registerReceiver(chargingBR, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

            telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            handler = new Handler();
            //checkIsSimCardRemoved();

            Log.d("1111", "Service Started");

        } catch (Exception e) {
            Toast.makeText(this, "SingleService: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
            //unregisterReceiver(chargingBR);
            //sensorManager = null;
        }

        Log.d("1111", "Service destroyed");
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        //================================TYPE_PROXIMITY============================================
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            try {
                AppData.proximity_sensor_value = sensorEvent.values[0];
                Log.d("1111", "proximity_sensor_value Service: " + AppData.proximity_sensor_value);

                if (AppData.Sensor_Type.is_proximity_on && sensorEvent.values[0] >= AppData.PROXIMITY_SENSOR_SENSITIVITY) {
                    SharedMethods.playAlarm(this, AppData.Sensor_Name.proximity);
                    Toast.makeText(this, "Removed from pocket", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d("1111", "ERROR : " + e.getMessage());
            }
        }

        //================================TYPE_ACCELEROMETER========================================
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER && AppData.Sensor_Type.is_shake_on) {
            try {
                long curTime = System.currentTimeMillis();
                // only allow one update every 100ms.
                if ((curTime - lastUpdate) > 100) {
                    long diffTime = (curTime - lastUpdate);
                    lastUpdate = curTime;

                    float x = sensorEvent.values[0];
                    float y = sensorEvent.values[1];
                    float z = sensorEvent.values[2];

                    float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                    if (speed > AppData.SHAKE_THRESHOLD && !isShaked) {
                        // yes, this is a shake action! Do something about it!
                        isShaked = false;

                        Toast.makeText(this, "Mobile shake", Toast.LENGTH_SHORT).show();
                        SharedMethods.playAlarm(this, AppData.Sensor_Name.shake);
                    }

                    last_x = x;
                    last_y = y;
                    last_z = z;
                }

            } catch (Exception e) {
                Log.d("1111", "ERROR : " + e.getMessage());
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    private void checkIsSimCardRemoved() {
        handler.post(() -> {
            while (true) {
                current_time = System.currentTimeMillis();
                if (current_time - prev_time >= 2000 && SP.getBooleanPreference(this, SP.is_sim_tracker_on)) {
                    int simState = telMgr.getSimState();

                    if (simState == TelephonyManager.SIM_STATE_ABSENT) {
                        //Toast.makeText(this, "No sim card available", Toast.LENGTH_SHORT).show();
                        Log.d("1111", "No sim card available");
                    } else {
                        Log.d("1111", "Sim card available");
                    }
                    prev_time = current_time;
                }

                Log.d("1111", "Diff " + (current_time - prev_time));
            }
        });
    }
}