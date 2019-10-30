package com.app.guardian.contacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.guardian.R;
import com.app.guardian.contacts.view.Contact;
import com.app.guardian.contacts.view.ContactsAdapter;
import com.app.guardian.utils.IntentController;
import com.app.guardian.utils.SettingSharedPreferences;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity implements ContactsAdapter.ClickListener {
    LinearLayout ll_contact_main;
    RecyclerView rv_contacts;
    ArrayList<Contact> contact_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        // set Toolbar
        setToolBar();

        initUI();

        String response = new SettingSharedPreferences(this).getEMERGENCY_CONTACT_LIST();
        if (response != null) {
            new ContactsPresenter(this).responseContacts(response);
        }
    }

    private void setToolBar() {
        AppCompatTextView tv_tooltitle = findViewById(R.id.tv_app_name);
        tv_tooltitle.setVisibility(View.VISIBLE);
        tv_tooltitle.setText(getString(R.string.title_contact));
        findViewById(R.id.ib_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void initUI() {
        ll_contact_main = findViewById(R.id.ll_contact_main);
        rv_contacts = findViewById(R.id.rv_contacts);

        setRecyclerView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_contacts.setLayoutManager(mLayoutManager);
    }

    public void setAdapter() {
        ContactsAdapter mAdapter = new ContactsAdapter(contact_list);
        rv_contacts.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
    }


    public void onAddContactClick(View view) {
        IntentController.sendIntent(ContactsActivity.this, AddContactActivity.class);
    }

    @Override
    public void itemClicked(View view, int position) {
        if (view.getId() == R.id.iv_edit) {
            //clickListener.itemClicked(view, getLayoutPosition());
            Toast.makeText(view.getContext(), "iv_edit", Toast.LENGTH_SHORT).show();
        }
//        } else if (view.getId() == R.id.tv_validate) {
//            Toast.makeText(view.getContext(), "tv_validate", Toast.LENGTH_SHORT).show();
//
//        }
    }
}
