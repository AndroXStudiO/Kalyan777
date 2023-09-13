package in.kal_777.kal_777_777.Activities;

import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.R;

public class PaymentMethodActivity extends AppCompatActivity {

    private IntentFilter mIntentFilter;
    NetBroadCastClass broadCastClass;
    AppBarLayout appBarLayout;
    LinearLayout googlePay, paytm, phonePe,bank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        initIds();
        clickListener();
    }

    private void initIds() {
        googlePay = findViewById(R.id.googlePay);
        paytm = findViewById(R.id.payTmBtn);
        phonePe = findViewById(R.id.phonePe);
        bank = findViewById(R.id.bank);
        appBarLayout = findViewById(R.id.appbarLayout);
        MaterialToolbar mToolbar = appBarLayout.findViewById(R.id.toolbar);
        mToolbar.setTitle("Payment Method");
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        MaterialTextView mDataConText = findViewById(R.id.internet_text);
        broadCastClass = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
    }

    private void clickListener() {

        bank.setOnClickListener(v -> {
            Intent intent = new Intent(this, BankDetailsActivity.class);
            startActivity(intent);
        });
        paytm.setOnClickListener(v -> {
            Intent intent = new Intent(this, UPIActivity.class);
            intent.putExtra("UPI", 1);
            startActivity(intent);
        });
        phonePe.setOnClickListener(v -> {
            Intent intent = new Intent(this, UPIActivity.class);
            intent.putExtra("UPI", 2);
            startActivity(intent);
        });
        googlePay.setOnClickListener(v -> {
            Intent intent = new Intent(this, UPIActivity.class);
            intent.putExtra("UPI",3);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(myReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, mIntentFilter);
    }
}