package com.webgrity.backgroundservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;


public class MyIntentService extends IntentService {
    private final long time_interval = 3 * 1000;   //in milisec

    public MyIntentService() {
        super("MyIntentService");
        Log.d("TAG", "MyIntentService: ");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                while (AppData.isAppInForeground) {
                    printHello();
                    SystemClock.sleep(time_interval);
                }
            } catch (Exception e) {
            }
        }
    }

    private void printHello() {
        Log.d("TAG", " Current service ===> " +  Thread.currentThread().getId());
    }


}
