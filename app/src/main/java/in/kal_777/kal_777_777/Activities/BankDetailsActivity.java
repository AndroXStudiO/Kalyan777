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

public class BankDetailsActivity extends AppCompatActivity {

    private MaterialToolbar mToolbar;
    AppBarLayout appBarLayout;
    private TextInputEditText mName, mAcNumber, mConfAcNumber, mIfscCode, mBankName, mBankAddress;
    private ProgressBar mProgressBar;
    private MaterialTextView mDataConText;
    private IntentFilter mIntentFilter;
    NetBroadCastClass broadCastClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details_screen);
        intVariables();
        confiToolbar();
        confiData();
    }

    private void confiData() {

        if (SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.BANK_USER_NAME_KEY)!=null)
            mName.setText(SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.BANK_USER_NAME_KEY));else mName.setText("");
        if (SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.B_AC_N_KEY)!=null) mAcNumber.setText(SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.B_AC_N_KEY));else mAcNumber.setText("");
        if (SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.B_AC_N_KEY)!=null)
            mConfAcNumber.setText(SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.B_AC_N_KEY));else mConfAcNumber.setText("");
        if (SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.B_IFSC_C_KEY)!=null) mIfscCode.setText(SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.B_IFSC_C_KEY));else mIfscCode.setText("");
        if (SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.B_N_KEY)!=null) mBankName.setText(SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.B_N_KEY));else mBankName.setText("");
        if (SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.BRANCH_ADDRESS_KEY)!=null)
            mBankAddress.setText(SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.BRANCH_ADDRESS_KEY));else mBankAddress.setText("");

        broadCastClass = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
    }


    private void confiToolbar() {
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void submitInfo(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (TextUtils.isEmpty(mName.getText().toString())){
            Snackbar.make(view, "Please Enter Your Bank Account Holder Name", 2000).show();
            return;
        }
        if (TextUtils.isEmpty(mAcNumber.getText().toString())){
            Snackbar.make(view, "Please Enter Your Bank Account Number", 2000).show();
            return;
        }
        if (mAcNumber.getText().toString().length()<5){
            Snackbar.make(view, "Please Enter Valid Bank Account Number", 2000).show();
            return;
        }
        if (TextUtils.isEmpty(mConfAcNumber.getText().toString())){
            Snackbar.make(view, "Please Enter Your Conform Bank Account Number", 2000).show();
            return;
        }
        if (!mAcNumber.getText().toString().equals(mConfAcNumber.getText().toString())){
            Snackbar.make(view, "Account Number Not Matching", 2000).show();
            return;
        }
        if (TextUtils.isEmpty(mIfscCode.getText().toString())){
            Snackbar.make(view, "Please Enter Your Bank IFSC Code", 2000).show();
            return;
        }
        if (mIfscCode.getText().toString().length()<11){
            Snackbar.make(view, "Please Enter Valid IFSC Code of Your Bank", 2000).show();
            return;
        }
        if (TextUtils.isEmpty(mBankName.getText().toString())){
            Snackbar.make(view, "Please Enter Your Bank Name", 2000).show();
            return;
        }
        if (TextUtils.isEmpty(mBankAddress.getText().toString())){
            Snackbar.make(view, "Please Enter Your Branch Address", 2000).show();
            return;
        }

        if (InternetService.isOnline(this))
        bankInfoMethod(BankDetailsActivity.this, mName.getText().toString(), mAcNumber.getText().toString(), mIfscCode.getText().toString(), mBankName.getText().toString(), mBankAddress.getText().toString());
        else Toast.makeText(this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
    }
    private void bankInfoMethod(BankDetailsActivity activity, String holderName, String accountNumber, String ifscCode, String bankName, String bankAddress) {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<CommonModal> call = ApiClient.getClient().update_bank_details(SharedPreferenceData.getLogiiiinInToken(this), holderName,accountNumber,ifscCode,bankName,bankAddress);
        call.enqueue(new Callback<CommonModal>() {
            @Override
            public void onResponse(Call<CommonModal> call, Response<CommonModal> response) {
                if (response.isSuccessful()){
                    CommonModal modelMain = response.body();
                    if (modelMain.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, modelMain.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (modelMain.getStatus().equalsIgnoreCase("success")){
                        SharedPreferenceData.setBankInnnttformation(activity, SharedPreferenceData.BANK_USER_NAME_KEY, holderName);
                        SharedPreferenceData.setBankInnnttformation(activity, SharedPreferenceData.B_AC_N_KEY, accountNumber);
                        SharedPreferenceData.setBankInnnttformation(activity, SharedPreferenceData.B_IFSC_C_KEY, ifscCode);
                        SharedPreferenceData.setBankInnnttformation(activity, SharedPreferenceData.B_N_KEY, bankName);
                        SharedPreferenceData.setBankInnnttformation(activity, SharedPreferenceData.BRANCH_ADDRESS_KEY,bankAddress);
                        onBackPressed();
                    }else
                    Toast.makeText(activity, modelMain.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CommonModal> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                System.out.println("updateBankDetails error "+t);
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, mIntentFilter);
    }

    private void intVariables() {
        appBarLayout = findViewById(R.id.appbarLayout);
        mToolbar = appBarLayout.findViewById(R.id.toolbar);
        mToolbar.setTitle("Bank Details");
        mName = findViewById(R.id.naam);
        mAcNumber = findViewById(R.id.acc_num);
        mConfAcNumber = findViewById(R.id.confirm_acc_num);
        mIfscCode = findViewById(R.id.ifsc);
        mBankName = findViewById(R.id.branch_name);
        mBankAddress = findViewById(R.id.branch_address);
        mProgressBar = findViewById(R.id.prgrss_bar);
        mDataConText = findViewById(R.id.internet_text);
    }
}