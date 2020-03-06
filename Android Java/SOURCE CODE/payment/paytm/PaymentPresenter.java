package com.app.bopufit.payment;

import com.app.bopufit.baseclasses.BasePresenter;
import com.app.bopufit.utils.SettingSharedPreferences;

public class PaymentPresenter extends BasePresenter {
    PaymentActivity activity;
    private SettingSharedPreferences ssp;

    public PaymentPresenter(PaymentActivity activity) {
        super(activity);
        this.activity = activity;
        ssp = new SettingSharedPreferences(activity);
    }


    @Override
    public void getJSONResponseResult(String message, String result, int url_no) {
        if (getpDialog().isShowing()) {
            getpDialog().dismiss();
        }

        switch (url_no) {
//            case WebServices.request_url_no_1:
//                if (result != null) {
//                    getPaymentResult(result);
//                }
//                break;
//            case WebServices.request_url_no_2:
//                if (result != null) {
//                    responsePaymentResponse(result);
//                }
//                break;
        }
    }

//    public void requestPaymentService(ServiceDetailsModel vsmModel) {
//        if (JSONFunctions.isInternetOn(activity)) {
//            getpDialog().setMessage("Connecting to server. Please wait...");
//            getpDialog().setCancelable(false);
//            getpDialog().show();
//
//            String url = Const.Services.Pay_now;
//
//            HashMap<String, String> hashMap = new HashMap<>();
//            hashMap.put(Const.Params.User_id, ssp.getUserId());
//            hashMap.put(Const.Params.VendorId, vsmModel.getVendor_id());
//            hashMap.put(Const.Params.Vendor_cat_id, vsmModel.getVendor_cat_id());
//            hashMap.put(Const.Params.Payment_mode, vsmModel.getPayment_mode());
//            // Log.e("mode",vsmModel.getPayment_mode());
//            hashMap.put(Const.Params.Billed_amount, vsmModel.getMark_price());
//            hashMap.put(Const.Params.Discount_amount, vsmModel.getDiscount());
//            hashMap.put(Const.Params.offer_discount_amount, vsmModel.getOffer_discount());
//            hashMap.put(Const.Params.Pay_amount, vsmModel.getPayable_price());
//
//            getJfns().makeHttpRequest(url, "POST", hashMap, false, Const.ServiceCode.PAYNOW);
//
//        } else {
//            Alert.showError(activity, activity.getString(R.string.no_internet));
//        }
//    }
//
//    private void getPaymentResult(String response) {
//        try {
//            JSONObject jsonObject = new JSONObject(response.trim());
//
//            if (jsonObject.getInt("result") == 0) {
//                openResponseDialog(jsonObject.getString("message"), 0);
//
//            } else if (jsonObject.getInt("result") == 1) {
//                openResponseDialog(jsonObject.getString("message"), 1);
//
//            } else {
//                JSONArray arrdata = jsonObject.getJSONArray("data");
//                if (arrdata.length() > 0) {
//                    JSONObject data = arrdata.getJSONObject(0);
//
//                    HashMap<String, String> paramMap = new HashMap<>();
//                    paramMap.put(Const.Params.CALLBACK_URL, data.getString("CALLBACK_URL"));
//                    paramMap.put(Const.Params.MID, data.getString("MID"));
//                    paramMap.put(Const.Params.ORDER_ID, data.getString("ORDER_ID"));
//                    ssp.saveOrderId(data.getString("ORDER_ID"));
//                    //Log.e("ORDER ID",app.ssp.getOrdderId());
//                    paramMap.put(Const.Params.CUST_ID, data.getString("CUST_ID"));
//                    paramMap.put(Const.Params.INDUSTRY_TYPE_ID, data.getString("INDUSTRY_TYPE_ID"));
//                    paramMap.put(Const.Params.CHANNEL_ID, data.getString("CHANNEL_ID"));
//                    paramMap.put(Const.Params.MOBILE_NO, data.getString("MOBILE_NO"));
//                    paramMap.put(Const.Params.EMAIL, data.getString("EMAIL"));
//                    paramMap.put(Const.Params.WEBSITE, data.getString("WEBSITE"));
//                    paramMap.put(Const.Params.TXN_AMOUNT, data.getString("TXN_AMOUNT"));
//                    paramMap.put(Const.Params.CHECKSUMHASH, data.getString("CHECKSUMHASH"));
//
//                    activity.navigateScreen(paramMap);
//                }
//                //  Utils.showToast(jsonObject.getString("message"), context);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void requestPaymentResponse(String bundle, ServiceDetailsModel vsmModel) {
//        if (JSONFunctions.isInternetOn(activity)) {
//            getpDialog().setMessage("Sending PAYTM response to server. Please wait...");
//            getpDialog().setCancelable(false);
//            getpDialog().show();
//
//            String url = Const.Services.payment_response;
//
//            HashMap<String, String> hashMap = new HashMap<>();
//            hashMap.put(Const.Params.bundle, bundle);
//            hashMap.put(Const.Params.User_id, ssp.getUserId());
//            hashMap.put(Const.Params.ORDER_ID, ssp.getOrdderId());
//            hashMap.put(Const.Params.VendorId, vsmModel.getVendor_id());
//            hashMap.put(Const.Params.Vendor_cat_id, vsmModel.getVendor_cat_id());
//            hashMap.put(Const.Params.Payment_mode, vsmModel.getPayment_mode());
//            // Log.e("mode",vsmModel.getPayment_mode());
//            hashMap.put(Const.Params.Billed_amount, vsmModel.getMark_price());
//            hashMap.put(Const.Params.Discount_amount, vsmModel.getDiscount());
//            hashMap.put(Const.Params.Pay_amount, vsmModel.getPayable_price());
//
//            getJfns().makeHttpRequest(url, "POST", hashMap, false, payment_response);
//
//        } else {
//            Alert.showError(activity, activity.getString(R.string.no_internet));
//        }
//    }
//
//    private void responsePaymentResponse(String result) {
//        try {
//            JSONObject jsonObject = new JSONObject(result.trim());
//            openResponseDialog(jsonObject.getString("message"), jsonObject.getInt("result"));
//
//        } catch (JSONException e) {
//            Alert.showError(activity, "Error : " + e.getMessage());
//        }
//    }
//
//    private void openResponseDialog(String message, int status) {
//        CustomDialogClass cdd = new CustomDialogClass(message, status);
//        cdd.setCancelable(false);
//        cdd.show();
//    }
//
//    public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {
//        Button yes;
//        TextView tv_payment_response;
//        String message;
//        int status;
//
//        CustomDialogClass(String message, int status) {
//            super(activity);
//            this.message = message;
//            this.status = status;
//        }
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//            setContentView(R.layout.dialog_payment_success);
//            yes = findViewById(R.id.btn_yes);
//            yes.setOnClickListener(this);
//
//            tv_payment_response = findViewById(R.id.tv_payment_response);
//            tv_payment_response.setText(message);
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (v.getId() == R.id.btn_yes) {
//                if (status == 1) {
//                    //activity.showAlertDialog();
//                }
//            }
//            dismiss();
//        }
//    }
}
