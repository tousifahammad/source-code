package com.app.haircutuser.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Save data in local by Shared preference
 */
public class SP {
    //keys
    public static final String fcm_token = "fcm_token";
    public static final String user_id = "user_id";
    public static final String name = "name";
    public static final String location = "location";
    public static final String referral_code = "referral_code";


    private static SharedPreferences.Editor editor;


    public static String getStringPreference(Context context, String key) {
        String value = null;
        if (context != null) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (preferences != null) {
                value = preferences.getString(key, null);
            }
        }

        return value;
    }

    public static boolean setStringPreference(Context context, String key, String value) {
        if (context != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (preferences != null && !TextUtils.isEmpty(key)) {
                if (value == null) {
                    return true;
                }
                editor = preferences.edit();
                editor.putString(key, value);
                return editor.commit();
            }
        }

        return false;
    }


    public static boolean logoutFunction(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.clear().commit();

        if (getStringPreference(context, user_id) == null) {
            return true;
        } else {
            return false;
        }
    }

}
