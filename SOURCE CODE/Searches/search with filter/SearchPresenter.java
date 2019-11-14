package com.app.cubeapparels.search;

import android.view.View;
import android.widget.Toast;

import com.app.cubeapparels.R;
import com.app.cubeapparels.baseclasses.BasePresenter;
import com.app.cubeapparels.baseclasses.SharedMethods;
import com.app.cubeapparels.baseclasses.WebServices;
import com.app.cubeapparels.loaders.JSONFunctions;
import com.app.cubeapparels.orderallocation.allocated.AllocatedOrder;
import com.app.cubeapparels.orderlist.Order;
import com.app.cubeapparels.utils.Alert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SearchPresenter extends BasePresenter {
    private SearchActivity activity;

    SearchPresenter(SearchActivity activity) {
        super(activity);
        this.activity = activity;
    }


    @Override
    public void getJSONResponseResult(String result, int url_no) {
        if (activity.pb_horizontal.getVisibility() == View.VISIBLE) {
            activity.pb_horizontal.setVisibility(View.GONE);
        }

        if (url_no == WebServices.request_url_no_1) {
            responseOrderSearch(result);

        } else if (url_no == WebServices.request_url_no_2) {
            responseAllocationSearch(result);
        }
    }

    void requestSearch() {
        if (JSONFunctions.isInternetOn(activity)) {
            activity.pb_horizontal.setVisibility(View.VISIBLE);

            //token, type=customersearch/designsearch, search_text, startdate, enddate
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("type", activity.type);
            hashMap.put("search_text", activity.et_search.getText().toString().trim());
            hashMap.put("startdate", activity.tv_from_date.getText().toString().trim());
            hashMap.put("enddate", activity.tv_to_date.getText().toString().trim());

            if (activity.activity_name.equals("OrderListActivity")) {
                getJfns().makeHeaderBodyPOSTRequest(WebServices.ordersearch, hashMap, WebServices.request_url_no_1, activity);

            } else if (activity.activity_name.equals("OrderAllocationActivity")) {
                getJfns().makeHeaderBodyPOSTRequest(WebServices.orderallocationsearch, hashMap, WebServices.request_url_no_2, activity);
            }

        } else {
            Toast.makeText(activity, activity.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }


    private void responseOrderSearch(String response) {
        try {
            JSONArray jsonArray = SharedMethods.getDataArray(response, activity, activity.rv_saloon_list);
            activity.saloon_list.clear();

            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject order_jo = jsonArray.getJSONObject(i);

                    Order order = new Order(
                            order_jo.getString("id"),
                            order_jo.getString("orderno"),
                            null,
                            null,
                            null,
                            order_jo.getString("rate"),
                            order_jo.getString("quantity"),
                            order_jo.getString("delivery_date"),
                            order_jo.getString("remarks"),
                            order_jo.getString("status"),
                            order_jo.getString("desingno"),
                            null,
                            order_jo.getString("companyname")
                    );

                    activity.saloon_list.add(order);
                }
            }

            activity.setAdapter();

        } catch (JSONException ex) {
            Alert.showError(activity, ex.getMessage());
        }
    }

    private void responseAllocationSearch(String response) {
        try {
            JSONArray jsonArray = SharedMethods.getDataArray(response, activity, activity.rv_saloon_list);
            activity.allocated_order__list.clear();

            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject order_jo = jsonArray.getJSONObject(i);

                    AllocatedOrder allocated_order = new AllocatedOrder(
                            order_jo.getString("id"),
                            order_jo.getString("challan_no"),
                            order_jo.getString("given_date"),
                            order_jo.getString("return_date"),
                            order_jo.getString("remarks"),
                            order_jo.getString("status"),
                            order_jo.getString("karigarname"),
                            order_jo.getString("desingno"),
                            null
                    );
                    activity.allocated_order__list.add(allocated_order);
                }
            }

            activity.setAllocationAdapter();

        } catch (JSONException ex) {
            Alert.showError(activity, ex.getMessage());
        }
    }
}
