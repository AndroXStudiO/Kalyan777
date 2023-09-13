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
import in.kal_777.kal_777_777.Modals.CommonModal;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText mInName, mIMobNum, mInPass, mInPC;
    private ProgressBar progressBar;
    private MaterialTextView dataConText;
    private IntentFilter mIntentFilter;
    NetBroadCastClass broadCastClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        intVariables();
        loadData();
    }

    private void loadData() {
        broadCastClass = new NetBroadCastClass(dataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
    }

    private void intVariables() {
        mInName = findViewById(R.id.client_name);
        mIMobNum = findViewById(R.id.phn_num);
        mInPass = findViewById(R.id.edt_txt_pass);
        mInPC = findViewById(R.id.edit_pin);
        progressBar = findViewById(R.id.prgrss_bar);
        dataConText = findViewById(R.id.internet_text);
    }

    public void signUp(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (TextUtils.isEmpty(mInName.getText().toString())){
            Snackbar.make(view, "Please Enter Your Name",2000).show();
            return;
        }
        if (TextUtils.isEmpty(mIMobNum.getText().toString())){
            Snackbar.make(view, "Please Enter Mobile Number",2000).show();
            return;
        }
        if (mIMobNum.getText().toString().length()<10){
            Snackbar.make(view, "Please Enter Valid Mobile Number)",2000).show();
            return;
        }
        if (TextUtils.isEmpty(mInPass.getText().toString())){
            Snackbar.make(view, "Please Enter Password",2000).show();
            return;
        }
        if (mInPass.getText().toString().length()<4){
            Snackbar.make(view, "Please Enter Min 4 Digits Password",2000).show();
            return;
        }
        if (TextUtils.isEmpty(mInPC.getText().toString())){
            Snackbar.make(view, "Please Enter Pin)",2000).show();
            return;
        }
        if (mInPC.getText().toString().length()<4){
            Snackbar.make(view, "Please Enter Min 4 Digit Pin",2000).show();
            return;
        }
        if (InternetService.isOnline(this))
        SignUpMethod(mInName.getText().toString().trim(), mIMobNum.getText().toString().trim(), mInPass.getText().toString().trim(), mInPC.getText().toString().trim());
        else Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
    }



    private void SignUpMethod(String personName, String mobileNumber, String password, String pinCode) {
        progressBar.setVisibility(View.VISIBLE);
        Call<CommonModal> call = ApiClient.getClient().signup(personName,mobileNumber,pinCode,password);
        call.enqueue(new Callback<CommonModal>() {
            @Override
            public void onResponse(Call<CommonModal> call, Response<CommonModal> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    CommonModal commonModal = response.body();
                    assert commonModal != null;
                    if (commonModal.getStatus().equals("success")){
                        getAppInfoMethod(SignUpActivity.this);
                        SharedPreferenceData.setRegisssstttterData(SignUpActivity.this, SharedPreferenceData.USER_NAME_KEY,personName);
                        SharedPreferenceData.setRegisssstttterData(SignUpActivity.this, SharedPreferenceData.PHONE_NUMBER_KEY,mobileNumber);
                        Intent intent = new Intent(SignUpActivity.this, OtpActivity.class);
                        intent.putExtra("number", mobileNumber);
                        intent.putExtra("from", "signup");
                        startActivity(intent);
                    }else Toast.makeText(SignUpActivity.this, commonModal.getMessage(), Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(SignUpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CommonModal> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                System.out.println("getSignUp OnFailure "+t);
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

    public void AlreadyRegistered(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private void getAppInfoMethod(SignUpActivity activity) {
        progressBar.setVisibility(View.VISIBLE);
        Call<AppDetailsModal> call = ApiClient.getClient().app_details("");
        call.enqueue(new Callback<AppDetailsModal>() {
            @Override
            public void onResponse(@NonNull Call<AppDetailsModal> call, @NonNull Response<AppDetailsModal> response) {
                if (response.isSuccessful()) {
                    AppDetailsModal appDetailsModal = response.body();
                    if (appDetailsModal.getCode().equalsIgnoreCase("505")) {
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, appDetailsModal.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (appDetailsModal.getStatus().equalsIgnoreCase("success")) {
                        SharedPreferenceData.setPrefrenccccrrrrreStrngData(activity, SharedPreferenceData.MAR_TXT_KEY, appDetailsModal.getData().getBanner_marquee());
                        SharedPreferenceData.setContaaaaactUsInfo(activity, SharedPreferenceData.PHONE_NUMBER1_KEY, "+91" + appDetailsModal.getData().getContact_details().getMobile_no_1());
                        SharedPreferenceData.setContaaaaactUsInfo(activity, SharedPreferenceData.PHONE_NUMBER2_KEY, "+91" + appDetailsModal.getData().getContact_details().getMobile_no_2());
                        SharedPreferenceData.setContaaaaactUsInfo(activity, SharedPreferenceData.WHATSAP_NUMBER_KEY, "+91" + appDetailsModal.getData().getContact_details().getWhatsapp_no());
                        SharedPreferenceData.setContaaaaactUsInfo(activity, SharedPreferenceData.REACH_US_EMAIL_KEY, appDetailsModal.getData().getContact_details().getEmail_1());
                        SharedPreferenceData.setContaaaaactUsInfo(activity, SharedPreferenceData.TELEGRAM_KEY, appDetailsModal.getData().getContact_details().getTelegram_no());
                        SharedPreferenceData.setPosteeeeeerrImages(activity, SharedPreferenceData.POSTER_IMAGES1_KEY, appDetailsModal.getData().getBanner_image().getBanner_img_1());
                        SharedPreferenceData.setPosteeeeeerrImages(activity, SharedPreferenceData.POSTER_IMAGES2_KEY, appDetailsModal.getData().getBanner_image().getBanner_img_2());
                        SharedPreferenceData.setPosteeeeeerrImages(activity, SharedPreferenceData.POSTER_IMAGES3_KEY, appDetailsModal.getData().getBanner_image().getBanner_img_3());
                    } else
                        Toast.makeText(activity, appDetailsModal.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<AppDetailsModal> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                System.out.println("getAppDetails error " + t);
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();

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