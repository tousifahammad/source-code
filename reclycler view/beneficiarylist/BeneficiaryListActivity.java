package com.app.alevant.DMT.beneficiarylist;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.app.alevant.DMT.addbeneficiary.AddBeneficiaryActivity;
import com.app.alevant.R;
import com.app.alevant.baseclasses.BaseAppCompatActivity;
import com.app.alevant.baseclasses.SharedMethods;
import com.app.alevant.utils.IntentController;

import java.util.ArrayList;

public class BeneficiaryListActivity extends BaseAppCompatActivity implements BeneficiaryAdapter.ClickListener {
  RecyclerView rv_bene_list;
  ArrayList<Beneficiary> bene_list = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_beneficiary_list);
    // set Toolbar
    initUI();

    new BeneficiaryListPresenter(this).requestBeneficiaryList();
  }


  private void initUI() {
    rv_bene_list = findViewById(R.id.rv_bene_list);

    // set Toolbar
    setToolBar();

    setRecyclerView();
  }


  private void setToolBar() {
    AppCompatTextView tv_tool_title = findViewById(R.id.tv_app_name);
    tv_tool_title.setVisibility(View.VISIBLE);
    tv_tool_title.setText(getString(R.string.title_beneficiary_list));

    findViewById(R.id.ib_back).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }


  private void setRecyclerView() {
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
    rv_bene_list.setLayoutManager(mLayoutManager);
  }

  public void setAdapter() {
    BeneficiaryAdapter mAdapter = new BeneficiaryAdapter(bene_list);
    rv_bene_list.setAdapter(mAdapter);
    mAdapter.setClickListener(this);
  }

  public void onAddBeneficiaryClicked(View view) {
    IntentController.sendIntent(BeneficiaryListActivity.this, AddBeneficiaryActivity.class);
  }

  @Override
  public void getJSONResponseResult(String result, int url_no) {
    if (pDialog.isShowing()) {
      pDialog.dismiss();
    }
    if (url_no == url_no_dmt_otp) {
      if (SharedMethods.isSuccess(result, this)) {
        responseDMTOTP(result);
      }
    } else if (url_no == url_no_verify_otp) {
      if (SharedMethods.isSuccess(result, this)) {
        responseVerifyOTP(result);
      }
    }
  }

  @Override
  public void itemClicked(View view, int position) {
    if (view.getId() == R.id.tv_fund_transfer) {
      //clickListener.itemClicked(view, getLayoutPosition());
      Toast.makeText(view.getContext(), "fund transfer", Toast.LENGTH_SHORT).show();

    } else if (view.getId() == R.id.tv_validate) {
      Toast.makeText(view.getContext(), "tv_validate", Toast.LENGTH_SHORT).show();

    } else if (view.getId() == R.id.tv_verify) {
      Toast.makeText(view.getContext(), "tv_verify", Toast.LENGTH_SHORT).show();

    } else if (view.getId() == R.id.tv_cancel) {
      Toast.makeText(view.getContext(), "cancel", Toast.LENGTH_SHORT).show();
    }
  }
}
