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

public class GDGameTypeActivity extends AppCompatActivity {

    private Intent intent;
    private IntentFilter aIntentFilter;
    private MaterialToolbar aToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd_game_type_screen);
        intVariables();
        loadData();
    }
    private void intVariables() {
        MaterialTextView dataConText = findViewById(R.id.internet_text);
        aToolbar = findViewById(R.id.toolbar);

        NetBroadCastClass broadcastClass = new NetBroadCastClass(dataConText);
        String games = getIntent().getStringExtra("game");
        intent = new Intent(this, GDBiddingActivity.class);
        intent.putExtra("games", games);
        aToolbar.setTitle(games);
    }
    private void loadData() {
        aIntentFilter = new IntentFilter();
        aIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
        aToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void leftDigit(View view) {
        intent.putExtra("game_name", 12);
        startActivity(intent);
    }

    public void jodiDigit(View view) {
        intent.putExtra("game_name", 14);
        startActivity(intent);
    }
    public void rightDigit(View view) {
        intent.putExtra("game_name", 13);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(myReceiver, aIntentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, aIntentFilter);
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

}