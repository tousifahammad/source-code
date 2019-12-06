package com.app.theshineindia.intruder_selfie;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.app.theshineindia.R;
import com.app.theshineindia.baseclasses.SharedMethods;
import com.app.theshineindia.utils.AppData;
import com.app.theshineindia.utils.SP;

public class AdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        ComponentName cn = new ComponentName(context, AdminReceiver.class);
        DevicePolicyManager mgr = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        mgr.setPasswordQuality(cn, DevicePolicyManager.PASSWORD_QUALITY_ALPHANUMERIC);

        onPasswordChanged(context, intent);
    }

    @Override
    public void onPasswordChanged(Context ctxt, Intent intent) {
        DevicePolicyManager mgr =
                (DevicePolicyManager) ctxt.getSystemService(Context.DEVICE_POLICY_SERVICE);
        int msgId;

        if (mgr.isActivePasswordSufficient()) {
            msgId = R.string.compliant;
        } else {
            msgId = R.string.not_compliant;
        }

        Log.d("1111", "" + msgId);
    }


    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        AppData.wrong_paas_count = AppData.wrong_paas_count + 1;
        Log.d("1111", "wrong_paas_count: " + AppData.wrong_paas_count);

        if (AppData.wrong_paas_count >= AppData.max_wrong_pass_attempts
                && SP.getBooleanPreference(context, SP.is_intruder_selfie_on)) {

//            Intent i = new Intent(context, CameraView.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);

//            Intent front_translucent = new Intent(context, CameraService.class);
//            front_translucent.putExtra("Front_Request", true);
//            front_translucent.putExtra("Quality_Mode", 50);
//            context.startService(front_translucent);


            try {
                Intent myService = new Intent(context, CameraService.class);
                myService.putExtra("Front_Request", true);
                myService.putExtra("Quality_Mode", 50);

                if (!SharedMethods.isMyServiceRunning(CameraService.class, context)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(myService);
                    } else {
                        context.startService(myService);
                    }
                } else {
                    Log.d("1111", "Already Running : " + CameraService.class.getSimpleName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        AppData.wrong_paas_count = 0;
    }
}
