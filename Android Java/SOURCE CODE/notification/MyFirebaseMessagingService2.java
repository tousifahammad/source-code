package com.app.effistay.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.effistay.R;
import com.app.effistay.Utility.SharedPreference;
import com.app.effistay.activities.HomeActivity;
import com.app.effistay.activities.SIdeNavigationItemsClickActivity;
import com.app.effistay.query.QueryActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    PendingIntent pendingIntent;
    private Context context;
    private NotificationManager notifManager;
    private Bitmap bigPicture;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("newToken", s);

        SharedPreference.setStringPreference(this, SharedPreference.fcm_token, s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Log.e(TAG, "notification: " + notification);

        Map<String, String> getData = remoteMessage.getData();
        Log.e(TAG, "getData: " + getData);

        // Check if message contains a data payload.
        if (getData.size() > 0) {
            Log.d(TAG, "Message data payload: " + getData);
            sendNotification(getData);
        } else {
            // Check if message contains a notification payload.
            if (notification != null) {
                Log.d(TAG, "Message Notification Body: " + notification.getBody());
                sendNotification(notification);
            }
        }
    }


    private void sendNotification(RemoteMessage.Notification notification) {
        //Log.d(TAG, "sendNotification: " + data.toString());
        //{msg_id=7, user_id=2, body=You have a notification from Guardian, title=Guardian, message=this ia test message }
        Intent intent = null;
        String body, msg_id, title, message;

        try {
            //getting the title and the body
            title = notification.getTitle();
            message = notification.getBody();
            String icon = notification.getIcon();
            body=notification.getBody();

            Log.e("djcdbcbdc", "iconURL " + icon);

            bigPicture = getBitmapfromUrl(icon);

            Log.e("djcdbcbdc", "NotifyTitle " + title);
            Log.e("djcdbcbdc", "NotifyMessage " + message);
            Log.e("djcdbcbdc", "NotifyIcon " + bigPicture);

            intent = new Intent(this, HomeActivity.class);

            createNotification(title, body,message, bigPicture, intent);

        } catch (Exception e) {
            e.printStackTrace();

            Log.e("dcjdncjndc", "dchdbchbd " + e.toString());
        }

    }

    private void sendNotification(Map<String, String> data) {
        //Log.d(TAG, "sendNotification: " + data.toString());
        //{msg_id=7, user_id=2, body=You have a notification from Guardian, title=Guardian, message=this ia test message }
        Intent intent = null;
        String body, msg_id, title, message;

        try {
            title = data.get("title");
            message = data.get("message");
            body = data.get("body");
            String pagename = data.get("pagename");
            String picture_url = data.get("picture_url");

            bigPicture = getBitmapfromUrl(picture_url);

            Log.e("djcdbcbdc", "title " + title);
            Log.e("djcdbcbdc", "message " + message);
            Log.e("djcdbcbdc", "body " + body);
            Log.e("djcdbcbdc", "pagename " + pagename);
            Log.e("djcdbcbdc", "bigPicture " + bigPicture);

            if (pagename.equalsIgnoreCase("mybooking")) {

                SharedPreference.setStringPreference(this, "NAV_PAGE", "MYBOOKINGS");
                intent = new Intent(this, SIdeNavigationItemsClickActivity.class);

            } else if (pagename.equalsIgnoreCase("notification")) {

                SharedPreference.setStringPreference(this, "NAV_PAGE", "NOTIFICATION");
                intent = new Intent(this, SIdeNavigationItemsClickActivity.class);

            } else if (pagename.equalsIgnoreCase("chat")) {
                intent = new Intent(this, QueryActivity.class);

            } else {
                intent = new Intent(this, HomeActivity.class);
            }

            Log.e(TAG, data.toString());

            createNotification(title, body,message, bigPicture, intent);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("dcjdncjndc", "dchdbchbd " + e.toString());
        }
    }

    public void createNotification(String titles, String body, String aMessage, Bitmap bigPicture, Intent intent) {
        final int NOTIFY_ID = 0; // ID of notification
        String id = getString(R.string.default_notification_channel_id); // default_channel_id
        String title = getString(R.string.default_notification_channel_title); // Default Channel

        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(aMessage);
        Log.e("bigPictureStyle", "bigPictureStyle " + bigPictureStyle);
        assert bigPictureStyle != null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent, 0);
            builder.setSmallIcon(R.drawable.logo_notification)   // required
                    .setColor(getResources().getColor(R.color.yelloProjectColor))
                    .setContentTitle(body)// required
                    .setContentText(aMessage) // required
                   // .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    //.setStyle(bigPictureStyle)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(aMessage))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {
            builder = new NotificationCompat.Builder(this, id);
           // intent = new Intent(this, SIdeNavigationItemsClickActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent, 0);
            builder.setSmallIcon(R.drawable.logo_notification)   // required
                    .setColor(getResources().getColor(R.color.yelloProjectColor))
                    .setContentTitle(body)// required
                    .setContentText(aMessage) // required
                   // .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                   // .setStyle(bigPictureStyle)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(aMessage))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);

        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            Bitmap bitmap = null;
            if (imageUrl.isEmpty()) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_round);
            } else {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);

                //                if (picture_url != null && !"".equals(picture_url)) {
//                    URL url = new URL(picture_url);
//                    bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                    Log.e("bigPicture", "DataLoadedPic " + bigPicture);
//                }
            }
            return bitmap;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }



    private void callNotificationTitleMethod(String title, String message, Intent intent, String body) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationBuilder.setContentText(message);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

}