package com.app.haircutuser.review;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.haircutuser.R;
import com.app.haircutuser.baseclasses.SharedMethods;
import com.app.haircutuser.utils.Alert;
import com.app.haircutuser.utils.AppData;


public class ReviewActivity extends AppCompatActivity {
    ReviewPresenter presenter;
    EditText et_remarks;
    RadioGroup rg_rating;
    String vendor_id, booking_id, rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        presenter = new ReviewPresenter(this);

        // set Toolbar
        setToolBar();

        initUI();

        vendor_id = getIntent().getStringExtra("vendor_id");
        booking_id = getIntent().getStringExtra("booking_id");
        if (booking_id == null || vendor_id == null)
            finish();
    }

    private void setToolBar() {
        AppCompatTextView tv_tooltitle = findViewById(R.id.tv_tooltitle);
        tv_tooltitle.setVisibility(View.VISIBLE);
        tv_tooltitle.setText("Rating & Review");

        findViewById(R.id.ib_back).setOnClickListener(view -> {
            finish();
        });
    }

    private void initUI() {
        et_remarks = findViewById(R.id.et_remarks);
        rg_rating = findViewById(R.id.rg_rating);

        setClickListener();

    }

    private void setClickListener() {
        rg_rating.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_review_1) {
                rating = "1";
            } else if (checkedId == R.id.rb_review_2) {
                rating = "2";
            } else if (checkedId == R.id.rb_review_3) {
                rating = "3";
            } else if (checkedId == R.id.rb_review_4) {
                rating = "4";
            } else if (checkedId == R.id.rb_review_5) {
                rating = "5";
            }
        });
    }

    public void onSendClicked(View view) {
        if (validateEveryField()) {
            presenter.requestReview();
        }
    }


    private boolean validateEveryField() {
        if (et_remarks.getText().toString().isEmpty()) {
            Alert.showError(this, "First name : " + getString(R.string.field_empty));
            return false;

        } else if (rating.isEmpty()) {
            Alert.showError(this, "Please rate something");
            return false;
        }

        return true;
    }
}