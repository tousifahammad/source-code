package com.app.haircutuser.otp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.app.haircutuser.R;
import com.app.haircutuser.utils.Alert;

public class OtpFragment extends Fragment {
    ImageButton btn_close;
    Button btn_verify;
    EditText et_otp;
    String otp;

    OtpEventListener otpEventListener;

    public OtpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        otpEventListener = null;
    }

    public void closeFragment() {
        getActivity().getFragmentManager().popBackStack();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            otp = getArguments().getString("otp");
            otpEventListener = (OtpEventListener) getActivity();
        } catch (Exception e) {
            throw new ClassCastException(e.getMessage() + " must implement OtpEventListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otp, container, false);

        et_otp = view.findViewById(R.id.et_otp);
        btn_verify = view.findViewById(R.id.btn_verify);
        btn_close = view.findViewById(R.id.btn_close);

        btn_verify.setOnClickListener(v -> {
            String str_otp = et_otp.getText().toString();
            if (str_otp.isEmpty()) {
                Alert.showError(getActivity(), "Please Enter OTP");

            } else if (str_otp.equals(otp)) {
                otpEventListener.isOtpValid(true);
                closeFragment();

            } else {
                Alert.showError(getActivity(), "Please Enter valid OTP");
            }
        });

        btn_close.setOnClickListener(v -> {
            closeFragment();
        });


        return view;
    }


}
