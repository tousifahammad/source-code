package com.app.bopufit.aboutus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MyWebView {

    @SuppressLint("SetJavaScriptEnabled")
    public static void setWebView(Context context, WebView mWebview, String web_url) {
        try {
            mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

            mWebview.setWebViewClient(new WebViewClient() {
                @SuppressWarnings("deprecation")
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(context, description, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                    // Redirect to deprecated method, so you can use it in all SDK versions
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                    }
                }
            });

            mWebview.loadUrl(web_url);


        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
