package in.kal_777.kal_777_777.Activities;

import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Modals.AppDetailsModal;
import in.kal_777.kal_777_777.Modals.LoginModal;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText inNum, inPass;
    private ProgressBar progressBar;
    private MaterialTextView mDataConText;
    private IntentFilter mIntentFilter;
    NetBroadCastClass broadCastClass;
    private String whatsAppNumber ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        intVariables();
        loadData();

    }

    private void loadData() {
        broadCastClass = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
    }

    public void SignInBtn(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        String number = inNum.getText().toString();
        String password = inPass.getText().toString();
        if (TextUtils.isEmpty(number)){
            Snackbar.make(view, "Please Enter Mobile Number",2000).show();
            return;
        }
        if (number.length()!=10){
            Snackbar.make(view, "Please Enter Valid Mobile Number",2000).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Snackbar.make(view, "Please Enter Password",2000).show();
            return;
        }
        if (password.length()<4){
            Snackbar.make(view, "please Enter Min 4 Digits Password",2000).show();
            return;
        }
        if (InternetService.isOnline(this))
        signInMethod(number,password);
        else Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();


    }

    private void AppLiveStatusFun(LoginActivity activity) {
        Call<AppDetailsModal> call = ApiClient.getClient().app_details("");
        call.enqueue(new Callback<AppDetailsModal>() {
            @Override
            public void onResponse(Call<AppDetailsModal> call, Response<AppDetailsModal> response) {
                if (response.isSuccessful()){
                    AppDetailsModal appStatus = response.body();
                    assert appStatus != null;
                    SharedPreferenceData.setContaaaaactUsInfo(activity, SharedPreferenceData.WHATSAP_NUMBER_KEY,"+91"+appStatus.getData().getContact_details().getWhatsapp_no());
                    whatsAppNumber = appStatus.getData().getContact_details().getWhatsapp_no();
                }
            }

            @Override
            public void onFailure(Call<AppDetailsModal> call, Throwable t) {
                System.out.println("App Status "+t);
                Toast.makeText(activity,"Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInMethod(String number, String password) {
        progressBar.setVisibility(View.VISIBLE);
        Call<LoginModal> call = ApiClient.getClient().login(number, password);
        call.enqueue(new Callback<LoginModal>() {
            @Override
            public void onResponse(@NonNull Call<LoginModal> call, @NonNull Response<LoginModal> response) {
                if (response.isSuccessful()){
                    LoginModal loginModal = response.body();
                    assert loginModal != null;
                    if (loginModal.getStatus().equals("success")){
                        SharedPreferenceData.setSigggggninTkn(LoginActivity.this, loginModal.getData().getToken());
                        SharedPreferenceData.setRegisssstttterData(LoginActivity.this,SharedPreferenceData.PHONE_NUMBER_KEY ,number);
                        Intent intent = new Intent(LoginActivity.this, SecurityPinActivity.class);
                        intent.putExtra("number", number);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, loginModal.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(LoginActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<LoginModal> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                System.out.println("Login OnFailure "+t);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(myReceiver, mIntentFilter);
    }
    public void forPass(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        if (!TextUtils.isEmpty(inNum.getText().toString())){
            intent.putExtra("number", inNum.getText().toString());
        }
        intent.putExtra("from", "forgotPass");
        startActivity(intent);
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
    private void intVariables() {
        inNum = findViewById(R.id.phn_num);
        inPass = findViewById(R.id.edt_txt_pass);
        progressBar = findViewById(R.id.prgrss_bar);
        mDataConText = findViewById(R.id.internet_text);
        AppLiveStatusFun(this);
        //inNum.setText(SharedPreferenceData.getDeveloperData(this, SharedPreferenceData.KEY_DEVELOPER_MOBILE));
        //inPass.setText(SharedPreferenceData.getDeveloperData(this, SharedPreferenceData.KEY_DEVELOPER_PASSWORD));
    }

    public void registerClick(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void whatsAppBtn(View view) {
        String url = "https://api.whatsapp.com/send?phone="+ whatsAppNumber;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}