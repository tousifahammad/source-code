    public static void startMyService(Context context, Class<?> serviceClass) {
        try {
            if (SharedMethods.isMyServiceRunning(serviceClass, context)) {
                return;
            }

            Intent myService = new Intent(context, serviceClass);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(myService);
            } else {
                context.startService(myService);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void stopServices(Context context, Class<?> serviceClass) {
        try {
            if (SharedMethods.isMyServiceRunning(serviceClass, context))
                context.stopService(new Intent(context, serviceClass));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void stopAllServices(AppCompatActivity activity) {
        try {
            if (SharedMethods.isMyServiceRunning(SingleService.class, activity))
                activity.stopService(new Intent(activity, SingleService.class));

//            if (SharedMethods.isMyServiceRunning(ShakeService.class, activity))
//                activity.stopService(new Intent(activity, ShakeService.class));
//
//            if (SharedMethods.isMyServiceRunning(ChargingService.class, activity))
//                activity.stopService(new Intent(activity, ChargingService.class));
        } catch (Exception e) {
            Alert.showError(activity, e.getMessage());
        }
    }




    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        boolean isServiceRunning = false;
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    isServiceRunning = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("1111", serviceClass.getSimpleName() + " Running : " + isServiceRunning);
        return isServiceRunning;
    }
