package com.hascode.android;

import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SchedulerEventService extends Service {
	private static final String APP_TAG = "com.hascode.android.scheduler";

	@Override
	public IBinder onBind(final Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(final Intent intent, final int flags,
			final int startId) {
		Log.d(APP_TAG, "event received in service: " + new Date().toString());
		return Service.START_NOT_STICKY;
	}

}
