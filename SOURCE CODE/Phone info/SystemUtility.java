package com.app.theshineindia.phone_info;

import android.app.ActivityManager;
import android.os.Environment;
import android.os.StatFs;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.DecimalFormat;

public class SystemUtility extends AppCompatActivity {
//    private static ActivityManager.MemoryInfo memoryInfo;
//    private static ActivityManager activityManager;

//    private SystemUtility() {
//        if (memoryInfo == null)
//            memoryInfo = new ActivityManager.MemoryInfo();
//
//        if (activityManager == null)
//            activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//
//        if (activityManager != null && memoryInfo != null)
//            activityManager.getMemoryInfo(memoryInfo);
//    }


//    public long getTotalRam() {
//        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
//        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        activityManager.getMemoryInfo(memoryInfo);
//        return memoryInfo.totalMem;
//    }
//
//    public long getAvailableRam() {
//        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
//        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        activityManager.getMemoryInfo(memoryInfo);
//        return memoryInfo.availMem;
//    }


    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }


    public static boolean externalMemoryAvailable() {
        //return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        return StorageHelper.isExternalStorageReadableAndWritable();
    }

    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return 0;
        }
    }

    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return 0;
        }
    }


    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = " KB";
            size /= 1024;

            if (size >= 1024) {
                suffix = " MB";
                size /= 1024;

                if (size >= 1024) {
                    suffix = " GB";
                    size /= 1024;
                }
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    private String returnToDecimalPlaces(long values) {
        DecimalFormat df = new DecimalFormat("#.00");
        String angleFormated = df.format(values);
        return angleFormated;
    }
}
