package in.kal_777.kal_777_777.Activities;


import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Modals.CommonModal;
import in.kal_777.kal_777_777.Modals.TransferVerifyModal;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Fragments.MyBSTransferCoins;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferPointsActivity extends AppCompatActivity {

    private TextInputEditText inputCoins, uNumb;
    private TextInputLayout point_lyt;
    private int coins=0, ava_coins =0;
    private InputMethodManager inputMethodManager;
    private MaterialToolbar toolbar;
    private ProgressBar progressBar;
    private MaterialTextView itemCoins;
    private MaterialTextView  userN;
    private MaterialButton subBtn, veBtn;
    private MaterialTextView dataConText;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_point_screen);
        intVariabless();
        loadData();
        ToolbarMethod();
    }

    private void loadData() {
        NetBroadCastClass broadCastClass = new NetBroadCastClass(dataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);

        itemCoins.setVisibility(View.VISIBLE);
        ava_coins = Integer.parseInt(SharedPreferenceData.getCustttttomerCoins(TransferPointsActivity.this));
        itemCoins.setText(String.valueOf(ava_coins));
        veBtn.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
    }
    private void ToolbarMethod() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        uNumb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()<10){
                    veBtn.setEnabled(false);
                    veBtn.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                    point_lyt.setVisibility(View.GONE);
                    subBtn.setVisibility(View.GONE);
                    userN.setVisibility(View.GONE);

                }else {
                    veBtn.setVisibility(View.VISIBLE);
                    veBtn.setEnabled(true);
                    veBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor( TransferPointsActivity.this, R.color.green)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void submitCoins(View view) {
        inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        String s = inputCoins.getText().toString();
        if (s.length()>0){
            coins = Integer.parseInt(s);
        }
        if (TextUtils.isEmpty(s) || coins<1){
            Snackbar.make(view, "Please Enter Points", 2000).show();
            return;
        }
        if (coins<Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_TRANSMIT_COINS_KEY))){
            Snackbar.make(view,"Minimum Points must be greater then "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_TRANSMIT_COINS_KEY), 2000).show();
            return;
        }
        if (coins>Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_TRANSMIT_COINS_KEY))){
            Snackbar.make(view, "Maximum Points must be less then "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_TRANSMIT_COINS_KEY), 2000).show();
            return;
        }
        if (coins> ava_coins){
            Snackbar.make(view, "Insufficient Points", 2000).show();
            return;
        }
        if (InternetService.isOnline(this))
        transferDialog();
        else Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();

    }

    private void transferDialog() {
        MyBSTransferCoins myBSTransferCoins = new MyBSTransferCoins(new MyBSTransferCoins.OnConformClick() {
            @Override
            public void onConformClick() {
                transferCoints(TransferPointsActivity.this);
            }
        });

        myBSTransferCoins.show(getSupportFragmentManager(),"bottomSheet");
        myBSTransferCoins.setCancelable(false);
    }

    private void transferCoints(TransferPointsActivity activity) {
        progressBar.setVisibility(View.VISIBLE);
        Call<CommonModal> call = ApiClient.getClient().transfer_points(SharedPreferenceData.getLogiiiinInToken(activity), inputCoins.getText().toString(), uNumb.getText().toString());
        call.enqueue(new Callback<CommonModal>() {
            @Override
            public void onResponse(Call<CommonModal> call, Response<CommonModal> response) {
                if (response.isSuccessful()){
                    CommonModal commonModal = response.body();
                    if (commonModal.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, commonModal.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (commonModal.getStatus().equalsIgnoreCase("success")){
                        inputCoins.setText("");
                        uNumb.setText("");
                        point_lyt.setVisibility(View.GONE);
                        subBtn.setVisibility(View.GONE);
                        userN.setVisibility(View.GONE);
                        SharedPreferenceData.setUseeeeerCoins(activity,String.valueOf(ava_coins -coins));
                        itemCoins.setText(String.valueOf(ava_coins -coins));
                        veBtn.setVisibility(View.VISIBLE);

                    }
                    Toast.makeText(TransferPointsActivity.this, commonModal.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CommonModal> call, Throwable t) {
                System.out.println("transferPoints Error "+t);
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void verifyMethod(TransferPointsActivity activity) {
        progressBar.setVisibility(View.VISIBLE);
        Call<TransferVerifyModal> call = ApiClient.getClient().transfer_verify(SharedPreferenceData.getLogiiiinInToken(activity), uNumb.getText().toString());
        call.enqueue(new Callback<TransferVerifyModal>() {
            @Override
            public void onResponse(Call<TransferVerifyModal> call, Response<TransferVerifyModal> response) {
                if (response.isSuccessful()){
                    TransferVerifyModal transferVerifyModal = response.body();
                    if (transferVerifyModal.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, transferVerifyModal.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (transferVerifyModal.getStatus().equalsIgnoreCase("success")){
                        point_lyt.setVisibility(View.VISIBLE);
                        subBtn.setVisibility(View.VISIBLE);
                        veBtn.setVisibility(View.GONE);
                        userN.setText(transferVerifyModal.getData().getName());
                        userN.setVisibility(View.VISIBLE);
                        inputCoins.requestFocus();
                    }
                    Toast.makeText(TransferPointsActivity.this, transferVerifyModal.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TransferVerifyModal> call, Throwable t) {
                System.out.println("transferVerify Error "+t);
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
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

    private void intVariabless() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Transfer Points");
        inputCoins = findViewById(R.id.edit_text_points);
        point_lyt = findViewById(R.id.point_lyt);
        progressBar = findViewById(R.id.prgrss_bar);
        uNumb = findViewById(R.id.auser_num);
        veBtn = findViewById(R.id.verify_txt);
        userN = findViewById(R.id.userN);
        subBtn = findViewById(R.id.SbmtBtn);
        dataConText = findViewById(R.id.internet_text);
        itemCoins = findViewById(R.id.toolbarPoints);
    }
    public void btnVerify(View view) {
        inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (InternetService.isOnline(this))
            verifyMethod(TransferPointsActivity.this);
        else Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

}