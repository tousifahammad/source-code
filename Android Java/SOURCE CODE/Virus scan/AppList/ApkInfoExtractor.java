package com.app.theshineindia.AppList;


import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.app.theshineindia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApkInfoExtractor {

    private Context context1;
    JSONArray harmful_app_ja;

    ApkInfoExtractor(Context context2) {
        context1 = context2;

        try {
            harmful_app_ja = new JSONObject(loadJSONFromAsset(context1)).getJSONArray("harmful_app");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    List<App> GetAllInstalledApkInfo() {
        List<App> ApkPackageName = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        List<ResolveInfo> resolveInfoList = context1.getPackageManager().queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resolveInfoList) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;

            App app = new App();
            String package_name = activityInfo.applicationInfo.packageName;
            String app_name = GetAppName(package_name);
            app.setName(app_name);
            app.setPackage_name(package_name);
            app.setIcon(getAppIconByPackageName(package_name));

            if (isSystemPackage(resolveInfo)) {
                app.setStatus("( System app )");
            } else {
                app.setStatus(checkAppStatus(app_name, package_name));
            }

            ApkPackageName.add(app);
        }

        return ApkPackageName;

    }


    public boolean isSystemPackage(ResolveInfo resolveInfo) {
        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public Drawable getAppIconByPackageName(String ApkTempPackageName) {
        Drawable drawable;

        try {
            drawable = context1.getPackageManager().getApplicationIcon(ApkTempPackageName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            drawable = ContextCompat.getDrawable(context1, R.mipmap.ic_launcher);
        }
        return drawable;
    }

    public String GetAppName(String ApkPackageName) {

        String Name = "";

        ApplicationInfo applicationInfo;

        PackageManager packageManager = context1.getPackageManager();

        try {

            applicationInfo = packageManager.getApplicationInfo(ApkPackageName, 0);

            if (applicationInfo != null) {
                Name = (String) packageManager.getApplicationLabel(applicationInfo);
            }

        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return Name;
    }


    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("harmful_app.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private String checkAppStatus(String app_name, String package_name) {
        String status = "Low risk";

        if (harmful_app_ja == null) {
            return status;
        }

        try {
            for (int i = 0; i < harmful_app_ja.length(); i++) {
                JSONObject jsonObject = harmful_app_ja.getJSONObject(i);

                if (!app_name.isEmpty() && app_name.equalsIgnoreCase(jsonObject.getString("name"))) {
                    return "High risk";
                }

                if (!package_name.isEmpty() && package_name.equalsIgnoreCase(jsonObject.getString("package_name"))) {
                    return "High risk";
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
