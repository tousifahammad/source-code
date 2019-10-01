package com.app.bopufit.payment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bopufit.R;
import com.app.bopufit.utils.Alert;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    TextView tv_service_name, tv_date, tv_rate, tv_cash, tv_paytm;
    EditText et_promo_code;
    String payment_mode;
    PaymentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // set Toolbar
        updateToolBar();

        initUI();

        presenter = new PaymentPresenter(this);

    }


    private void updateToolBar() {
        ImageView ib_back = findViewById(R.id.ib_back);
        TextView common_title = findViewById(R.id.common_title);
        common_title.setText("Payment");

        ib_back.setVisibility(View.VISIBLE);
        ib_back.setImageResource(R.drawable.arrow_back_white);
        ib_back.setOnClickListener(view -> onBackPressed());
    }


    private void initUI() {
        tv_service_name = findViewById(R.id.tv_service_name);
        tv_date = findViewById(R.id.tv_date);
        tv_rate = findViewById(R.id.tv_rate);
        tv_cash = findViewById(R.id.tv_cash);
        tv_paytm = findViewById(R.id.tv_paytm);
        //et_promo_code = findViewById(R.id.et_promo_code);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void onSelectPaymentClicked(View view) {
        if (view.getId() == R.id.tv_cash) {
            tv_cash.setBackground(ContextCompat.getDrawable(this, R.drawable.round_border_gray));
            tv_paytm.setBackground(ContextCompat.getDrawable(this, R.drawable.border_black));

            payment_mode = "Cash"; // Cash or Paytm

        } else {
            tv_paytm.setBackground(ContextCompat.getDrawable(this, R.drawable.round_border_gray));
            tv_cash.setBackground(ContextCompat.getDrawable(this, R.drawable.border_black));

            payment_mode = "Paytm"; // Cash or Paytm
        }

    }


    public void onContinueClicked(View view) {
        if (payment_mode == null) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        //presenter.requestPaymentService(null);
    }


    //======================== PAYTM =================================
    void navigateScreen(HashMap<String, String> paramMap) {
        //getting paytm service
        // PaytmPGService Service = PaytmPGService.getStagingService();
        //use this when using for production
        PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder(paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true, this);
    }

    @Override
    public void onTransactionResponse(Bundle bundle) {
        System.out.println("Inside onTransactionResponse method:" + bundle.toString());
        //presenter.callPaymentresponseService(bundle.toString(), vsmModel);
        //paymentPresenter.requestPaymentResponse(bundle.toString(), vsmModel);

    }

    @Override
    public void networkNotAvailable() {
        System.out.println("Inside networkNotAvailable method:" + "Network error");
        Alert.showError(this, "Inside networkNotAvailable method:" + "Network error");
    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        System.out.println("Inside clientAuthenticationFailed method:" + inErrorMessage);
        Alert.showError(this, inErrorMessage);
    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        System.out.println("Inside someUIErrorOccurred method:" + inErrorMessage);
        Alert.showError(this, inErrorMessage);
    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        System.out.println("Inside onErrorLoadingWebPage method:" + inErrorMessage);
        Alert.showError(this, inErrorMessage);
    }

    @Override
    public void onBackPressedCancelTransaction() {
        System.out.println("Inside onBackPressedCancelTransaction method:" + "Back Pressed");
        Alert.showError(this, "Transaction Canceled");
    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle bundle) {
        System.out.println("Inside onTransactionCancel method:" + inErrorMessage + "  ,  " + bundle.toString());
        Alert.showError(this, inErrorMessage);
    }

}
