package com.app.theshineindia.intruder_selfie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import com.app.theshineindia.R;
import com.app.theshineindia.baseclasses.SharedMethods;
import com.app.theshineindia.utils.AppData;
import com.app.theshineindia.utils.SP;

public class IntruderSelfieActivity extends AppCompatActivity {
    Switch switch_intruder_selfie;
    AdminReceiver adminReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introder_selfie);

        adminReceiver = new AdminReceiver();

        setupToolbar();

        intUI();

        iniListener();
    }

//    private void initReceiver() {
//        ComponentName cn = new ComponentName(this, AdminReceiver.class);
//        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
//        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.device_admin_explanation));
//        startActivity(intent);
//    }

    private void intUI() {
        switch_intruder_selfie = findViewById(R.id.switch_intruder_selfie);
    }

    private void setupToolbar() {
        findViewById(R.id.ib_tracker_back).setOnClickListener(view -> onBackPressed());
    }


    private void iniListener() {
//        switch_intruder_selfie.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                AppData.Sensor_Type.is_intruder_selfie_on = true;
//                initReceiver();
//            } else {
//                AppData.Sensor_Type.is_intruder_selfie_on = true;
//            }
//        });

        if (SP.getBooleanPreference(this, SP.is_intruder_selfie_on)) {
            switch_intruder_selfie.setChecked(true);
        }

        switch_intruder_selfie.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                SP.setBooleanPreference(this, SP.is_intruder_selfie_on, true);
                SharedMethods.initIntruderSelfie(this);
                //initReceiver();

            } else {
                SP.setBooleanPreference(this, SP.is_intruder_selfie_on, false);
            }
        });
    }

}
