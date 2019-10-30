package com.app.JainFurnishing.location;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.JainFurnishing.R;
import com.app.JainFurnishing.area_details.AreaDetailsActivity;
import com.app.JainFurnishing.baseclass.BaseAppCompatActivity;
import com.app.JainFurnishing.others_material.OthersMaterialActivity;
import com.app.JainFurnishing.parse.ParseContent;
import com.app.JainFurnishing.utils.AllIntent;
import com.app.JainFurnishing.utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocationActivity extends BaseAppCompatActivity implements LocationAdapter.ClickListener {
    private ArrayList<Location> location_list = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // set Toolbar
        setToolBar();

        setRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        pDialog.setTitle(getString(R.string.please_wait));
        pDialog.setMessage(getString(R.string.getting_data));
        pDialog.show();

        findViewById(R.id.empty_text).setVisibility(View.GONE);

        getLocationList();
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.rv_location_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void setToolBar() {
        AppCompatTextView tv_tooltitle = findViewById(R.id.tv_app_name);
        tv_tooltitle.setVisibility(View.VISIBLE);
        tv_tooltitle.setText(getString(R.string.area_activity_title));
        findViewById(R.id.ib_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void getJSONResponseResult(String message, String result, int url_no) {
        switch (url_no) {
            case Const.RequestCode.LOCATION_LIST:
                pDialog.dismiss();
                getLocationListResponse(result);
                break;
        }
    }

    private void getLocationListResponse(String result) {
        if (new ParseContent().isSuccess(result, this)) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray(Const.ParamsAPI.data);
                if (jsonArray.length() > 0) {
                    location_list.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        String location_id = jsonObj.getString("LocationId");
                        String location_name = jsonObj.getString("LocationName");
                        String location_status = jsonObj.getString("Status");
                        String location_image_url = jsonObj.getString("Image");
                        if (!location_id.isEmpty() && !location_name.isEmpty()) {
                            Location location = new Location(location_id, location_name,location_status,location_image_url );
                            location_list.add(location);
                        }
                    }
                    // add other material at the end of list
                    location_list.add(new Location(Const.other_material_id, Const.other_material_name,null,null));
                } else {
                    findViewById(R.id.empty_text).setVisibility(View.VISIBLE);
                }
                setAdapter();

            } catch (Exception e) {
                Log.d(Const.TAG.SignIn, "Exception: " + e.getMessage());
                findViewById(R.id.empty_text).setVisibility(View.VISIBLE);
            }
        }
    }

    private void setAdapter() {
        LocationAdapter mAdapter = new LocationAdapter(location_list);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
    }

    @Override
    public void itemClicked(View view, int position) {

        if(!location_list.get(position).getLocation_name().isEmpty()){
            Const.location = location_list.get(position).getLocation_name();
            Const.location_img_url = location_list.get(position).getLocation_image();
        }else {
            Toast.makeText(this, "Location not saved", Toast.LENGTH_SHORT).show();
        }

        if (location_list.get(position).getLocation_id() != Const.other_material_id) {
            // got to area details
            AllIntent.gotoActivity(this, AreaDetailsActivity.class);
        } else {
            // got to other material
            AllIntent.gotoActivity(this, OthersMaterialActivity.class);
        }
    }
}
