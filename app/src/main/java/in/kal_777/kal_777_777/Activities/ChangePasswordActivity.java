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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Modals.LoginModal;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextInputEditText mINewPass, mIConfPass, input_txt_conf_pin,input_txt_new_pin;
    private ShapeableImageView mBackIcon;
    private ProgressBar mProgressBar;
    private IntentFilter mIntentFilter;
    private String from="";
    private String number = "";
    NetBroadCastClass broadCastClass;
    AppBarLayout appBarLayout;
    LinearLayout passwordLyt, pinLyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);
        getWindow().setBackgroundDrawableResource(R.drawable.ic_bg_gradient) ;
        intVariables();
        loadData();
    }

    private void loadData() {
        if (from.equalsIgnoreCase("home")){
            mBackIcon.setVisibility(View.VISIBLE);
            passwordLyt.setVisibility(View.VISIBLE);
            pinLyt.setVisibility(View.GONE);
        }else if(from.equalsIgnoreCase("securityPin")){
            mBackIcon.setVisibility(View.GONE);
            passwordLyt.setVisibility(View.GONE);
            pinLyt.setVisibility(View.VISIBLE);
        } else if(from.equalsIgnoreCase("forgotPass")){
            mBackIcon.setVisibility(View.GONE);
            passwordLyt.setVisibility(View.VISIBLE);
            pinLyt.setVisibility(View.GONE);
        }
        MaterialTextView mDataConText = findViewById(R.id.internet_text);
        broadCastClass = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
    }

    private void intVariables() {
        appBarLayout = findViewById(R.id.appbarLayout);
        passwordLyt = findViewById(R.id.passwordLyt);
        pinLyt = findViewById(R.id.pinLyt);
        mINewPass = findViewById(R.id.input_txt_new_pass);
        input_txt_new_pin = findViewById(R.id.input_txt_new_pin);
        input_txt_conf_pin = findViewById(R.id.input_txt_conf_pin);
        mBackIcon = findViewById(R.id.back_Icon);
        mIConfPass = findViewById(R.id.input_txt_conf_pass);
        mProgressBar = findViewById(R.id.prgrss_bar);
        from = getIntent().getStringExtra("from");
        number = getIntent().getStringExtra("number");
    }

    public void GoChangePassBtn(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (TextUtils.isEmpty(mINewPass.getText().toString())){
            Snackbar.make(view,"Please enter your new Password", 2000).show();
            return;
        }
        if (mINewPass.getText().toString().length()<4){
            Snackbar.make(view, "New password must be of at least 4 characters length", 2000).show();
            return;
        }
        if (TextUtils.isEmpty(mIConfPass.getText().toString())){
            Snackbar.make(view, "Please enter conform password", 2000).show();
            return;
        }
        if (!mIConfPass.getText().toString().trim().equals(mINewPass.getText().toString().trim())){
            Snackbar.make(view, "Password not matching", 2000).show();
            return;
        }
        if (InternetService.isOnline(this))
        changePassMethod(number, mIConfPass.getText().toString());
        else Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
    }

    private void changePassMethod(String mobile, String inputConformPass) {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<LoginModal> call = ApiClient.getClient().forgot_password_verify(SharedPreferenceData.getLogiiiinInToken(this),mobile,"mobile_token",inputConformPass);
        call.enqueue(new Callback<LoginModal>() {
            @Override
            public void onResponse(Call<LoginModal> call, Response<LoginModal> response) {
                LoginModal modelSignIn = response.body();
                if (response.isSuccessful()) {
                    if (modelSignIn.getStatus().equals("success")) {
                        if (modelSignIn.getCode().equalsIgnoreCase("505")) {
                            SharedPreferenceData.setCllllleaninfo(ChangePasswordActivity.this);
                            Toast.makeText(ChangePasswordActivity.this, modelSignIn.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        if (from.equalsIgnoreCase("home")) {
                            SharedPreferenceData.setSigggggninTkn(ChangePasswordActivity.this, modelSignIn.getData().getToken());
                            onBackPressed();
                        }
                        if (from.equalsIgnoreCase("forgotPass")) {
                            SharedPreferenceData.setSigggggninTkn(ChangePasswordActivity.this, modelSignIn.getData().getToken());
                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                    }
                    Toast.makeText(ChangePasswordActivity.this, modelSignIn.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ChangePasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LoginModal> call, Throwable t) {
                System.out.println("forgotPasswordVerify "+t);
                Toast.makeText(ChangePasswordActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);

            }
        });
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
    public void backBtn(View view) {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, mIntentFilter);
    }

    public void GoChangePinBtn(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (TextUtils.isEmpty(input_txt_new_pin.getText().toString())){
            Snackbar.make(view,"Please enter your new pin", 2000).show();
            return;
        }
        if (input_txt_new_pin.getText().toString().length()<4){
            Snackbar.make(view, "New pin must be 4 characters length", 2000).show();
            return;
        }
        if (TextUtils.isEmpty(input_txt_conf_pin.getText().toString())){
            Snackbar.make(view, "Please enter conform pin", 2000).show();
            return;
        }
        if (!input_txt_conf_pin.getText().toString().trim().equals(input_txt_new_pin.getText().toString().trim())){
            Snackbar.make(view, "Pin not matching", 2000).show();
            return;
        }
        if (InternetService.isOnline(this))
            changePinMethod(number, input_txt_conf_pin.getText().toString());
        else Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();

    }
    public void changePinMethod(String number,String pin ) {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<LoginModal> call = ApiClient.getClient().createPin(SharedPreferenceData.getLogiiiinInToken(this),number,"mobile_token",pin);
        call.enqueue(new Callback<LoginModal>() {
            @Override
            public void onResponse(Call<LoginModal> call, Response<LoginModal> response) {
                LoginModal modelSignIn = response.body();
                if (response.isSuccessful()) {
                    if (modelSignIn.getStatus().equals("success")) {
                        if (modelSignIn.getCode().equalsIgnoreCase("505")) {
                            SharedPreferenceData.setCllllleaninfo(ChangePasswordActivity.this);
                            Toast.makeText(ChangePasswordActivity.this, modelSignIn.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        SharedPreferenceData.setSigggggninTkn(ChangePasswordActivity.this, modelSignIn.getData().getToken());
                        Intent intent = new Intent(ChangePasswordActivity.this, SecurityPinActivity.class);
                        intent.putExtra("number", number);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    Toast.makeText(ChangePasswordActivity.this, modelSignIn.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ChangePasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LoginModal> call, Throwable t) {
                System.out.println("forgotPasswordVerify "+t);
                Toast.makeText(ChangePasswordActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);

            }
        });
    }

    public void whatsAppBtn(View view) {
        String url = "https://api.whatsapp.com/send?phone="+ SharedPreferenceData.getContaaaaaactObject(this, SharedPreferenceData.WHATSAP_NUMBER_KEY);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}