package in.kal_777.kal_777_777.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Modals.CommonModal;
import in.kal_777.kal_777_777.Modals.LoginModal;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {

    TextView resendOtp;
    PinView otp;
    private static final long RESEND_OTP_INTERVAL = 30000; // 30 seconds
    private static final long COUNTDOWN_INTERVAL = 1000; // 1 second

    private CountDownTimer resendOtpTimer;
    private boolean isTimerRunning = false;
    private ProgressBar progressBar;
    private  String number;
    private  String from="";
    TextView textViewTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        initIds();
    }

    private void initIds() {
        resendOtp = findViewById(R.id.textViewResend);
        otp = findViewById(R.id.otp);
        progressBar = findViewById(R.id.prgrss_bar);
        textViewTitle = findViewById(R.id.textViewTitle);
        number = getIntent().getStringExtra("number");
        from = getIntent().getStringExtra("from");

        textViewTitle.setText("Please type verification code sent to\n"+number);
    }

    public void submit(View view) {
        String otps = otp.getText().toString();
        if (TextUtils.isEmpty(otps)){
            Toast.makeText(this, "Enter Otp", Toast.LENGTH_SHORT).show();
            return;
        }
        if (otps.length()<4){
            Toast.makeText(this, "Invalid Otp", Toast.LENGTH_SHORT).show();
            return;
        }
        if (from.equalsIgnoreCase("signup")){
            verifyUserMethod(number, otps);
        }else {
            submitOtp(otps);
        }
    }

    private void submitOtp(String otps) {
        progressBar.setVisibility(View.VISIBLE);
        Call<LoginModal> call = ApiClient.getClient().verifyOtp(number, otps);
        call.enqueue(new Callback<LoginModal>() {
            @Override
            public void onResponse(@NonNull Call<LoginModal> call, @NonNull Response<LoginModal> response) {
                if (response.isSuccessful()){
                    LoginModal loginModal = response.body();
                    assert loginModal != null;
                    if (loginModal.getStatus().equals("success")){
                        SharedPreferenceData.setSigggggninTkn(OtpActivity.this, loginModal.getData().getToken());
                        Intent intent = new Intent(OtpActivity.this, ChangePasswordActivity.class);
                        intent.putExtra("number", number);
                        intent.putExtra("from", from);
                        startActivity(intent);
                    }else{
                        Toast.makeText(OtpActivity.this, loginModal.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(OtpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<LoginModal> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                System.out.println("VerifyOtp OnFailure "+t);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void verifyUserMethod(String mobileNumber, String otps) {
        progressBar.setVisibility(View.VISIBLE);
        Call<LoginModal> call = ApiClient.getClient().verify_user(mobileNumber, "firebase_token",otps );
        call.enqueue(new Callback<LoginModal>() {
            @Override
            public void onResponse(Call<LoginModal> call, Response<LoginModal> response) {
                if (response.isSuccessful()) {
                    LoginModal loginModal = response.body();
                    if (loginModal.getStatus().equals("success")) {
                        SharedPreferenceData.setSigggggninTkn(OtpActivity.this, loginModal.getData().getToken());
                        SharedPreferenceData.setSigniiiiinnnSuccess(OtpActivity.this, true);
                        Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    Toast.makeText(OtpActivity.this, loginModal.getMessage(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(OtpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LoginModal> call, Throwable t) {
                System.out.println("verifyUser " + t);
                Toast.makeText(OtpActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void resendOtp(View view) {
        resendOtpApi();
    }

    private void resendOtpApi() {
        progressBar.setVisibility(View.VISIBLE);
        Call<CommonModal> call = ApiClient.getClient().resendOtp(number);
        call.enqueue(new Callback<CommonModal>() {
            @Override
            public void onResponse(@NonNull Call<CommonModal> call, @NonNull Response<CommonModal> response) {
                if (response.isSuccessful()){
                    CommonModal commonModal = response.body();
                    assert commonModal != null;
                    startResendOtpTimer();
                    Toast.makeText(OtpActivity.this, commonModal.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(OtpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<CommonModal> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                System.out.println("Login OnFailure "+t);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void startResendOtpTimer() {
        if (!isTimerRunning) {
            resendOtpTimer = new CountDownTimer(RESEND_OTP_INTERVAL, COUNTDOWN_INTERVAL) {
                @Override
                public void onTick(long millisUntilFinished) {
                    isTimerRunning = true;
                    long secondsRemaining = millisUntilFinished / COUNTDOWN_INTERVAL;
                    resendOtp.setText("Resend Otp in "+String.valueOf(secondsRemaining)+" Sec");
                }
                @Override
                public void onFinish() {
                    isTimerRunning = false;
                    resendOtp.setText("Resend Otp");
                }
            };

            resendOtpTimer.start();
        }
    }

    public void whatsAppBtn(View view) {
        String url = "https://api.whatsapp.com/send?phone="+ SharedPreferenceData.getContaaaaaactObject(this, SharedPreferenceData.WHATSAP_NUMBER_KEY);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resendOtpTimer != null) {
            resendOtpTimer.cancel();
        }
    }
}