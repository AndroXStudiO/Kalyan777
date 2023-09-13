package in.kal_777.kal_777_777.Activities;

import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.snpinfo.upipayment.UpiPayment;
import com.snpinfo.upipayment.entity.TransactionResponse;
import com.snpinfo.upipayment.listener.PaymentStatusListener;
import com.snpinfo.upipayment.util.Validator;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Modals.CommonModal;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCoinActivity extends AppCompatActivity implements PaymentStatusListener {

    private TextInputEditText mInputCoins;
    private int mCoins;
    private MaterialToolbar mToolbar;
    private ProgressBar mProgressBar;
    private String mPayApp;
    String amount;
    private RadioGroup mRadioGroup;
    private IntentFilter mIntentFilter;
    NetBroadCastClass broadCastClass;
    AppBarLayout appBarLayout;
    MaterialTextView addFundNotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coin_screen);
        intVeriables();
        confiToolbar();
    }
    private void confiToolbar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.GPay:
                        mPayApp = Validator.GPAY;
                        break;
                    case R.id.phone_pe_btn:
                        mPayApp = Validator.PHONE_PAY;
                        break;
                    case R.id.payTmBtn:
                        mPayApp = Validator.PAYTM;
                        break;
                    default:
                        mPayApp = Validator.ALL_PAYMENT_APP;
                        break;
                }
            }
        });
    }

    private void payDialog() {
        String transactionId = "TID" + System.currentTimeMillis();
        amount = mInputCoins.getText().toString()+".0";
        // START PAYMENT INITIALIZATION
        UpiPayment upiPayment = new UpiPayment.Builder()
                .with(this)
                .setPayeeVpa(SharedPreferenceData.getAddAmmmmmmountUpiId(this, SharedPreferenceData.ADD_COINS_BHIM_ID_KEY))
                .setPayeeName(SharedPreferenceData.getAddAmmmmmmountUpiId(this, SharedPreferenceData.ADD_COINS_BHIM_NAME_KEY))
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionId)
                .setDescription(getString(R.string.app_name))
                .setAmount(amount)
                .build();

        // END INITIALIZATION
        try {
            upiPayment.setPaymentStatusListener(this);

            // Start payment / transaction
            upiPayment.pay(mPayApp);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error "+exception.getMessage());

        }
    }

    private void addCoinMethod(AddCoinActivity activity, String amount, String payApp) {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<CommonModal> call = ApiClient.getClient().add_fund(SharedPreferenceData.getLogiiiinInToken(activity), amount,"successful","3resdn34yw8er", payApp);
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
                        Toast.makeText(AddCoinActivity.this, commonModal.getMessage(), Toast.LENGTH_SHORT).show();
                        mInputCoins.setText("");
                    }
                    Toast.makeText(AddCoinActivity.this, commonModal.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CommonModal> call, Throwable t) {
                System.out.println("addFund Error "+t);
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onTransactionCancelled() {
        // Payment Cancelled by User
        toast("Cancelled by user");
    }


    @Override
    public void onTransactionCompleted(TransactionResponse transactionDetails) {

    }

    @Override
    public void onTransactionSuccess(TransactionResponse transactionDetails) {
        toast("Success");
        addCoinMethod(this,amount, mPayApp);
    }

    @Override
    public void onTransactionSubmitted() {
        // Payment Pending
        toast("Pending | Submitted");
    }
    @Override
    public void onTransactionFailed() {
        // Payment Failed
        toast("Failed");
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setClipboard(Context context, String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "UPI Copied", Toast.LENGTH_SHORT).show();
    }

    private void intVeriables() {
        appBarLayout = findViewById(R.id.appbarLayout);
        mToolbar = appBarLayout.findViewById(R.id.toolbar);
        mToolbar.setTitle("Add Money");
        mInputCoins = findViewById(R.id.edit_text_points);
        mProgressBar = findViewById(R.id.prgrss_bar);
        addFundNotice = findViewById(R.id.addFundNotice);
        mRadioGroup = findViewById(R.id.radio_group);
        MaterialTextView mDataConText = findViewById(R.id.internet_text);
        broadCastClass = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
        if (SharedPreferenceData.getString(this, SharedPreferenceData.ADD_FUND_NOTICE)!=null)
            addFundNotice.setText(SharedPreferenceData.getString(this, SharedPreferenceData.ADD_FUND_NOTICE));
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
    public void submitCoins(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        String mString = mInputCoins.getText().toString();
        if (mString.length()>0){
            mCoins = Integer.parseInt(mString);
        }
        if (TextUtils.isEmpty(mString)){
            Snackbar.make(view, "Please Enter Points", 2000).show();
            return;
        }
        if (mCoins <Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_ADD_AMOUNT_COINS_KEY))){
            Snackbar.make(view,"Minimum Points must be greater then "+" "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_ADD_AMOUNT_COINS_KEY), 2000).show();
            return;
        }
        if (mCoins >Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_ADD_AMOUNT_COINS_KEY))){
            Snackbar.make(view, "Maximum Points must be less then "+" "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_ADD_AMOUNT_COINS_KEY), 2000).show();
            return;
        }
        if (mRadioGroup.getCheckedRadioButtonId()==-1){
            Snackbar.make(view,"Please Select Payment Method",2000).show();
            return;
        }
        if (InternetService.isOnline(this)) payDialog();
        else Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
    }

    public void clipClick(View view) {
        mInputCoins.setText(view.getTag().toString());
        mInputCoins.setSelection(mInputCoins.getText().length());
    }
}