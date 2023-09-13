package in.kal_777.kal_777_777.Activities;

import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;
    private IntentFilter mIntentFilter;
    NetBroadCastClass broadCastClass;
    MaterialTextView mbtn_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.pb_api);
        MaterialTextView dataConText = findViewById(R.id.mtv_net);
        mbtn_start = findViewById(R.id.mbtn_start);
        method();
        broadCastClass = new NetBroadCastClass(dataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
    }
    private void method() {
        new Handler().postDelayed(() -> {
            if (SharedPreferenceData.getsignInnnnnnSuccess(SplashActivity.this)){
                Intent intent = new Intent(SplashActivity.this, SecurityPinActivity.class);
                intent.putExtra("Reset Pin", 300);
                intent.putExtra("number", SharedPreferenceData.getRegistrrrrrrttationObject(SplashActivity.this, SharedPreferenceData.PHONE_NUMBER_KEY));
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);

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