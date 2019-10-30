package com.app.alevant.DMT.beneficiarylist;

import com.app.alevant.R;
import com.app.alevant.baseclasses.Alert;
import com.app.alevant.baseclasses.BasePresenter;
import com.app.alevant.baseclasses.SharedMethods;
import com.app.alevant.baseclasses.WebService;
import com.app.alevant.loaders.JSONFunctions;
import com.app.alevant.utils.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BeneficiaryListPresenter extends BasePresenter {
  //WEB SERVICE
  private final String beneficiary_list = "beneficiary_list";
  private final int url_no_beneficiary_list = 1;

  BeneficiaryListActivity activity;

  public BeneficiaryListPresenter(BeneficiaryListActivity activity) {
    super(activity);
    this.activity = activity;
  }


  @Override
  public void getJSONResponseResult(String result, int url_no) {
    if (getpDialog().isShowing()) {
      getpDialog().dismiss();
    }
    switch (url_no) {
      case url_no_beneficiary_list:
        responseBeneficiaryList(result);
        break;
    }
  }


  public void requestBeneficiaryList() {
    if (JSONFunctions.isInternetOn(activity)) {
      getpDialog().setMessage("Getting beneficiaries. Please wait...");
      getpDialog().show();

      String url = WebService.commonUrlAEPS + beneficiary_list;

      HashMap<String, String> hm_body = new HashMap<>();
      hm_body.put("agent_code", getSsp().getAccessToken());
      hm_body.put("mobile_no", "7782025623");
      hm_body.put("st", AppData.st_dmt);

      getJfns().makeHttpRequest(url, "POST", hm_body, false, url_no_beneficiary_list);
    } else {
      Alert.errorMessage(activity, activity.getString(R.string.no_internet));
    }

  }

  private void responseBeneficiaryList(String result) {
    if (SharedMethods.isSuccess(result, activity)) {
      try {
        JSONObject jsonObject = new JSONObject(result);
        Alert.showMessage(activity, jsonObject.getString(WebService.message));

        JSONObject jsonObject1 = jsonObject.getJSONObject("response");
        JSONObject jobj_data = jsonObject1.getJSONObject("DATA");
        JSONArray jsonArray = jobj_data.getJSONArray("BENEFICIARY_DATA");
        if (jsonArray.length() > 0) {
          for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            String BENE_ID = jsonObject2.getString("BENE_ID");
            String BENE_NAME = jsonObject2.getString("BENE_NAME");
            String BENE_BANKNAME = jsonObject2.getString("BENE_BANKNAME");
            String BANK_ACCOUNTNO = jsonObject2.getString("BANK_ACCOUNTNO");
            String BANKIFSC_CODE = jsonObject2.getString("BANKIFSC_CODE");
            String BENE_OTP_VERIFIED = jsonObject2.getString("BENE_OTP_VERIFIED");
            String IS_BENEVERIFIED = jsonObject2.getString("IS_BENEVERIFIED");

            activity.bene_list.add(new Beneficiary(BENE_ID, BENE_NAME, BENE_BANKNAME, BANK_ACCOUNTNO, BANKIFSC_CODE, BENE_OTP_VERIFIED, IS_BENEVERIFIED));
          }
          activity.setAdapter();

        } else {
          Alert.showMessage(activity, "No data found");
        }
      } catch (JSONException ex) {
        Alert.errorMessage(activity, "Error : " + ex.getMessage());
      }
    }
  }

}
