package in.kal_777.kal_777_777.Activities;

import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;

public class WalletActivity extends AppCompatActivity {


    public MaterialTextView coins, mMinWithdCoins, mWithdOT, mWithdCT;
    private MaterialTextView transBtn, addCoins,withDBtn;
    private Vibrator mVibe;
    private MaterialTextView mDataConText;
    private IntentFilter mIntentFilter;
    private AppBarLayout appBarLayout;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_screen);
        intVariables();
        laodData();
        clickListeners();
        NetBroadCastClass broadCastClass = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
    }


    private void laodData() {
        coins.setText(SharedPreferenceData.getCustttttomerCoins(this));
        mMinWithdCoins.setText("Minimum withdrawal point ares - "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_EXTRACT_COINS_KEY));
        mVibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
    }

    private void clickListeners() {
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
        transBtn.setOnClickListener(v -> {
            if (SharedPreferenceData.getTransssssmitCoins(this)){
                Intent transferCoins = new Intent(this, TransferPointsActivity.class);
                startActivity(transferCoins);
            }else {
                Toast.makeText(this, "Transfer is not enable in your account", Toast.LENGTH_SHORT).show();
            }
            mVibe.vibrate(100);
        });
        addCoins.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddCoinActivity.class);
            startActivity(intent);
        });
        withDBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, TakeOutActivity.class);
            startActivity(intent);
        });

    }


    private void intVariables() {

        coins = findViewById(R.id.menuChips);
        mMinWithdCoins = findViewById(R.id.azminWithdCoins);
        mWithdOT = findViewById(R.id.awithd_ot);
        mWithdCT = findViewById(R.id.qawithd_ct);
        transBtn = findViewById(R.id.atransBtn);
        addCoins = findViewById(R.id.aaddCoins);
        withDBtn = findViewById(R.id.awithDBtn);
        mDataConText = findViewById(R.id.internet_text);
        appBarLayout = findViewById(R.id.appbarLayout);
        toolbar = appBarLayout.findViewById(R.id.toolbar);
        toolbar.setTitle("Wallet");
    }

    public void googlepayUpi(View view) {
        Intent intent = new Intent(this, UPIActivity.class);
        intent.putExtra("UPI",3);
        startActivity(intent);
    }

    public void paytm(View view) {
        Intent intent = new Intent(this, UPIActivity.class);
        intent.putExtra("UPI", 1);
        startActivity(intent);
    }

    public void bank(View view) {
        Intent intent = new Intent(this, BankDetailsActivity.class);
        startActivity(intent);
    }

    public void phonePe(View view) {
        Intent intent = new Intent(this, UPIActivity.class);
        intent.putExtra("UPI", 2);
        startActivity(intent);
    }
}