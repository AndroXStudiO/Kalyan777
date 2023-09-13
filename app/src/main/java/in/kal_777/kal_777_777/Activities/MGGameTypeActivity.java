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
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.R;

public class MGGameTypeActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    AppBarLayout appBarLayout;
    private ShapeableImageView sinD, jodD, sinP, douP, triP, haS, fuS;
    private Intent intent;
    private MaterialTextView mDataConText;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mg_game_type_screen);
        intVariables();
        loadData();
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

    }

    private void loadData() {
        String games = getIntent().getStringExtra("game");
        String gameName = getIntent().getStringExtra("gameName");
        Boolean open = getIntent().getBooleanExtra("open", false);
        intent = new Intent(this, MGBiddingActivity.class);
        intent.putExtra("open", open);
        intent.putExtra("games", games);
        if(!open){
            sinD.setVisibility(View.VISIBLE);
            jodD.setVisibility(View.GONE);
            sinP.setVisibility(View.VISIBLE);
            douP.setVisibility(View.VISIBLE);
            triP.setVisibility(View.VISIBLE);
            haS.setVisibility(View.GONE);
            fuS.setVisibility(View.GONE);
        }

        NetBroadCastClass utility = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);

        toolbar.setTitle(gameName);
    }


    private void intVariables() {
        appBarLayout = findViewById(R.id.appbarLayout);
        toolbar = appBarLayout.findViewById(R.id.toolbar);
        sinD = findViewById(R.id.sing_d);
        sinP = findViewById(R.id.sing_p);
        jodD = findViewById(R.id.jodiii_d);
        douP = findViewById(R.id.doub_p);
        haS = findViewById(R.id.halfff_s);
        triP = findViewById(R.id.triii_p);
        fuS = findViewById(R.id.fulllll_s);
        mDataConText = findViewById(R.id.internet_text);
    }

    public void singleDigit(View view) {
        intent.putExtra("game_name", 1);
        startActivity(intent);
    }

    public void jodiDigit(View view) {
        intent.putExtra("game_name", 2);
        startActivity(intent);
    }

    public void singlePana(View view) {
        intent.putExtra("game_name", 3);
        startActivity(intent);
    }

    public void doublePana(View view) {
        intent.putExtra("game_name", 4);
        startActivity(intent);
    }

    public void triplePana(View view) {
        intent.putExtra("game_name", 5);
        startActivity(intent);
    }

    public void halfSangam(View view) {
        intent.putExtra("game_name", 6);
        startActivity(intent);
    }

    public void fullSangam(View view) {
        intent.putExtra("game_name", 7);
        startActivity(intent);
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

}