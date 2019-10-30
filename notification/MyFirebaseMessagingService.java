package com.app.haircutuser.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.haircutuser.R;
import com.app.haircutuser.home.HomeActivity;
import com.app.haircutuser.utils.SP;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    PendingIntent pendingIntent;
    private NotificationManager notifManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, "FCM TOKEN " + s);

        SP.setStringPreference(this, SP.fcm_token, s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, remoteMessage.getFrom());

        Map<String, String> map = remoteMessage.getData();
        sendNotification(map);

        Log.d(TAG, map.toString());
    }

    private void sendNotification(Map<String, String> data) {
        //Log.d(TAG, "sendNotification: " + data.toString());
        //{msg_id=7, user_id=2, body=You have a notification from Guardian, title=Guardian, message=this ia test message }
        Intent intent = null;
        String message_text, msg_id, title, message;

        try {
            title = data.get("title");
            message = data.get("message");
            message_text = data.get("body");
            intent = new Intent(this, HomeActivity.class);

            Log.e(TAG, data.toString());

            if (title == null) {
                title = getResources().getString(R.string.app_name);
            }
            if (message == null) {
                message = "Got new notification";
            }
            createNotification(title, message, intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void callNotificationTitleMethod(String title, String message_text, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message_text))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }


    public void createNotification(String title, String message, Intent intent) {
        final int NOTIFY_ID = 0; // ID of notification
        String id = getString(R.string.default_notification_channel_id); // default_channel_id
        String channel_title = getString(R.string.default_notification_channel_title); // Default Channel

        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, channel_title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent, 0);
            builder.setSmallIcon(R.mipmap.logo)   // required
                    //.setColor(getResources().getColor(R.color.colorAccent))
                    .setContentTitle(title)// required
                    .setContentText(message) // required
                    // .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(message)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    //.setStyle(bigPictureStyle)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {
            builder = new NotificationCompat.Builder(this, id);
            // intent = new Intent(this, SIdeNavigationItemsClickActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent, 0);
            builder.setSmallIcon(R.mipmap.logo)   // required
                    //.setColor(getResources().getColor(R.color.colorAccent))
                    .setContentTitle(title)// required
                    .setContentText(message) // required
                    // .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(message)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    // .setStyle(bigPictureStyle)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);

        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }

}