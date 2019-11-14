package com.app.haircutuser.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.app.haircutuser.R;
import com.app.haircutuser.otp.OtpEventListener;
import com.app.haircutuser.registration.RegistrationActivity;
import com.app.haircutuser.utils.IntentController;

public class LoginActivity extends AppCompatActivity implements OtpEventListener {
    EditText et_phn_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        openOtpFragment(this, R.id.ConstrainLayout_root, otp);

    }
	
	    public static void openOtpFragment(Activity activity, int container_view_id, String otp) {
        try {
            if (!otp.isEmpty()) {
                Bundle bundle = new Bundle();
                bundle.putString("otp", otp);
                //bundle.putStringArrayList("code_list", biler_code_list);

                Fragment fragment = new OtpFragment();
                fragment.setArguments(bundle);

                FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
                transaction.replace(container_view_id, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            }
        } catch (Exception e) {
            Alert.showError(activity, e.getMessage());
        }
    }




    @Override
    public void isOtpValid(boolean is_valid) {
        if (is_valid) {
            presenter.login();
        }
    }
}
