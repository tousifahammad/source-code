
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />


public void addOverlay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                askedForOverlayPermission = true;
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_CODE);
            }
        }
    }


        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);


            if (requestCode == OVERLAY_PERMISSION_CODE) {
                askedForOverlayPermission = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(this)) {
                        // SYSTEM_ALERT_WINDOW permission not granted...
                        //Toast.makeText(MyProtector.getContext(), "ACTION_MANAGE_OVERLAY_PERMISSION Permission Granted",                        Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(this, "ACTION_MANAGE_OVERLAY_PERMISSION Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
