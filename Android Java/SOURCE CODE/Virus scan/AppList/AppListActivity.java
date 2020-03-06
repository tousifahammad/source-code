package com.app.theshineindia.AppList;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.theshineindia.R;
import com.narayanacharya.waveview.WaveView;
import com.yangp.ypwaveview.YPWaveView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AppListActivity extends AppCompatActivity {
    RecyclerView rv_app_list;
    AppListAdapter mAdapter;
    List<App> app_list = new ArrayList<>();

    RelativeLayout RL_wave_view;
    YPWaveView yp_waveView;
    WaveView waveView;
    TextView tv_app;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        gotoNextScreen(getRandomNumberInRange(4, 10) * 1000);

        setToolbar();

        initUI();

        setupRecyclerView();



        app_list = new ApkInfoExtractor(this).GetAllInstalledApkInfo();
    }

    private void initUI() {
        RL_wave_view = findViewById(R.id.RL_wave_view);
        rv_app_list = findViewById(R.id.rv_app_list);
        yp_waveView = findViewById(R.id.YPWaveView);
        waveView = findViewById(R.id.waveView);
        tv_app = findViewById(R.id.tv_app);

        waveView.play();
    }

    private void setToolbar() {
        findViewById(R.id.ib_phoneinfo_back).setOnClickListener(view -> onBackPressed());
    }



    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private void gotoNextScreen(long delay) {
        count = app_list.size();

        new CountDownTimer(delay, 50) {

            public void onTick(long millisUntilFinished) {
                long d1 = delay - millisUntilFinished;
                double d2 = (double) d1 / (double) delay;
                int d3 = (int) (d2 * 1000);

                yp_waveView.setProgress(d3);

                if (app_list.size() > 0) {
                    if (count < app_list.size()) {
                        tv_app.setText(app_list.get(count).getPackage_name());
                        count++;
                    } else {
                        count = 0;
                    }
                }
            }

            public void onFinish() {
                waveView.pause();
                RL_wave_view.setVisibility(View.GONE);
                count = 0;
                setAdapter();
            }

        }.

                start();
    }


    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_app_list.setLayoutManager(linearLayoutManager);
        //rv_app_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void setAdapter() {
        mAdapter = new AppListAdapter(app_list, this);
        rv_app_list.setAdapter(mAdapter);
    }
}
