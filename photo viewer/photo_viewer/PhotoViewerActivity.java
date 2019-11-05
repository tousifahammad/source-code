package com.app.cubeapparels.photo_viewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.cubeapparels.R;
import com.app.cubeapparels.add_company.AddCompanyPresenter;
import com.app.cubeapparels.utils.Alert;
import com.app.cubeapparels.utils.AppData;
import com.app.cubeapparels.utils.Validator;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class PhotoViewerActivity extends AppCompatActivity {
    PhotoView photoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);

        // set Toolbar
        setToolBar();

        initUI();

        setImage();
    }

    private void setToolBar() {
        TextView tv_tooltitle = findViewById(R.id.tv_title_center);
        tv_tooltitle.setText("Viewer");

        findViewById(R.id.ib_back).setOnClickListener(view -> onBackPressed());

    }

    private void initUI() {
        photoView = findViewById(R.id.photo_view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void setImage() {
        try {
            String image_url = getIntent().getStringExtra("image_url");
            if (image_url != null) {
                Glide.with(this)
                        .load(image_url)
                        .override(100, 100)
                        .into(photoView);

            } else if (AppData.bitmap != null) {
                photoView.setImageBitmap(AppData.bitmap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
