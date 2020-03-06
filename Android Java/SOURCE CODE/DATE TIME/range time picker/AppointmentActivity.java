import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.app.haircutuser.R;
import com.app.haircutuser.baseclasses.SharedMethods;
import com.app.haircutuser.payment.PaymentActivity;
import com.app.haircutuser.utils.Alert;
import com.app.haircutuser.utils.AppData;
import com.app.haircutuser.utils.IntentController;
import com.app.haircutuser.utils.RangeTimePickerDialog;
import com.app.haircutuser.utils.SP;
import com.app.haircutuser.utils.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentActivity extends AppCompatActivity {
    CalendarView calendarView;
    TextView tv_booking_time;
    int min_hour, max_hour, min_minute, max_minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // set Toolbar
        setToolBar();

        initUI();

        setClickListener();

        resetData();

    }

        AppData.service.name = "";
        AppData.service.date = "";
        AppData.service.time = "";
        AppData.service.gender = "";
        AppData.service.booking_for = "";
        AppData.service.mobile = "";
        AppData.service.address = "";
    }

 


    private void initUI() {
        calendarView = findViewById(R.id.calendarView);
        tv_booking_time = findViewById(R.id.tv_booking_time);
    }


    private void setClickListener() {
        try {
            calendarView.setMinDate(System.currentTimeMillis());
        } catch (Exception e) {
            Alert.showError(this, e.getMessage());
        }

        date = SharedMethods.getCurrentDate();
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            date = year + "-" + (month + 1) + "-" + dayOfMonth;
        });

    }



   public void openFromTimeFragment(View view) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date my_date = sdf.parse(date);
            if (DateUtils.isToday(my_date.getTime())) {
                String dateString = hh_mm_aa_formater.format(new Date());
                time = getHourMinute(dateString);

                if (time != null) {
                    min_hour = Integer.parseInt(time.split(":")[0]) + 1; //1hr ahead
                    min_minute = Integer.parseInt(time.split(":")[1]);
                }

            } else {
                time = getHourMinute(AppData.service.opening_time);

                if (time != null) {
                    min_hour = Integer.parseInt(time.split(":")[0]);
                    min_minute = Integer.parseInt(time.split(":")[1]);
                }
            }

        } catch (ParseException e) {
            Alert.showError(this, e.getMessage());
        }
		
		if (time != null) {
                    min_hour = Integer.parseInt(time.split(":")[0]);
                    min_minute = Integer.parseInt(time.split(":")[1]);
                }

        time = getHourMinute(AppData.service.closing_time);
        if (time != null) {
            max_hour = Integer.parseInt(time.split(":")[0]);
            max_minute = Integer.parseInt(time.split(":")[1]);
        }

        Log.d("1111", "min_hour: " + min_hour + "    min_minute: " + min_minute);
        Log.d("1111", "max_hour: " + max_hour + "    max_minute: " + max_minute);

        if (min_hour > 0 && max_hour > 0)
            ShowTimePicker();
    }
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date my_date = sdf.parse(date);
            if (DateUtils.isToday(my_date.getTime())) {
                DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
                String dateString = dateFormat.format(new Date());
                time = getHourMinute(dateString);

            } else {
                time = getHourMinute(AppData.service.opening_time);
            }

        } catch (ParseException e) {
            Alert.showError(this, e.getMessage());
        }

        if (time != null) {
            min_hour = Integer.parseInt(time.split(":")[0]);
            min_minute = Integer.parseInt(time.split(":")[1]);
        }

        time = getHourMinute(AppData.service.closing_time);
        if (time != null) {
            max_hour = Integer.parseInt(time.split(":")[0]);
            max_minute = Integer.parseInt(time.split(":")[1]);
        }

        Log.d("1111", "min_hour: " + min_hour + "    min_minute: " + min_minute);
        Log.d("1111", "max_hour: " + max_hour + "    max_minute: " + max_minute);

        if (min_hour > 0 && max_hour > 0)
            ShowTimePicker();
    }

    private String getHourMinute(String time) {
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
            Date date = parseFormat.parse(time);
            return displayFormat.format(date);
        } catch (ParseException e) {
            Alert.showError(this, e.getMessage());
        }

        return null;
    }

    public void ShowTimePicker() {
        RangeTimePickerDialog tpd = new RangeTimePickerDialog(this, new RangeTimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");
                Date date = null;
                try {
                    String time = selectedHour + ":" + selectedMinute;
                    date = fmt.parse(time);
                    tv_booking_time.setText(fmtOut.format(date));

                } catch (ParseException e) {
                    Alert.showError(AppointmentActivity.this, e.getMessage());
                }
            }
        }, min_hour, min_minute, false);
        tpd.setMin(min_hour, min_minute);
        tpd.setMax(max_hour, max_minute);
        tpd.show();
    }


}
