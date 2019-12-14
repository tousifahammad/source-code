    companion object {
        const val REQUEST_CODE = 3434
    }

    fun schedulePendingIntent(triggerTimeMillis: Long, pendingIntent: PendingIntent) {
        if (Build.VERSION.SDK_INT >= 23) { // https://stackoverflow.com/questions/34378707/alarm-manager-does-not-work-in-background-on-android-6-0
            logger.debug("setExactAndAllowWhileIdle")
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTimeMillis, pendingIntent)
        } else {
            if (Build.VERSION.SDK_INT >= 19) {
                logger.debug("setExact")
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTimeMillis, pendingIntent)
            } else {
                logger.debug("set")
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTimeMillis, pendingIntent)
            }
        }
    }

    fun checkAlarmScheduled(): Boolean {
        val notificationIntent = Intent(BringUserBackAppReminder.ACTION_FIRE_NOTIFICATION)
        return PendingIntent.getBroadcast(context, REQUEST_CODE, notificationIntent, PendingIntent.FLAG_NO_CREATE) != null
    }

    private fun getNotificationPendingIntent(): PendingIntent {
        val notificationIntent = Intent(BringUserBackAppReminder.ACTION_FIRE_NOTIFICATION)
        return PendingIntent.getBroadcast(context, REQUEST_CODE, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

