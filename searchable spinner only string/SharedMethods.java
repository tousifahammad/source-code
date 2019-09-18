package com.app.baseproject.baseclasses;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.baseproject.R;
import com.app.baseproject.home.Saloon;
import com.app.baseproject.shared.Search;
import com.app.baseproject.shared.SearchFragment;
import com.app.baseproject.utils.Alert;
import com.app.baseproject.utils.SettingSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//use this class to write methods, which are used in many other classes
public class SharedMethods {


    public static void openSearch(Activity activity, ArrayList<String> name_list, int container_view_id, String title) {
        try {
            if (name_list.size() > 0) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("name_list", name_list);
                bundle.putString("title", title);
                //bundle.putStringArrayList("code_list", biler_code_list);

                Fragment fragment = new SearchFragment();
                fragment.setArguments(bundle);

                FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
                transaction.replace(container_view_id, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            } else {
                Alert.showError(activity, "No data found");
            }
        } catch (Exception e) {
            Alert.showError(activity, e.getMessage());
        }
    }



}
