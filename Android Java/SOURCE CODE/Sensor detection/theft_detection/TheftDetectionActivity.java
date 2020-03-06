package com.app.theshineindia.theft_detection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.theshineindia.R;
import com.app.theshineindia.baseclasses.SharedMethods;
import com.app.theshineindia.secret_code.SecretCodeActivity;
import com.app.theshineindia.services.ChargingBR;
import com.app.theshineindia.services.SingleService;
import com.app.theshineindia.utils.Alert;
import com.app.theshineindia.utils.Animator;
import com.app.theshineindia.utils.AppData;
import com.app.theshineindia.utils.SP;

public class TheftDetectionActivity extends AppCompatActivity {
    Switch switch_phone_shake, switch_proxity, switch_charger;
    public static final int REQUEST_CODE_SHAKE = 1;
    public static final int REQUEST_CODE_PROXIMITY = 2;
    public static final int REQUEST_CODE_CHARGER = 3;

    CountDownTimer countDownTimer;
    ConstraintLayout CL_timer;
    TextView tv_timer, tv_type;
    int REQUEST_CODE;

    ChargingBR chargingBR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theft_detection);

        setToolbar();

        initUI();

        setListener();

        reset();

        chargingBR = new ChargingBR();
        SharedMethods.startMyService(this, SingleService.class);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        if (chargingBR != null)
//            unregisterReceiver(chargingBR);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            registerReceiver(chargingBR, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

            //Hide keyboard if opened
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        } catch (Exception e) {
            Alert.showError(this, e.getMessage());
        }


        //useful after add secret code and complete auth
        if (!AppData.Sensor_Type.is_shake_on) {
            switch_phone_shake.setChecked(false);
        }
        if (!AppData.Sensor_Type.is_proximity_on) {
            switch_proxity.setChecked(false);
        }
        if (!AppData.Sensor_Type.is_charger_detection_on) {
            switch_charger.setChecked(false);
        }
    }

    private void initUI() {
        switch_phone_shake = findViewById(R.id.switch_phone_shake);
        switch_proxity = findViewById(R.id.switch_proxity);
        switch_charger = findViewById(R.id.switch_charger);
        tv_timer = findViewById(R.id.tv_timer);
        tv_type = findViewById(R.id.tv_type);
        CL_timer = findViewById(R.id.CL_timer);
    }

    private void setToolbar() {
        findViewById(R.id.ib_changepassword_back).setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        reset();

        finish();
    }


    private void reset() {
        AppData.Sensor_Type.is_shake_on = false;
        AppData.Sensor_Type.is_proximity_on = false;
        AppData.Sensor_Type.is_charger_detection_on = false;
        AppData.Sensor_Type.is_charger_plugged_in = false;
        AppData.proximity_sensor_value = 50;

        SharedMethods.stopAllServices(this);

        try {
            if (chargingBR != null)
                unregisterReceiver(chargingBR);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setListener() {
        switch_phone_shake.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkSecretCode(REQUEST_CODE_SHAKE);
                tv_type.setText("Shake Detection \n\n Starts In");

            } else {
                AppData.Sensor_Type.is_shake_on = false;
            }
        });


        switch_proxity.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkSecretCode(REQUEST_CODE_PROXIMITY);
                tv_type.setText("Proximity Detection \n\n Starts In \n\n Please put your phone in the pocket");
            } else {
                AppData.Sensor_Type.is_proximity_on = false;
            }
        });

        switch_charger.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (AppData.Sensor_Type.is_charger_plugged_in) {
                    tv_type.setText("Charger Detection \n\n Starts In");
                    checkSecretCode(REQUEST_CODE_CHARGER);
                } else {
                    Alert.showMessage(this, "Please plugged in your charger");
                    switch_charger.setChecked(false);
                }
            } else {
                AppData.Sensor_Type.is_charger_detection_on = false;
                Log.d("1111", "is_charger_detection_on: " + AppData.Sensor_Type.is_charger_detection_on);
            }
        });
    }

    private void checkSecretCode(int REQUEST_CODE) {
        this.REQUEST_CODE = REQUEST_CODE;

        if (SP.getStringPreference(this, SP.secret_code) != null) {     //if secret code present
            startCountDown();
        } else {
            Intent my_intent = new Intent(this, SecretCodeActivity.class);
            startActivityForResult(my_intent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            startCountDown();
        }
    }

    private void startCountDown() {
        if (countDownTimer != null) {
            return;
        }
        CL_timer.setVisibility(View.VISIBLE);

        countDownTimer = new CountDownTimer(AppData.theft_detection_count_down_time * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_timer.setText(millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                CL_timer.setVisibility(View.GONE);
                countDownTimer.cancel();
                countDownTimer = null;

                startDetection();
            }

        }.start();
    }


    private void startDetection() {
        if (REQUEST_CODE == REQUEST_CODE_SHAKE) {
            AppData.Sensor_Type.is_shake_on = true;

        } else if (REQUEST_CODE == REQUEST_CODE_PROXIMITY) {
            AppData.Sensor_Type.is_proximity_on = true;

            checkProximityStatus();

        } else if (REQUEST_CODE == REQUEST_CODE_CHARGER) {
            AppData.Sensor_Type.is_charger_detection_on = true;
            //check second time
            //checkChargerStatus();
        }
    }

    public void resetSecretCode(View view) {
        Animator.buttonAnim(this, view);

        if (SP.getStringPreference(this, SP.secret_code) != null) {     //if secret code present
            Intent my_intent = new Intent(this, SecretCodeActivity.class);
            my_intent.putExtra("request_for", "reset_code");
            startActivity(my_intent);
        }
    }


    private void checkProximityStatus() {
        if (AppData.proximity_sensor_value > AppData.PROXIMITY_SENSOR_SENSITIVITY) {
            SharedMethods.playAlarm(this, AppData.Sensor_Name.proximity);
        }
    }


    private void checkChargerStatus() {
        if (!AppData.Sensor_Type.is_charger_plugged_in) {
            Alert.showMessage(this, "Please plugged in your charger");
            switch_charger.setChecked(false);
            //AppData.Sensor_Type.is_charger_detection_on = false;
        }
    }

}
