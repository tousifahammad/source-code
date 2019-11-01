package com.app.cubeapparels.master_entry;

import android.widget.Toast;

import com.app.cubeapparels.R;
import com.app.cubeapparels.addorder.Company;
import com.app.cubeapparels.addorder.Design;
import com.app.cubeapparels.allocate_order.Karigar;
import com.app.cubeapparels.baseclasses.BasePresenter;
import com.app.cubeapparels.baseclasses.SharedMethods;
import com.app.cubeapparels.baseclasses.WebServices;
import com.app.cubeapparels.loaders.JSONFunctions;
import com.app.cubeapparels.utils.Alert;
import com.app.cubeapparels.utils.IntentController;
import com.app.cubeapparels.utils.SP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MasterEntryPresenter extends BasePresenter {
    MasterEntryActivity activity;

    MasterEntryPresenter(MasterEntryActivity activity) {
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
                responseCompanyDetails(result);
            }
        } else if (url_no == WebServices.request_url_no_2) {
            if (SharedMethods.isSuccess(result, activity)) {
                responseDesigns(result);
            }
        } else if (url_no == WebServices.request_url_no_3) {
            if (SharedMethods.isSuccess(result, activity)) {
                responseKarigar(result);
            }
        }
    }

    void requestCompanyDetails() {
        if (JSONFunctions.isInternetOn(activity)) {
            getpDialog().setMessage("Getting companies details. Please wait...");
            getpDialog().show();

            getJfns().makeHeaderPOSTRequest(WebServices.managecompany, WebServices.request_url_no_1, activity);

        } else {
            Alert.showError(activity, activity.getString(R.string.no_internet));
        }
    }

    private void responseCompanyDetails(String response) {
        try {
            JSONArray jsonArray = SharedMethods.getDataArray(response, activity);
            if (jsonArray != null) {
                activity.company_list.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject company_jo = jsonArray.getJSONObject(i);
                    Company company = new Company(
                            company_jo.getString("id"),
                            company_jo.getString("company_name"),
                            company_jo.getString("address"),
                            company_jo.getString("gst_number"),
                            company_jo.getString("mobile_number")
                    );

                    activity.company_list.add(company);
                }

                requestDesigns();
            }
        } catch (Exception ex) {
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void requestDesigns() {
        if (JSONFunctions.isInternetOn(activity)) {
            getpDialog().setMessage("Getting designs. Please wait...");
            getpDialog().show();

            getJfns().makeHeaderPOSTRequest(WebServices.managedesign, WebServices.request_url_no_2, activity);

        } else {
            Alert.showError(activity, activity.getString(R.string.no_internet));
        }
    }

    private void responseDesigns(String response) {
        try {
            JSONArray jsonArray = SharedMethods.getDataArray(response, activity);
            if (jsonArray != null) {
                activity.design_list.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject design_jo = jsonArray.getJSONObject(i);
                    Design design = new Design(
                            design_jo.getString("id"),
                            design_jo.getString("design_number"),
                            design_jo.getString("imagepath"),
                            design_jo.getJSONArray("getdesignimage")
                    );

                    activity.design_list.add(design);
                }

                requestKarigar();
            }
        } catch (Exception ex) {
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void requestKarigar() {
        if (JSONFunctions.isInternetOn(activity)) {
            getpDialog().setMessage("Getting karigars list. Please wait...");
            getpDialog().show();

            getJfns().makeHeaderPOSTRequest(WebServices.managekarigar, WebServices.request_url_no_3, activity);

        } else {
            Alert.showError(activity, activity.getString(R.string.no_internet));
        }
    }

    private void responseKarigar(String response) {
        try {
            JSONArray jsonArray = SharedMethods.getDataArray(response, activity);
            if (jsonArray != null) {
                activity.karigar_list.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject karigar_jo = jsonArray.getJSONObject(i);
                    Karigar karigar = new Karigar(
                            karigar_jo.getString("id"),
                            karigar_jo.getString("name"),
                            karigar_jo.getString("address"),
                            karigar_jo.getString("mobile_number")
                    );

                    activity.karigar_list.add(karigar);
                }
            }
        } catch (Exception ex) {
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        activity.setupViewPager();
    }
}
