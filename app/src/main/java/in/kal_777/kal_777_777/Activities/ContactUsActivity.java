package in.kal_777.kal_777_777.Activities;


import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.R;

public class ContactUsActivity extends AppCompatActivity {


    private MaterialToolbar toolbar;
    private IntentFilter mIntentFilter;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_screen);
        intVariables();
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(myReceiver, mIntentFilter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, mIntentFilter);
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    private void intVariables() {
        appBarLayout = findViewById(R.id.appbarLayout);
        toolbar = appBarLayout.findViewById(R.id.toolbar);
        toolbar.setTitle("Contact us");

        MaterialTextView dataConText = findViewById(R.id.internet_text);
        new NetBroadCastClass(dataConText);
    }

}