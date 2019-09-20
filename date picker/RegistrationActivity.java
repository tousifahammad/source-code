package com.app.baseproject.registration;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.app.baseproject.R;
import com.app.baseproject.baseclasses.SharedMethods;
import com.app.baseproject.utils.Alert;

import java.util.Calendar;


public class RegistrationActivity extends AppCompatActivity {
    TextView tv_dob;
    int year, month, day;
    final int DATE_PICKER_ID = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
		
        initUI();
    }


    private void initUI() {
        tv_dob = findViewById(R.id.tv_dob);
    }
        if (et_name.getText().toString().isEmpty()) {
            Alert.showError(this, "First name : " + getString(R.string.field_empty));
            return false;

        } else if (!SharedMethods.validateName(et_name.getText().toString().trim())) {
            Alert.showError(this, "Invalid first name");
            return false;

        } else if (tv_dob.getText().toString().isEmpty()) {
            Alert.showError(this, "DOB : " + getString(R.string.field_empty));
            return false;

        } else if (et_email.getText().toString().isEmpty()) {
            Alert.showError(this, "Email : " + getString(R.string.field_empty));
            return false;

        } else if (!SharedMethods.validateEmail(et_email.getText().toString().trim())) {
            Alert.showError(this, "Invalid email");
            return false;

        } else if (et_mobile_no.getText().toString().trim().length() != 10) {
            Alert.showError(this, "Please enter 10 digit mobile number");
            return false;

        } else if (et_location.getText().toString().isEmpty()) {
            Alert.showError(this, "Location : " + getString(R.string.field_empty));
            return false;
        }

        return true;
    }


    public void pickDate(View view) {
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
            tv_dob.setText(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).append(" "));
        }
    };

}