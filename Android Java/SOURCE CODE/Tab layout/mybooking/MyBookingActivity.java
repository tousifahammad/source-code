package com.app.haircutuser.mybooking;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.app.haircutuser.R;
import com.app.haircutuser.home.HomeActivity;
import com.app.haircutuser.mybooking.fragment.upcoming.UpcomingFragment;
import com.app.haircutuser.mybooking.fragment.past.PastFragment;
import com.app.haircutuser.utils.IntentController;

import java.util.ArrayList;

public class MyBookingActivity extends AppCompatActivity implements
        UpcomingFragment.OnFragmentInteractionListener,
        PastFragment.OnFragmentInteractionListener,
        BookingClickListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    TabLayout tabLayout;
    MyBookingPresenter presenter;
    ArrayList<Booking> upcoming_booking_list = new ArrayList<>();
    ArrayList<Booking> past_booking_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // set Toolbar
        initUI();

        // set Toolbar
        setToolBar();

        presenter = new MyBookingPresenter(this);
        presenter.requestMyBooking();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        setupViewPager();
//        tabLayout.setupWithViewPager(mViewPager);
//    }

    private void initUI() {
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private void setToolBar() {
        AppCompatTextView tv_tooltitle = findViewById(R.id.tv_tooltitle);
        tv_tooltitle.setVisibility(View.VISIBLE);
        tv_tooltitle.setText("My bookings");

        findViewById(R.id.ib_back).setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        String activity_name = getIntent().getStringExtra("activity_name");
        if (activity_name != null && activity_name.equals("PaymentActivity")) {
            IntentController.sendIntent(this, HomeActivity.class);
        }
        finish();
    }


    void setupViewPager() {
        if (upcoming_booking_list.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("upcoming_booking_list", upcoming_booking_list);

            Fragment fragment = new UpcomingFragment();
            fragment.setArguments(bundle);

            mSectionsPagerAdapter.addFragment(fragment, "Upcoming Booking");
        }

        if (past_booking_list.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("past_booking_list", past_booking_list);

            Fragment fragment = new PastFragment();
            fragment.setArguments(bundle);

            mSectionsPagerAdapter.addFragment(fragment, "Past Booking");
        }

        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
        private final ArrayList<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }

    @Override
    public void onCancelClicked(String booking_id) {
        presenter.requestBookingCancel(booking_id);
    }

    @Override
    public void onModifyClicked(String booking_id) {
        Toast.makeText(this, "Not available right now", Toast.LENGTH_SHORT).show();
        //IntentController.gotToActivityNoBack(this, SaloonActivity.class);
    }

    @Override
    public void onRemainderChanged(boolean isChecked, String booking_id) {
        String is_reminder = "N";
        if (isChecked) {
            is_reminder = "Y";
        }
        presenter.requestRemainder(is_reminder, booking_id);
    }
}
