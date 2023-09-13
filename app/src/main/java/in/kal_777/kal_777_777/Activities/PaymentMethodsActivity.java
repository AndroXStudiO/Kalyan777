package in.kal_777.kal_777_777.Activities;

import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.R;

public class PaymentMethodsActivity extends AppCompatActivity {

    private MaterialToolbar mToolbar;
    AppBarLayout appBarLayout;
    private MaterialTextView mDataConText;
    private IntentFilter mIntentFilter;
    NetBroadCastClass broadCastClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods_screen);
        intVariables();
        confToolbar();
        loadData();

    }

    private void loadData() {
        broadCastClass = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
    }

    private void intVariables() {
        appBarLayout = findViewById(R.id.appbarLayout);
        mToolbar = appBarLayout.findViewById(R.id.toolbar);
        mToolbar.setTitle("Payment Methods");
        mDataConText = findViewById(R.id.internet_text);
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, mIntentFilter);
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


    public void Bank(View view) {
        Intent intent = new Intent(this, BankDetailsActivity.class);
        startActivity(intent);
    }

    public void paytm(View view) {
        Intent intent = new Intent(this, UPIActivity.class);
        intent.putExtra("UPI", 1);
        startActivity(intent);
    }

    public void phonePe(View view) {
        Intent intent = new Intent(this, UPIActivity.class);
        intent.putExtra("UPI", 2);
        startActivity(intent);
    }

    public void gPay(View view) {
        Intent intent = new Intent(this, UPIActivity.class);
        intent.putExtra("UPI",3);
        startActivity(intent);
    }

    private void confToolbar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}