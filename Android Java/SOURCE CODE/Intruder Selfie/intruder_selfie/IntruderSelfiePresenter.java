package com.app.theshineindia.intruder_selfie;

import android.content.Context;
import android.widget.Toast;

import com.app.theshineindia.R;
import com.app.theshineindia.baseclasses.BasePresenter;
import com.app.theshineindia.baseclasses.SharedMethods;
import com.app.theshineindia.baseclasses.WebServices;
import com.app.theshineindia.home.HomeActivity;
import com.app.theshineindia.loaders.JSONFunctions;
import com.app.theshineindia.utils.AppData;
import com.app.theshineindia.utils.SP;

import java.util.HashMap;

public class IntruderSelfiePresenter extends BasePresenter {
    Context context;

    public IntruderSelfiePresenter(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public void getJSONResponseResult(String result, int url_no) {
        if (getpDialog().isShowing()) {
            getpDialog().dismiss();
        }

        if (url_no == WebServices.request_url_no_1) {
            SharedMethods.isSuccess(result, context);
        }
    }

    void requestUploadSelfie(String image_str) {
        if (JSONFunctions.isInternetOn(context)) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", SP.getStringPreference(context, SP.user_id));
            hashMap.put("lat", String.valueOf(AppData.location.latitude));
            hashMap.put("long", String.valueOf(AppData.location.longitude));
            hashMap.put("address", AppData.location.address);
            hashMap.put("image", image_str);

            getJfns().makeHttpRequest(WebServices.upload_selfi, "POST", hashMap, false, WebServices.request_url_no_1);
        } else {
            Toast.makeText(context, context.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }
}
