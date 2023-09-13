package in.kal_777.kal_777_777.Activities;


import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

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

public class SecurityPinActivity extends AppCompatActivity {

    private AppCompatCheckBox cB1, cB2, cB3, cB4;
    private LinearLayout btnCr, cBLay;
    private Animation mShake;
    private ProgressBar progressBar;
    private MaterialTextView txtCr;
    private Vibrator mVibrator;
    private MaterialTextView mDataConText;
    private IntentFilter mIntentFilter;
    String number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_pin_screen);
        intVariables();
        loadData();
    }

    private void loadData() {
        NetBroadCastClass broadCastClass = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
        getAppInfoMethod(this);
    }

    private void intVariables() {
        progressBar = findViewById(R.id.prgrss_bar);
        cB3 = findViewById(R.id.check_box_3);
        cB4 = findViewById(R.id.check_box_4);
        cB1 = findViewById(R.id.check_box_1);
        cB2 = findViewById(R.id.check_box_2);
        btnCr = findViewById(R.id.btn_clear);
        txtCr = findViewById(R.id.text_clear);
        cBLay = findViewById(R.id.check_box_Lyt);
        mDataConText = findViewById(R.id.internet_text);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
        number = getIntent().getStringExtra("number");

    }


    public void button1(View view) {
        checkBoxFun("1");
    }

    public void button2(View view) {
        checkBoxFun("2");
    }

    public void button3(View view) {
        checkBoxFun("3");
    }

    public void button4(View view) {
        checkBoxFun("4");
    }

    public void button5(View view) {
        checkBoxFun("5");
    }

    public void button6(View view) {
        checkBoxFun("6");
    }

    public void button7(View view) {
        checkBoxFun("7");
    }

    public void button8(View view) {
        checkBoxFun("8");
    }

    public void button9(View view) {
        checkBoxFun("9");
    }

    public void button0(View view) {
        checkBoxFun("0");
    }

    public void btnDlt(View view) {
        if (cB1.isChecked()&& cB2.isChecked()&& cB3.isChecked()&& cB4.isChecked()){
            cB4.setChecked(false);
        }else if (cB1.isChecked() && cB2.isChecked()&& cB3.isChecked()&& !cB4.isChecked()){
            cB3.setChecked(false);
        }else if (cB1.isChecked() && cB2.isChecked() && !cB3.isChecked()&& !cB4.isChecked()){
            cB2.setChecked(false);
        }else if (cB1.isChecked() && !cB2.isChecked()&&!cB3.isChecked()&& !cB4.isChecked()){
            cB1.setChecked(false);
            btnCr.setVisibility(View.INVISIBLE);
            txtCr.setVisibility(View.INVISIBLE);
        }
    }

    public void btnClr(View view) {
        cB1.setChecked(false);
        cB2.setChecked(false);
        cB3.setChecked(false);
        cB4.setChecked(false);
        btnCr.setVisibility(View.INVISIBLE);
        txtCr.setVisibility(View.INVISIBLE);
    }

    String pin1, pin2, pin3,pin4;
    public void checkBoxFun(String pin){
        if (!cB1.isChecked()&& !cB2.isChecked()&& !cB3.isChecked()&& !cB4.isChecked()){
            cB1.setChecked(true);
            btnCr.setVisibility(View.VISIBLE);
            txtCr.setVisibility(View.VISIBLE);
            pin1 = pin;
        }else if (cB1.isChecked() && !cB2.isChecked()&& !cB3.isChecked()&& !cB4.isChecked()){
            cB2.setChecked(true);
            pin2 = pin;
        }else if (cB1.isChecked() && cB2.isChecked() && !cB3.isChecked()&& !cB4.isChecked()){
            cB3.setChecked(true);
            pin3 = pin;
        }else if (cB1.isChecked() && cB2.isChecked()&& cB3.isChecked()&& !cB4.isChecked()){
            cB4.setChecked(true);
            pin4 = pin;
            sPinVerifyMethod(SecurityPinActivity.this,pin1 + pin2 + pin3 + pin4);
        }
    }

    private void sPinVerifyMethod(SecurityPinActivity activity, String pin) {
        progressBar.setVisibility(View.VISIBLE);
        Call<LoginModal> call = ApiClient.getClient().login_pin(SharedPreferenceData.getLogiiiinInToken(activity), pin);
        call.enqueue(new Callback<LoginModal>() {
            @Override
            public void onResponse(@NonNull Call<LoginModal> call, @NonNull Response<LoginModal> response) {
                if (response.isSuccessful()){
                    LoginModal modelSignIn = response.body();
                    if (modelSignIn.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, modelSignIn.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (modelSignIn.getStatus().equals("success")){
                       /* if (devNumber.equalsIgnoreCase(SharedPreferenceData.getDeveloperData(activity, SharedPreferenceData.KEY_DEVELOPER_MOBILE))){
                            Intent intent = new Intent(activity, ExclusiveActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else {

                        }*/
                        SharedPreferenceData.setSigggggninTkn(activity, modelSignIn.getData().getToken());
                        SharedPreferenceData.setSigniiiiinnnSuccess(activity, true);
                        Intent intent = new Intent(activity, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    }else {
                        cB1.setChecked(false);
                        cB2.setChecked(false);
                        cB3.setChecked(false);
                        cB4.setChecked(false);
                        mVibrator.vibrate(500);
                        mShake = AnimationUtils.loadAnimation(activity, R.anim.ic_shake);
                        cBLay.startAnimation(mShake);
                    }
                    Toast.makeText(activity, modelSignIn.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginModal> call, @NonNull Throwable t) {
                System.out.println("security pin Error "+t);
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
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    public void resetPin(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        intent.putExtra("from", "securityPin");
        intent.putExtra("number", number);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, mIntentFilter);
    }

    private void getAppInfoMethod(SecurityPinActivity activity) {
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
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }

}