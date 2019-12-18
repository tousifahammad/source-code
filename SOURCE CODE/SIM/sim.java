    public static boolean isSimCardAvailable(Context context) {
        try {
            TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telMgr.getSimState() == TelephonyManager.SIM_STATE_ABSENT) {
                Log.d("1111", "No sim card available");
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return true;
    }


    //===================================================================================
    @SuppressLint("MissingPermission")
    public static int getAvailableSimCount(Context context) {
        int sim_count = 0;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                SubscriptionManager sManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                SubscriptionInfo infoSim1 = sManager.getActiveSubscriptionInfoForSimSlotIndex(0);
                if (infoSim1 != null) {
                    sim_count += 1;
                }
                SubscriptionInfo infoSim2 = sManager.getActiveSubscriptionInfoForSimSlotIndex(1);
                if (infoSim2 != null) {
                    sim_count += 1;
                }
            } else {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager.getSimSerialNumber() != null) {
                    sim_count += 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sim_count;
    }

