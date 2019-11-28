package com.app.theshineindia.phone_info;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.theshineindia.R;
import com.app.theshineindia.utils.Alert;

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class PhoneInfoActivity extends AppCompatActivity implements SensorEventListener {
    ProgressBar pb_storage, pb_ram, pb_cpu;
    TextView tv_storage, tv_ram, tv_cpu_temp, tv_phone_name, tv_android_version, tv_soft_version, tv_storage_percentage, tv_ram_percentage, tv_cpu_percentage;
    private SensorManager mSensorManager;
    private Sensor mTempSensor;
    private ActivityManager.MemoryInfo memoryInfo;
    private ActivityManager activityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_info);

        setToolbar();

        initUI();

        getPhoneInfo();

        //initObjects();

        getStorageInfo();

        getRamInfo();

        getCpuInfo();

        getCpuTemp();
    }

    private void initObjects() {
        memoryInfo = new ActivityManager.MemoryInfo();
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
    }

    private void getCpuTemp() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mTempSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (mTempSensor == null) {
            Alert.showError(this, "Sorry, sensor not available for this device.");
        }
    }


    private void setToolbar() {
        findViewById(R.id.ib_phoneinfo_back).setOnClickListener(view -> onBackPressed());
    }


    private void initUI() {
        pb_storage = findViewById(R.id.pb_storage);
        pb_ram = findViewById(R.id.pb_ram);
        pb_cpu = findViewById(R.id.pb_cpu);
        tv_storage = findViewById(R.id.tv_storage);
        tv_ram = findViewById(R.id.tv_ram);
        tv_cpu_temp = findViewById(R.id.tv_cpu_temp);
        tv_phone_name = findViewById(R.id.tv_phone_name);
        tv_android_version = findViewById(R.id.tv_android_version);
        tv_soft_version = findViewById(R.id.tv_soft_version);
        tv_storage_percentage = findViewById(R.id.tv_storage_percentage);
        tv_ram_percentage = findViewById(R.id.tv_ram_percentage);
        tv_cpu_percentage = findViewById(R.id.tv_cpu_percentage);
    }


    private void getPhoneInfo() {
        try {
            tv_phone_name.setText(Build.BRAND + " " + Build.MODEL);
            tv_android_version.setText(Build.VERSION.RELEASE);
            tv_soft_version.setText(Build.FINGERPRINT);

        } catch (Exception e) {
            Alert.showError(this, e.getMessage());
        }
    }


    private void getStorageInfo() {
        try {
            long total_internal_memory = SystemUtility.getTotalInternalMemorySize();
            long free_internal_memory = SystemUtility.getAvailableInternalMemorySize();

            long total_external_memory = 0;
            long free_external_memory = 0;

//            Log.d("111", "total_external_memory: " + total_external_memory);
//            Log.d("111", "free_external_memory: " + free_external_memory);
//            Log.d("111", "externalMemoryAvailable: " + SystemUtility.externalMemoryAvailable());

            long total_memory = total_internal_memory + total_external_memory;
            long free_memory = free_internal_memory + free_external_memory;

            double percentage = (double) free_memory / (double) total_memory;
            percentage = percentage * 100;

            tv_storage_percentage.setText((int) percentage + "%");
            pb_storage.setProgress((int) percentage);

            tv_storage.setText(SystemUtility.getFileSize(free_internal_memory) + "/" + SystemUtility.getFileSize(total_internal_memory));

        } catch (Exception e) {
            Alert.showError(this, e.getMessage());
        }
    }


//    private void getStorageInfo() {
//        try {
//            long total_internal_memory = getTotalInternalMemorySize();
//            long total_external_memory = getTotalExternalMemorySize();
//
//            long free_internal_memory = getAvailableInternalMemorySize();
//            long free_external_memory = getAvailableExternalMemorySize();
//
//            long total_memory = total_internal_memory + total_external_memory;
//            long free_memory = free_internal_memory + free_external_memory;
//
//            double percentage = (double) total_memory - (double) free_memory;
//            percentage = percentage / (double) total_memory;
//            percentage = percentage * 100;
//
//            tv_storage.setText(formatSize(free_memory) + "/" + formatSize(total_memory));
//            tv_storage_percentage.setText((int) percentage + "%");
//            pb_storage.setProgress((int) percentage);
//
//        } catch (Exception e) {
//            Alert.showError(this, e.getMessage());
//        }
//    }


    private void getRamInfo() {
        try {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(memoryInfo);

            long total_ram = memoryInfo.totalMem;
            long available_ram = memoryInfo.availMem;

            tv_ram.setText(SystemUtility.getFileSize(available_ram) + "/" + SystemUtility.getFileSize(total_ram));
        } catch (Exception e) {
            Alert.showError(this, e.getMessage());
        }
    }


    private void getCpuInfo() {
        try {
            int[] output = new int[getNumCores()];
            for (int i = 0; i < getNumCores(); i++) {
                //output[i] = readSystemFileAsInt("/sys/devices/system/cpu/cpu" + i + "/cpufreq/scaling_cur_freq");
            }
        } catch (Exception e) {
            Alert.showError(this, e.getMessage());
        }
    }

    public static int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            //Default to return 1 core
            return 1;
        }
    }

//    private void getCpuInfo() {
//        ProcessBuilder processBuilder;
//        String Holder = "";
//        String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
//        InputStream inputStream;
//        Process process;
//        byte[] byteArry;
//
//        byteArry = new byte[1024];
//
//        try {
//            processBuilder = new ProcessBuilder(DATA);
//            process = processBuilder.start();
//            inputStream = process.getInputStream();
//
//            while (inputStream.read(byteArry) != -1) {
//                Holder = Holder + new String(byteArry);
//            }
//
//            inputStream.close();
//
//        } catch (IOException e) {
//            Alert.showError(this, e.getMessage());
//        }
//
//        //tv_cpu_percentage.setText(Holder);
//
//        Log.d("1111", "getCpuInfo: " + Holder);
//    }

//    private long freeRamMemorySize() {
//        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
//        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        activityManager.getMemoryInfo(memoryInfo);
//
//        Runtime runtime = Runtime.getRuntime();
//        String strMemInfo =
//                "Available Memory = " + memoryInfo.availMem + "\n"
//                        + "Total Memory = " + memoryInfo.totalMem + "\n"
//                        + "Runtime Max Memory = " + runtime.maxMemory() + "\n"
//                        + "Runtime Total Memory = " + runtime.totalMemory() + "\n"
//                        + "Runtime Free Memory = " + runtime.freeMemory() + "\n";
//
//        Log.d("1111", strMemInfo);
//
//        return memoryInfo.totalMem;
//
//    }
//
//    private long totalRamMemorySize() {
//        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
//        activityManager.getMemoryInfo(memInfo);
//        return memInfo.totalMem;
//    }


    //================================================
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mTempSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        tv_cpu_percentage.setText(String.valueOf(event.values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
