package com.app.bopufit.aboutus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.app.bopufit.R;
import com.app.bopufit.aboutus.MyWebView;

public class PrivacyFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;


    public PrivacyFragment() {
        // Required empty public constructor
    }

    public static PrivacyFragment newInstance(String param1/*, String param2*/) {
        PrivacyFragment fragment = new PrivacyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //   args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_webview, container, false);

        String web_url = "https://www.bopufit.com/Web/privacy_policy";
        WebView mWebview = view.findViewById(R.id.mWebview);

        MyWebView.setWebView(getContext(), mWebview, web_url);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
