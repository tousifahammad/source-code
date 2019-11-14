package com.app.bopufit.aboutus;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bopufit.R;
import com.app.bopufit.aboutus.fragment.AboutFragment;
import com.app.bopufit.aboutus.fragment.DisclaimerFragment;
import com.app.bopufit.aboutus.fragment.PrivacyFragment;
import com.app.bopufit.aboutus.fragment.RefundFragment;
import com.app.bopufit.aboutus.fragment.TermFragment;

import java.util.ArrayList;

public class AboutUsActivity extends AppCompatActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        // set Toolbar
        initUI();

        // set Toolbar
        updateToolBar();
    }


    private void initUI() {
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private void updateToolBar() {
        ImageView ib_back = findViewById(R.id.ib_back);
        TextView common_title = findViewById(R.id.common_title);
        common_title.setText("About Us");
        ib_back.setVisibility(View.VISIBLE);
        ib_back.setImageResource(R.drawable.arrow_back_white);
        ib_back.setOnClickListener(new View.OnClickListener() {
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
    protected void onStart() {
        super.onStart();

        setupViewPager();
    }

    void setupViewPager() {
        Fragment fragment = new AboutFragment();
        mSectionsPagerAdapter.addFragment(fragment, "About Us");
        fragment = new PrivacyFragment();
        mSectionsPagerAdapter.addFragment(fragment, "Privacy Policy");

        fragment = new RefundFragment();
        mSectionsPagerAdapter.addFragment(fragment, "Refund Policy");

        fragment = new DisclaimerFragment();
        mSectionsPagerAdapter.addFragment(fragment, "Disclaimer");

        fragment = new TermFragment();
        mSectionsPagerAdapter.addFragment(fragment, "Term & Condition");

        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
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

}
