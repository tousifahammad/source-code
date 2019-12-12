package com.hascode.android;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SchedulerSetupReceiver extends BroadcastReceiver {
	private static final String APP_TAG = "com.hascode.android";

	private static final int EXEC_INTERVAL = 20 * 1000;

	@Override
	public void onReceive(final Context ctx, final Intent intent) {
		Log.d(APP_TAG, "SchedulerSetupReceiver.onReceive() called");
		AlarmManager alarmManager = (AlarmManager) ctx
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(ctx, SchedulerEventReceiver.class); // explicit
																	// intent
		PendingIntent intentExecuted = PendingIntent.getBroadcast(ctx, 0, i,
				PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, 20);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				now.getTimeInMillis(), EXEC_INTERVAL, intentExecuted);
	}

}
