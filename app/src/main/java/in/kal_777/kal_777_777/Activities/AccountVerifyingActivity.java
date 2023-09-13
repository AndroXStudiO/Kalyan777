package in.kal_777.kal_777_777.Activities;


import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.R;

public class AccountVerifyingActivity extends AppCompatActivity {

    private String mobileNumber;
    private ProgressBar progressBar;
    private IntentFilter mIntentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_verifying_screen);
        intVariables();
        loadData();
    }

    private void loadData() {
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
    //    verifyUserMethod(mobileNumber);
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
    private void intVariables() {
        progressBar = findViewById(R.id.prgrss_bar);
        mobileNumber = getIntent().getStringExtra("Mobile Number");
        MaterialTextView dataConText = findViewById(R.id.internet_text);
        NetBroadCastClass broadCastClass = new NetBroadCastClass(dataConText);
        mIntentFilter = new IntentFilter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, mIntentFilter);
    }
}