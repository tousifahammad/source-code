package com.app.cubeapparels.search;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.cubeapparels.R;
import com.app.cubeapparels.orderallocation.allocated.AllocatedOrder;
import com.app.cubeapparels.orderlist.Order;
import com.app.cubeapparels.utils.Alert;
import com.app.cubeapparels.utils.AppData;

import java.util.ArrayList;
import java.util.Calendar;


public class SearchActivity extends AppCompatActivity {
    RecyclerView rv_saloon_list;
    ArrayList<Order> saloon_list = new ArrayList<>();
    public ArrayList<AllocatedOrder> allocated_order__list = new ArrayList<>();
    TextView tv_search, tv_from_date, tv_to_date, tv_search_result;
    SearchPresenter presenter;
    ProgressBar pb_horizontal;
    String activity_name, type = "";
    EditText et_search;
    int year, month, day, selected_view_id;
    final int DATE_PICKER_ID = 11;
    Button btn_company, btn_design;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        presenter = new SearchPresenter(this);

        // set Toolbar
        initUI();

        // set Toolbar
        setToolBar();

        setRecyclerView();

        setData();

    }


    private void initUI() {
        rv_saloon_list = findViewById(R.id.rv_order_list);
        tv_search = findViewById(R.id.tv_search);
        tv_from_date = findViewById(R.id.tv_from_date);
        tv_to_date = findViewById(R.id.tv_to_date);
        et_search = findViewById(R.id.et_search);
        tv_search_result = findViewById(R.id.tv_search_result);
        btn_company = findViewById(R.id.btn_company);
        btn_design = findViewById(R.id.btn_design);
        pb_horizontal = findViewById(R.id.pb_horizontal);
    }

    private void setToolBar() {
        TextView tv_tool_title = findViewById(R.id.tv_title_center);
        tv_tool_title.setText("Search");


        ImageView iv = findViewById(R.id.ib_back);
        iv.setOnClickListener(v -> {
            finish();
        });
    }


    private void setData() {
        activity_name = getIntent().getStringExtra("activity_name");
        if (activity_name != null) {
            if (activity_name.equals("OrderListActivity")) {
                et_search.setHint("Search by company");
                btn_company.setText("by company");
                type = AppData.SearchType.customersearch;

            } else if (activity_name.equals("OrderAllocationActivity")) {
                et_search.setHint("Search by karigar");
                btn_company.setText("by karigar");
                type = AppData.SearchType.karigarsearch;
            }
        }
    }


    private void setRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_saloon_list.setLayoutManager(mLayoutManager);
        rv_saloon_list.setItemAnimator(new DefaultItemAnimator());
    }

    public void setAdapter() {
        SearchOrderAdapter mAdapter = new SearchOrderAdapter(this);
        rv_saloon_list.setAdapter(mAdapter);

        tv_search_result.setText("Search result (" + saloon_list.size() + ")");
    }

    public void setAllocationAdapter() {
        SearchAllocationAdapter mAdapter = new SearchAllocationAdapter(this);
        rv_saloon_list.setAdapter(mAdapter);

        tv_search_result.setText("Search result (" + allocated_order__list.size() + ")");
    }

    public void onByCompanyClicked(View view) {
        if (activity_name.equals("OrderListActivity")) {
            type = AppData.SearchType.customersearch;
            et_search.setHint("Search by company");

        } else if (activity_name.equals("OrderAllocationActivity")) {
            type = AppData.SearchType.karigarsearch;
            et_search.setHint("Search by karigar");
        }

        btn_company.setTextColor(getResources().getColor(R.color.colorAccent));
        btn_design.setTextColor(getResources().getColor(R.color.darkGray));
    }

    public void onByDesignClicked(View view) {
        type = AppData.SearchType.designsearch;
        et_search.setHint("Search by design");

        btn_company.setTextColor(getResources().getColor(R.color.darkGray));
        btn_design.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    public void onSearchClicked(View view) {
        if (validateEveryField()) {
            presenter.requestSearch();
        }
    }

    private boolean validateEveryField() {
        if (et_search.getText().toString().isEmpty()) {
            Alert.showError(this, "Please enter name or design no to search");
            return false;

        } else if (tv_from_date.getText().toString().isEmpty() && !tv_to_date.getText().toString().isEmpty()) {
            Alert.showError(this, "Please select from date");
            return false;
        }
//        else if (!tv_from_date.getText().toString().isEmpty() && tv_to_date.getText().toString().isEmpty()) {
//            Alert.showError(this, "Please select to date");
//            return false;
//        }

        return true;
    }


    public void onCalenderClicked(View view) {
        selected_view_id = view.getId();

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        showDialog(DATE_PICKER_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                DatePickerDialog dialog = new DatePickerDialog(this, pickerListener, year, month, day);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // Show selected date
            if (selected_view_id == R.id.tv_from_date)
                tv_from_date.setText(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).append(" "));
            if (selected_view_id == R.id.tv_to_date)
                tv_to_date.setText(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).append(" "));
        }
    };
}
