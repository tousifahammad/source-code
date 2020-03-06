package com.app.theshineindia.secret_code;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.theshineindia.R;
import com.app.theshineindia.baseclasses.SharedMethods;
import com.app.theshineindia.utils.Alert;
import com.app.theshineindia.utils.AppData;
import com.app.theshineindia.utils.SP;
import com.mukesh.OtpView;

public class SecretCodeActivity extends AppCompatActivity {
    OtpView otpView;
    String request_for;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_code);

        setToolbar();

        initUI();

        setListener();

        request_for = getIntent().getStringExtra("request_for");
        if (request_for != null) {
            findViewById(R.id.ib_tracker_back).setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (request_for == null) {
            //do nothing
            finish();
        }
    }

    private void initUI() {
        otpView = findViewById(R.id.otp_view);
        otpView.requestFocus();
    }

    private void setToolbar() {
        findViewById(R.id.ib_tracker_back).setOnClickListener(view -> onBackPressed());
    }


    private void setListener() {
        otpView.setOtpCompletionListener(otp -> {

            if (request_for == null) {
                SP.setStringPreference(this, SP.secret_code, otp);

                finishActivity("Secret code applied successfully");

            } else {
                if (!SP.getStringPreference(this, SP.secret_code).equals(otp)) {
                    Alert.showError(this, "Invalid code");
                    return;
                }

                if (request_for.equals(AppData.Sensor_Name.shake)) {
                    AppData.Sensor_Type.is_shake_on = false;
                    finishActivity("Authentication successful");

                } else if (request_for.equals(AppData.Sensor_Name.proximity)) {
                    AppData.Sensor_Type.is_proximity_on = false;
                    finishActivity("Authentication successful");

                } else if (request_for.equals(AppData.Sensor_Name.charger)) {
                    AppData.Sensor_Type.is_charger_detection_on = false;
                    finishActivity("Authentication successful");

                } else if (request_for.equals("reset_code")) {
                    SP.removeStringPreference(this, SP.secret_code);
                    Toast.makeText(this, "Secret code removed. Enter new code.", Toast.LENGTH_LONG).show();

                    otpView.getText().clear();
                    request_for = null;
                }

                SharedMethods.getMediaPlayer(this).pause();     //pause alarm
            }
        });
    }

    private void finishActivity(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, new Intent());
        finish();
    }
}
