package com.app.guardian.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.guardian.R;
import com.app.guardian.baseclasses.BasePresenter;
import com.app.guardian.baseclasses.SharedMethods;
import com.app.guardian.baseclasses.WebServices;
import com.app.guardian.loaders.JSONFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ContactsPresenter extends BasePresenter {
    ContactsActivity activity;

    public ContactsPresenter(ContactsActivity activity) {
        super(activity);
        this.activity = activity;
    }


    @Override
    public void getJSONResponseResult(String result, int url_no) {
//        if (getpDialog().isShowing()) {
//            getpDialog().dismiss();
//        }
////        switch (url_no) {
////            case WebServices.url_no_contact_list:
////                if (SharedMethods.isSuccess(result, activity)) {
////                    responseContacts(result);
////                }
////                break;
////        }
    }


//    public void requestContacts() {
//        if (JSONFunctions.isInternetOn(activity)) {
//            getpDialog().setMessage("Getting your emergency contacts from the server");
//            getpDialog().show();
//
//            String url = WebServices.commonUrl + WebServices.contact_list;
//
//            HashMap<String, String> hashMap = new HashMap<>();
//            hashMap.put("user_id", getSsp().getUSERID());
//
//            getJfns().makeHttpRequest(url, "POST", hashMap, false, WebServices.url_no_contact_list);
//
//        } else {
//            Toast.makeText(activity, activity.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
//        }
//    }

    public void responseContacts(String response) {
        try {
            JSONArray jsonArray = SharedMethods.getDataArray(response, activity);
            if (jsonArray != null) {
                LayoutInflater inflater = activity.getLayoutInflater();
                //activity.ll_contact_main.removeAllViews();

                for (int i = 0; i < jsonArray.length(); i++) {
                    View myLayout = inflater.inflate(R.layout.row_contact, activity.ll_contact_main, false);
                    TextView tv_name = myLayout.findViewById(R.id.tv_name);
                    TextView tv_mobile = myLayout.findViewById(R.id.tv_mobile);

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    tv_name.setText(jsonObject.getString("name"));
                    tv_mobile.setText(jsonObject.getString("phone_number"));
                    activity.ll_contact_main.addView(myLayout);
                }

                // can't add more than 5 contact
                if (jsonArray.length() >= 5) {
                    activity.findViewById(R.id.btn_contact).setVisibility(View.INVISIBLE);
                }
                //saving contacts for sending sms in the background
                //getSsp().setEMERGENCY_CONTACT_LIST(response);
            }
        } catch (Exception ex) {
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
