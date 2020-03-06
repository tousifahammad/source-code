    private static AlarmManager alarmManager;

    public static void startAlarmManager(Context context) {
        try {
            if (isAlarmManagerRunning(context)) {
                return;
            }

            int EXEC_INTERVAL = 60 * 1000;

            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent_receiver = new Intent(context, SchedulerEventReceiver.class); // explicit intent
            intent_receiver.setAction(SchedulerEventReceiver.ACTION_ALARM_RECEIVER);//my custom string action name
            PendingIntent intentExecuted = PendingIntent.getBroadcast(context, 1001, intent_receiver, PendingIntent.FLAG_CANCEL_CURRENT);
            Calendar now = Calendar.getInstance();
            now.add(Calendar.SECOND, 20);

            if (alarmManager != null && intentExecuted != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), EXEC_INTERVAL, intentExecuted);

                Log.d("1111", "AlarmManager started");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    private static boolean isAlarmManagerRunning(Context context) {
        //checking if alarm is working with pendingIntent
        boolean isRunning = false;
        try {
            Intent intent_receiver = new Intent(context, SchedulerEventReceiver.class); // explicit intent
            intent_receiver.setAction(SchedulerEventReceiver.ACTION_ALARM_RECEIVER);//the same as up
            isRunning = (PendingIntent.getBroadcast(context, 1001, intent_receiver, PendingIntent.FLAG_NO_CREATE) != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("1111", "AlarmManager running : " + isRunning);
        return isRunning;
    }







    public static void stopAlarmManager(Context context) {
        try {
            if (!isAlarmManagerRunning(context)) {
                return;
            }

            Intent intent_receiver = new Intent(context, SchedulerEventReceiver.class); // explicit intent
            intent_receiver.setAction(SchedulerEventReceiver.ACTION_ALARM_RECEIVER);//the same as up
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1001, intent_receiver, PendingIntent.FLAG_CANCEL_CURRENT);

            if (alarmManager != null && pendingIntent != null && !isSingleDetectionEnabled(context)) {
                alarmManager.cancel(pendingIntent);//important
                pendingIntent.cancel();//important
                //AppData.is_alarm_manager_activated = false;
                Log.d("1111", "AlarmManager : stopped");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
