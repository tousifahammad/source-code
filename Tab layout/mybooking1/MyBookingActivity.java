package com.app.baseproject.mybooking;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.app.baseproject.R;
import com.app.baseproject.mybooking.fragment.PastFragment;
import com.app.baseproject.mybooking.fragment.LandlineFragment;

import java.util.ArrayList;

public class MyBookingActivity extends AppCompatActivity implements
        PastFragment.OnFragmentInteractionListener,
        LandlineFragment.OnFragmentInteractionListener{

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);

        // set Toolbar
        initUI();

        // set Toolbar
        setToolBar();

    }

    @Override
    protected void onStart() {
        super.onStart();

        setupViewPager();
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void initUI() {
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private void setToolBar() {
        AppCompatTextView tv_tooltitle = findViewById(R.id.tv_app_name);
        tv_tooltitle.setVisibility(View.VISIBLE);
        tv_tooltitle.setText("My booking");

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


    private void setupViewPager() {
        mSectionsPagerAdapter.addFragment(LandlineFragment.newInstance("land line"), "land line");
        mSectionsPagerAdapter.addFragment(PastFragment.newInstance("electricity"), "electricity");
        mViewPager.setAdapter(mSectionsPagerAdapter);
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


        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }
}
