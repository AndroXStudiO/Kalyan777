package in.kal_777.kal_777_777.Activities;

import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Modals.CommonModal;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UPIActivity extends AppCompatActivity {

    private TextInputEditText setTextUPI;
    private MaterialToolbar toolbar;
    private int anInt = 0;
    private Call<CommonModal> call;
    private ProgressBar progressBar;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi_screen);
        intVariables();
        loadData();
        ToolbarMethod();
    }

    private void loadData() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
        anInt = getIntent().getIntExtra("UPI",0);
        switch (anInt){
            case 1:
                setTextUPI.setHint("Paytm Number");
                setTextUPI.setText(SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.P_UPI_ID_KEY));
                toolbar.setTitle("Paytm");
                break;
            case 2:
                setTextUPI.setHint("PhonePe Number");
                setTextUPI.setText(SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.PP_UPI_ID_KEY));
                toolbar.setTitle("PhonePe");
                break;
            case 3:
                setTextUPI.setHint("Google Pay Number");
                setTextUPI.setText(SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.G_PAY_UPI_ID_KEY));
                toolbar.setTitle("Google Pay");
                break;
        }
    }

    private void intVariables() {
        setTextUPI = findViewById(R.id.in_txt_upi);
        AppBarLayout appBarLayout = findViewById(R.id.appbarLayout);
        toolbar = appBarLayout.findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.prgrss_bar);

        MaterialTextView dataConText = findViewById(R.id.internet_text);
        NetBroadCastClass broadCastClass = new NetBroadCastClass(dataConText);
    }

    public void sUp(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (TextUtils.isEmpty(setTextUPI.getText().toString())){
            Snackbar.make(view, "Enter Valid Number", 2000).show();
            return;
        }
        if (setTextUPI.getText().toString().length()<10){
            Snackbar.make(view, "Enter Valid Number", 2000).show();
            return;
        }
        if (InternetService.isOnline(this))
        UpiUpMethod();
        else Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();

    }

    private void UpiUpMethod() {
        progressBar.setVisibility(View.VISIBLE);
        switch (anInt){
            case 1:
                call = ApiClient.getClient().update_paytm(SharedPreferenceData.getLogiiiinInToken(this), setTextUPI.getText().toString());
                break;
            case 2:
                call = ApiClient.getClient().update_phonepe(SharedPreferenceData.getLogiiiinInToken(this), setTextUPI.getText().toString());
                break;
            case 3:
                call = ApiClient.getClient().update_gpay(SharedPreferenceData.getLogiiiinInToken(this), setTextUPI.getText().toString());
                break;
        }
        call.enqueue(new Callback<CommonModal>() {
            @Override
            public void onResponse(@NonNull Call<CommonModal> call, @NonNull Response<CommonModal> response) {
                if (response.isSuccessful()){
                    CommonModal modelMain = response.body();

                    if (modelMain.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(UPIActivity.this);
                        Toast.makeText(UPIActivity.this, modelMain.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UPIActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (modelMain.getStatus().equalsIgnoreCase("success")){
                        progressBar.setVisibility(View.GONE);
                        switch (anInt){
                            case 1:
                                SharedPreferenceData.setPrefrenccccrrrrreStrngData(UPIActivity.this, SharedPreferenceData.P_UPI_ID_KEY, setTextUPI.getText().toString());
                                break;
                            case 2:
                                SharedPreferenceData.setPrefrenccccrrrrreStrngData(UPIActivity.this, SharedPreferenceData.PP_UPI_ID_KEY, setTextUPI.getText().toString());
                                break;
                            case 3:
                                SharedPreferenceData.setPrefrenccccrrrrreStrngData(UPIActivity.this, SharedPreferenceData.G_PAY_UPI_ID_KEY, setTextUPI.getText().toString());
                                break;
                        }
                        onBackPressed();
                    }
                    Toast.makeText(UPIActivity.this, modelMain.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UPIActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<CommonModal> call, @NonNull Throwable t) {
                System.out.println("UPI Update Error "+t);
                Toast.makeText(UPIActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void ToolbarMethod() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }
}