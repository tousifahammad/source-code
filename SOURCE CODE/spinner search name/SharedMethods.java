package com.app.cubeapparels.baseclasses;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.cubeapparels.R;
import com.app.cubeapparels.shared.SearchFragment;
import com.app.cubeapparels.utils.Alert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

//use this class to write methods, which are used in many other classes
public class SharedMethods {

    public static boolean isSuccess(String response, Context mContext) {
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt(WebServices.result) == 1) {
                    return true;
                } else {
                    Toast.makeText(mContext, jsonObject.getString(WebServices.message), Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "No response from server. Please try again", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public static JSONArray getDataArray(String response, Activity activity) {
        JSONArray jsonArray = null;
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.getInt(WebServices.result) == 1) {
                    jsonArray = jsonObject.getJSONArray(WebServices.data);

                    if (jsonArray.length() > 0) {
                        return jsonArray;
                    } else {
                        //showEmptyView(activity);
                        Alert.showError(activity, "Data not available");
                    }

                } else {
                    //showEmptyView(activity);
                    Alert.showError(activity, jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                Alert.showError(activity, "Error 1 : " + e.getMessage());
            }
        } else {
            Alert.showError(activity, "No response from server. Please try again");
        }
        return jsonArray;
    }

    public static void showEmptyView(final Activity activity) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.empty_view);
        dialog.show();

        dialog.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity.finish();
                dialog.dismiss();
            }
        });
    }


//    public static void sendSMS(Context context) {
//        try {
//            String contact_response = new SettingSharedPreferences(context).getEMERGENCY_CONTACT_LIST();
//            if (contact_response != null) {
//                JSONObject jsonObject = new JSONObject(contact_response);
//                JSONArray jsonArray = jsonObject.getJSONArray(WebServices.data);
//                if (jsonArray.length() > 0) {
//                    String sender_name = new SettingSharedPreferences(context).getNAME();
//                    String message = "This message sent by Guardian APP from " + sender_name + ". He may be in emergency. Please take an immediate action";
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jobj = jsonArray.getJSONObject(i);
//                        String phone_number = jobj.getString("phone_number");
//                        if (!phone_number.isEmpty()) {
//                            SmsManager smsManager = SmsManager.getDefault();
//                            smsManager.sendTextMessage(phone_number, null, message, null, null);
//                            Toast.makeText(context, "Message Sent to : " + phone_number, Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }
//            } else {
//                Toast.makeText(context, "No contact found. Please check emergency contacts first.", Toast.LENGTH_LONG).show();
//            }
//
//        } catch (Exception ex) {
//            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }
//    }

    public static String encodeImageBitmap(Bitmap bm) {
        String encImage = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 10, baos);
            byte[] b = baos.toByteArray();
            encImage = Base64.encodeToString(b, Base64.DEFAULT);

        } catch (Exception e) {
            Log.d("OrderAllocationActivity", "encodeImage: " + e.getMessage());
        }
        return encImage;
    }


    public static void openStringSearch(Activity activity, ArrayList<String> name_list, int container_view_id, String title) {
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
