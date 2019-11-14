package com.app.cubeapparels.master_entry;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cubeapparels.R;
import com.app.cubeapparels.addorder.Company;
import com.app.cubeapparels.addorder.Design;
import com.app.cubeapparels.allocate_order.Karigar;
import com.app.cubeapparels.master_entry.fragment.company.CompanyFragment;
import com.app.cubeapparels.master_entry.fragment.design.DesignFragment;
import com.app.cubeapparels.master_entry.fragment.karigar.KarigarFragment;

import java.util.ArrayList;

public class MasterEntryActivity extends AppCompatActivity implements MasterEntryClickListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    TabLayout tabLayout;
    MasterEntryPresenter presenter;
    ArrayList<Company> company_list = new ArrayList<>();
    ArrayList<Design> design_list = new ArrayList<>();
    ArrayList<Karigar> karigar_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_entry);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // set Toolbar
        initUI();

        // set Toolbar
        setToolBar();

        presenter = new MasterEntryPresenter(this);
        presenter.requestCompanyDetails();
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
        TextView tv_tooltitle = findViewById(R.id.tv_title_center);
        tv_tooltitle.setText("Master Entry");

        findViewById(R.id.ib_back).setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


    void setupViewPager() {
        if (company_list.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("company_list", company_list);

            Fragment fragment = new CompanyFragment();
            fragment.setArguments(bundle);

            mSectionsPagerAdapter.addFragment(fragment, "Company");
        }

        if (design_list.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("design_list", design_list);

            Fragment fragment = new DesignFragment();
            fragment.setArguments(bundle);

            mSectionsPagerAdapter.addFragment(fragment, "Design");
        }

        if (karigar_list.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("karigar_list", karigar_list);

            Fragment fragment = new KarigarFragment();
            fragment.setArguments(bundle);

            mSectionsPagerAdapter.addFragment(fragment, "Karigar");
        }

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

    @Override
    public void onCancelClicked(String booking_id) {
        Toast.makeText(this, "Not available right now", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onModifyClicked(String booking_id) {
        Toast.makeText(this, "Not available right now", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemainderChanged(boolean isChecked, String booking_id) {
        Toast.makeText(this, "Not available right now", Toast.LENGTH_SHORT).show();
    }
}
