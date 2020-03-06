    public String getUniqueIMEIId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    device_imei = telephonyManager.getImei();
                } else {
                    device_imei = telephonyManager.getDeviceId();
                }

                if (device_imei != null && !device_imei.isEmpty()) {
                    return device_imei;
                } else {
                    return android.os.Build.SERIAL;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "not_found";
    }
