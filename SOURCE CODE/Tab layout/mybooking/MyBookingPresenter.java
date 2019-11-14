package com.app.haircutuser.mybooking;

import android.widget.Toast;

import com.app.haircutuser.R;
import com.app.haircutuser.baseclasses.BasePresenter;
import com.app.haircutuser.baseclasses.SharedMethods;
import com.app.haircutuser.baseclasses.WebServices;
import com.app.haircutuser.loaders.JSONFunctions;
import com.app.haircutuser.utils.Alert;
import com.app.haircutuser.utils.IntentController;
import com.app.haircutuser.utils.SP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyBookingPresenter extends BasePresenter {
    MyBookingActivity activity;

    public MyBookingPresenter(MyBookingActivity activity) {
        super(activity);
        this.activity = activity;
    }


    @Override
    public void getJSONResponseResult(String result, int url_no) {
        if (getpDialog().isShowing()) {
            getpDialog().dismiss();
        }

        if (url_no == WebServices.request_url_no_1) {
            if (SharedMethods.isSuccess(result, activity)) {
                responseMyBooking(result);
            }
        } else if (url_no == WebServices.request_url_no_2) {
            if (SharedMethods.isSuccess(result, activity)) {
                responseBookingCancel(result);
            }
        } else if (url_no == WebServices.request_url_no_3) {
            if (SharedMethods.isSuccess(result, activity)) {
                responseRemainder(result);
            }
        }
    }


    void requestMyBooking() {
        if (JSONFunctions.isInternetOn(activity)) {
            getpDialog().setMessage("Getting booking history. Please wait...");
            getpDialog().show();

            String url = WebServices.BASE_URL + WebServices.customer_booking;

            //vendor_id,customer_id,service_id,booking_type,booking_date,booking_time,customer_type,name,gender,mobile,location,payment_mode
            HashMap<String, String> hashMap = new HashMap<>();
            //hashMap.put("customer_id", SP.getStringPreference(activity, SP.user_id));
            hashMap.put("customer_id", SP.getStringPreference(activity, SP.user_id));

            getJfns().makeHttpRequest(url, "POST", hashMap, false, WebServices.request_url_no_1);

        } else {
            Alert.showError(activity, activity.getString(R.string.no_internet));
        }
    }

    private void responseMyBooking(String response) {
        try {
            JSONObject response_jo = new JSONObject(response);
            JSONArray upcoming_ja = response_jo.getJSONArray("upcoming");
            JSONArray completed_ja = response_jo.getJSONArray("completed");

            activity.upcoming_booking_list = getList(upcoming_ja);
            activity.past_booking_list = getList(completed_ja);

            activity.setupViewPager();

        } catch (Exception ex) {
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<Booking> getList(JSONArray jsonArray) {
        ArrayList<Booking> list = new ArrayList<>();
        try {
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject booking_jo = jsonArray.getJSONObject(i);
                    list.add(new Booking(
                            booking_jo.getString("vendor_id"),
                            booking_jo.getString("booking_id"),
                            booking_jo.getString("name"),
                            booking_jo.getString("location"),
                            booking_jo.getString("booking_type"),
                            booking_jo.getString("booking_date"),
                            booking_jo.getString("booking_time"),
                            booking_jo.getString("service_id"),
                            booking_jo.getString("price"),
                            booking_jo.getString("service_started"),
                            booking_jo.getString("servicename"),
                            booking_jo.getString("saloonname"),
                            booking_jo.getString("status"),
                            booking_jo.getString("is_reminder")

                    ));
                }
            }
        } catch (Exception e) {
            Alert.showError(activity, e.getMessage());
        }

        return list;
    }

    void requestBookingCancel(String booking_id) {
        if (JSONFunctions.isInternetOn(activity)) {
            getpDialog().setMessage("Cancelling your booking. Please wait...");
            getpDialog().show();

            String url = WebServices.BASE_URL + WebServices.cancel_booking;

            //vendor_id,customer_id,service_id,booking_type,booking_date,booking_time,customer_type,name,gender,mobile,location,payment_mode
            HashMap<String, String> hashMap = new HashMap<>();
            //hashMap.put("customer_id", SP.getStringPreference(activity, SP.user_id));
            hashMap.put("booking_id", booking_id);

            getJfns().makeHttpRequest(url, "POST", hashMap, false, WebServices.request_url_no_2);

        } else {
            Alert.showError(activity, activity.getString(R.string.no_internet));
        }
    }

    private void responseBookingCancel(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            Toast.makeText(activity, jsonObject.getString(WebServices.message), Toast.LENGTH_SHORT).show();

            IntentController.gotToActivityNoBack(activity, MyBookingActivity.class);
        } catch (Exception ex) {
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    void requestRemainder(String is_reminder, String booking_id) {
        if (JSONFunctions.isInternetOn(activity)) {
            getpDialog().setMessage("Please wait...");
            getpDialog().show();

            String url = WebServices.BASE_URL + WebServices.reminder;

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("is_reminder", is_reminder);
            hashMap.put("booking_id", booking_id);

            getJfns().makeHttpRequest(url, "POST", hashMap, false, WebServices.request_url_no_3);

        } else {
            Alert.showError(activity, activity.getString(R.string.no_internet));
        }
    }

    private void responseRemainder(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            Toast.makeText(activity, jsonObject.getString(WebServices.message), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
