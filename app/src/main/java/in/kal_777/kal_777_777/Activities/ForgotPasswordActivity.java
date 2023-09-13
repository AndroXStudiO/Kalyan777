package in.kal_777.kal_777_777.Activities;

import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class ForgotPasswordActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextInputEditText editTextPhoneNumber;
    private IntentFilter mIntentFilter;
    NetBroadCastClass broadCastClass;
    private String from= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_screen);
        intVariables();
    }

    private void intVariables() {

        from  = getIntent().getStringExtra("from");
        String number = getIntent().getStringExtra("number");
        mProgressBar = findViewById(R.id.prgrss_bar);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        MaterialTextView mDataConText = findViewById(R.id.internet_text);
        broadCastClass = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);

        if (number !=null){
            editTextPhoneNumber.setText(number);
        }

    }

    public void submit(View view) {
        String number = editTextPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(number)){
            Toast.makeText(this, "Please Enter Number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (number.length()<10){
            Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show();
            return;
        }
        forgotPassword(number);
    }

    private void forgotPassword(String number) {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<LoginModal> call = ApiClient.getClient().forgotPassword(number);
        call.enqueue(new Callback<LoginModal>() {
            @Override
            public void onResponse(Call<LoginModal> call, Response<LoginModal> response) {
                LoginModal modelSignIn = response.body();
                if (response.isSuccessful()) {
                    if (modelSignIn.getStatus().equals("success")) {
                        Intent intent = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
                        intent.putExtra("number",number);
                        intent.putExtra("from", from);
                        startActivity(intent);
                    }
                    Toast.makeText(ForgotPasswordActivity.this, modelSignIn.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ForgotPasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LoginModal> call, Throwable t) {
                System.out.println("forgotPassword "+t);
                Toast.makeText(ForgotPasswordActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, mIntentFilter);
    }
}