package com.app.theshineindia.secret_code;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.theshineindia.R;
import com.app.theshineindia.baseclasses.SharedMethods;
import com.app.theshineindia.theft_detection.TheftDetectionActivity;
import com.app.theshineindia.utils.Alert;
import com.app.theshineindia.utils.AppData;
import com.app.theshineindia.utils.SP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecretCodeActivity extends AppCompatActivity {
    //OtpView otpView;
    String request_for;
    EditText et_secret_code;

        /*
        1. Set Device owner and lock task/pin screen
        2. Set as home intent
        3. Disable power off button/ give a way to turn off the device - not possible
        4. Disable volume botton if required
        5. stop screen to turn off, or lock
     */

    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    private OnScreenOffReceiver onScreenOffReceiver;
    private final List blockedKeys = new ArrayList(Arrays.asList(
            KeyEvent.KEYCODE_VOLUME_DOWN,
            KeyEvent.KEYCODE_VOLUME_UP)
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().getDecorView().setSystemUiVisibility(flags);
        setContentView(R.layout.activity_secret_code);

        setToolbar();

        initUI();

        //setListener();

        request_for = getIntent().getStringExtra("request_for");
        if (request_for != null && request_for.equals(AppData.authenticate)) {
            findViewById(R.id.ib_tracker_back).setVisibility(View.GONE);
            activateKioskMode();
        }
    }


    private void initUI() {
        //otpView = findViewById(R.id.otp_view);
        et_secret_code = findViewById(R.id.et_secret_code);
        et_secret_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 4)
                    manageSecretCode(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 4)
                    manageSecretCode(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
    }

    private void setToolbar() {
        findViewById(R.id.ib_tracker_back).setOnClickListener(view -> onBackPressed());
    }


    public void manageSecretCode(String secret_code) {
        if (secret_code.length() != 4) {
            Alert.showError(this, "Enter 4 digit secret code");

        } else if (request_for == null) {
            SP.setStringPreference(this, SP.secret_code, secret_code);
            Toast.makeText(this, "Secret code applied successfully", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, new Intent());
            finish();

        } else if (!SP.getStringPreference(this, SP.secret_code).equals(secret_code)) {
            Alert.showError(this, "Invalid code");

        } else if (request_for.equals("reset_code")) {
            SP.removeStringPreference(this, SP.secret_code);
            Toast.makeText(this, "Secret code removed. Enter new code", Toast.LENGTH_LONG).show();

            et_secret_code.getText().clear();
            request_for = null;

        } else {
            finishActivity(request_for);
        }
    }

    private void finishActivity(String detection_type) {
        Toast.makeText(this, "Authentication successful", Toast.LENGTH_SHORT).show();

        //SP.setBooleanPreference(this, detection_type, false);
        SP.setBooleanPreference(this, SP.Sensor_Type.is_shake_detection_on, false);
        SP.setBooleanPreference(this, SP.Sensor_Type.is_proximity_detection_on, false);
        SP.setBooleanPreference(this, SP.Sensor_Type.is_charger_detection_on, false);
        SP.setBooleanPreference(this, SP.Sensor_Type.is_headset_detection_on, false);

        SharedMethods.getMediaPlayer(this).pause();     //pause alarm

        stopServiceAndReceiver();

        Intent my_intent = new Intent(this, TheftDetectionActivity.class);
        my_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        my_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(my_intent);
        finish();
    }


    //======================== disable system hardware =============================
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //Log.d("1111", "dispatchKeyEvent: " + event.getKeyCode());
        if (blockedKeys.contains(event.getKeyCode())) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onBackPressed() {
        if (request_for.equals(AppData.authenticate)) {
            //Disable the back button
            return;
        }

        finish();
    }


    private void registerKioskModeScreenOffReceiver() {
        // register screen off receiver
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        onScreenOffReceiver = new OnScreenOffReceiver();
        registerReceiver(onScreenOffReceiver, filter);

        Log.d("1111", "OnScreenOffReceiver : registered");
    }

    private void startKioskService() { // ... and this method
        SharedMethods.startMyService(this, KioskService.class);
    }


    private void stopServiceAndReceiver() {
        try {
            SharedMethods.stopServices(this, KioskService.class);
            if (onScreenOffReceiver != null)
                unregisterReceiver(onScreenOffReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void activateKioskMode() {
        setVolumMax();

        //The home button
        startKioskService();

        //Disable the power button
        //Short power button press:
        registerKioskModeScreenOffReceiver();

        //Prevent status bar expansion / disable status bar pull down
        preventStatusBarExpansion(this);
    }

    private void setVolumMax() {
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(
                AudioManager.STREAM_SYSTEM,
                am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM),
                0);
    }

    //DISABLE Long power button press:
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }


    public static void preventStatusBarExpansion(Context context) {
        WindowManager manager = ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE));

        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = Gravity.TOP;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;

        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int result = 0;
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        } else {
            // Use Fallback size:
            result = 60; // 60px Fallback
        }

        localLayoutParams.height = result;
        localLayoutParams.format = PixelFormat.TRANSPARENT;

        CustomViewGroup view = new CustomViewGroup(context);
        manager.addView(view, localLayoutParams);
    }

    public static class CustomViewGroup extends ViewGroup {
        public CustomViewGroup(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            // Intercepted touch!
            return true;
        }
    }


}
