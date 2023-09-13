package in.kal_777.kal_777_777.Activities;


import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.R;

public class SLGameTypeActivity extends AppCompatActivity {

    private Intent intent;
    private IntentFilter mIntentFilter;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sl_game_type_screen);
        intVariables();
        loadData();
    }

    private void loadData() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void intVariables() {
        MaterialTextView dataConText = findViewById(R.id.internet_text);
        toolbar = findViewById(R.id.toolbar);
        new NetBroadCastClass(dataConText);
        String games = getIntent().getStringExtra("Game");
        intent = new Intent(this, SLBiddingActivity.class);
        intent.putExtra("games", games);
        toolbar.setTitle(games);
    }

    public void singleDigit(View view) {
        intent.putExtra("game_name", 8);
        startActivity(intent);
    }

    public void singlePana(View view) {
        intent.putExtra("game_name", 9);
        startActivity(intent);
    }

    public void doublePana(View view) {
        intent.putExtra("game_name", 10);
        startActivity(intent);
    }

    public void triplePana(View view) {
        intent.putExtra("game_name", 11);
        startActivity(intent);
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