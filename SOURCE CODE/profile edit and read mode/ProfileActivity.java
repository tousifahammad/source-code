package com.app.haircutuser.profile;

import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.haircutuser.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    TextView tv_edit, tv_phone_no, tv_date_of_birth;
    EditText et_name, et_locaton, et_email_Id;
    ConstraintLayout cl_root_view;
    Button btn_update;
    boolean isEditModeOn;
    List<View> all_view = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // set Toolbar
        setToolBar();

        initUI();

        all_view = getAllChildren(cl_root_view);
        changeEditMode();
    }

    private void setToolBar() {
        AppCompatTextView tv_tooltitle = findViewById(R.id.tv_tooltitle);
        tv_tooltitle.setVisibility(View.VISIBLE);
        tv_tooltitle.setText(getString(R.string.title_profile));
        findViewById(R.id.ib_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }


    private void initUI() {
        cl_root_view = findViewById(R.id.cl_root_view);
        tv_edit = findViewById(R.id.tv_edit);
        tv_phone_no = findViewById(R.id.tv_phone_no);
        tv_date_of_birth = findViewById(R.id.tv_date_of_birth);
        et_name = findViewById(R.id.et_name);
        et_locaton = findViewById(R.id.et_locaton);
        et_email_Id = findViewById(R.id.et_email_Id);
        btn_update = findViewById(R.id.btn_update);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onEditModeClicked(View view) {
        changeEditMode();
    }

    private void changeEditMode() {
        if (isEditModeOn) { // edit mode on
            Toast.makeText(this, "Edit mode on", Toast.LENGTH_SHORT).show();

            isEditModeOn = false;
            tv_edit.setText("Edit Mode");
            btn_update.setVisibility(View.VISIBLE);

            for (int i = 0; i < all_view.size(); i++) {
                if (all_view.get(i) instanceof EditText) {
                    EditText editText = (EditText) all_view.get(i);
                    editText.setEnabled(true);

                } else if (all_view.get(i) instanceof CircleImageView) {
                    CircleImageView imageView = (CircleImageView) all_view.get(i);
                    imageView.setClickable(true);

                } else if (all_view.get(i) instanceof ImageView) {
                    ImageView imageView = (ImageView) all_view.get(i);
                    imageView.setClickable(true);
                }
            }
        } else { // edit mode off -- read mode on
            Toast.makeText(this, "Read mode on", Toast.LENGTH_SHORT).show();

            isEditModeOn = true;
            tv_edit.setText("Read Mode");
            btn_update.setVisibility(View.GONE);

            for (int i = 0; i < all_view.size(); i++) {
                if (all_view.get(i) instanceof EditText) {
                    EditText editText = (EditText) all_view.get(i);
                    editText.setEnabled(false);
                    editText.setTextColor(getResources().getColor(R.color.darkGray));

                } else if (all_view.get(i) instanceof CircleImageView) {
                    CircleImageView imageView = (CircleImageView) all_view.get(i);
                    imageView.setClickable(false);

                } else if (all_view.get(i) instanceof ImageView) {
                    ImageView imageView = (ImageView) all_view.get(i);
                    imageView.setClickable(false);
                }
            }
        }
    }


    private List<View> getAllChildren(View v) {
        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            //Do not add any parents, just add child elements
            result.addAll(getAllChildren(child));
        }
        return result;
    }

}
