package in.kal_777.kal_777_777.Activities;


import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Adapters.WalletAdapter;
import in.kal_777.kal_777_777.Modals.CommonModal;
import in.kal_777.kal_777_777.Modals.WalletStatementModal;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TakeOutActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private MaterialTextView toolbarPoints, openTime, closeTime;
    private ProgressBar progressBar;
    private WalletAdapter walletAdapter;
    private List<WalletStatementModal.Data.Statement> statementArrayList = new ArrayList<>();
    private ShapeableImageView emptyIV;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private int currentCoins = 0;
    private TextInputEditText inWithdCoins;
    private int points;
    private MaterialTextView sPayMethod,withdrawNotice;
    private MaterialTextView mDataConText;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_withdraw);
        intVariables();
        loadData();
        toolbarMethod();
        withdSMethod(TakeOutActivity.this);
    }

    private void loadData() {
        NetBroadCastClass broadCastClass = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);

        toolbarPoints.setText(SharedPreferenceData.getCustttttomerCoins(TakeOutActivity.this));

    }
    
    //Replace the function 

    private void withdSMethod(TakeOutActivity activity) {
        swipeRefreshLayout.setRefreshing(true);
        Call call = ApiClient.getClient().withdraw_statement(SharedPreferenceData.getLogiiiinInToken(this),"");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    WalletStatementModal statementModal = response.body();
                    if (statementModal.getCode().equalsIgnoreCase("505")) {
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, statementModal.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (statementModal.getStatus().equalsIgnoreCase("success")) {
                        if (statementModal.getData().getStatement() !=null) {
                            emptyIV.setVisibility(View.GONE);
                            statementArrayList = statementModal.getData().getStatement();
                            LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
                            recyclerView.setLayoutManager(layoutManager);
                            walletAdapter = new WalletAdapter(activity, statementArrayList);
                            recyclerView.setAdapter(walletAdapter);
                        } else {
                            emptyIV.setVisibility(View.VISIBLE);
                        }
                    }

                    Toast.makeText(activity, statementModal.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("walletStatement error "+t);
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toolbarMethod() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                withdSMethod(TakeOutActivity.this);
            }
        });

    }

    public void btnWithdCoins(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        String s = inWithdCoins.getText().toString();
        if (s.length()>0){
            points = Integer.parseInt(s);
        }
        if (TextUtils.isEmpty(inWithdCoins.getText().toString())){
            Snackbar.make(view, "Enter Points", 2000).show();
            return;
        }
        if (points<Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_EXTRACT_COINS_KEY))){
            Snackbar.make(view, "Minimum Points must be greater then "+" "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_EXTRACT_COINS_KEY),2000).show();
            return;
        }
        if (points>Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_EXTRACT_COINS_KEY))){
            Snackbar.make(view, "Maximum Points must be less then "+" "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_EXTRACT_COINS_KEY), 2000).show();
            return;
        }
        if (sPayMethod.getText().toString().equals("Select Payment Method")){
            Snackbar.make(view, "Please Select Payment Method", 2000).show();
            return;
        }
        if (InternetService.isOnline(this))
        withdCoinsM(TakeOutActivity.this, inWithdCoins.getText().toString(), sPayMethod.getText().toString());
        else Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
    }

    private void withdCoinsM(TakeOutActivity activity, String points, String method) {
        progressBar.setVisibility(View.VISIBLE);
        String methodStr = null;
        if (method.contains("Account number")){
            methodStr = "bank_name";
        }else if (method.contains("PayTM")){
            methodStr = "paytm_mobile_no";
        }else if (method.contains("PhonePe")){
            methodStr = "phonepe_mobile_no";
        }else if (method.contains("GooglePay")){
            methodStr = "gpay_mobile_no";
        }
        Call<CommonModal> call = ApiClient.getClient().withdraw(SharedPreferenceData.getLogiiiinInToken(activity), points, methodStr);
        call.enqueue(new Callback<CommonModal>() {
            @Override
            public void onResponse(Call<CommonModal> call, Response<CommonModal> response) {
                if (response.isSuccessful()){
                    CommonModal commonModal = response.body();
                    assert commonModal != null;
                    if (commonModal.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, commonModal.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (commonModal.getStatus().equalsIgnoreCase("success")){
                        currentCoins = Integer.parseInt(SharedPreferenceData.getCustttttomerCoins(activity))-Integer.parseInt(points);
                        toolbarPoints.setText(String.valueOf(currentCoins));
                        SharedPreferenceData.setUseeeeerCoins(TakeOutActivity.this, String.valueOf(currentCoins));
                        withdSMethod(activity);
                    }
                    Toast.makeText(TakeOutActivity.this, commonModal.getMessage(), Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(TakeOutActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CommonModal> call, Throwable t) {
                System.out.println("withdrawPoints Error "+t);
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private PopupWindow popupWindow;
    private MaterialTextView bInfo, pUpi, pPeUpi, gPUpi;
    public void sPayMethod(View view) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_select_pay_method_layout, null);
        bInfo = popupView.findViewById(R.id.bankDetails);
        pPeUpi = popupView.findViewById(R.id.phonePeUpi);
        gPUpi = popupView.findViewById(R.id.googlePayUpi);
        pUpi = popupView.findViewById(R.id.paytmUpi);
        popupWindow = new PopupWindow(popupView, 900, LinearLayout.LayoutParams.WRAP_CONTENT, true );
        popupWindow.setElevation(20);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(view,0,-135);


        if (SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.B_AC_N_KEY)!=null && !SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.B_AC_N_KEY).equals("")){
            bInfo.setText("Account number ( "+ SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.B_AC_N_KEY)+" )");
        }
        if (SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.P_UPI_ID_KEY)!=null && !SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.P_UPI_ID_KEY).equals("")){
            pUpi.setText("PayTM ( "+ SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.P_UPI_ID_KEY)+" )");
        }
        if (SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.PP_UPI_ID_KEY)!=null && !SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.PP_UPI_ID_KEY).equals("")){
            pPeUpi.setText("PhonePe ( "+ SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.PP_UPI_ID_KEY)+" )");
        }
        if (SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.G_PAY_UPI_ID_KEY)!=null && !SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.G_PAY_UPI_ID_KEY).equals("")){
            gPUpi.setText("GooglePay ( "+ SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.G_PAY_UPI_ID_KEY)+" )");
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }
    public void googlepayUpi(View view) {
        if (SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.G_PAY_UPI_ID_KEY)!=null && !SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.G_PAY_UPI_ID_KEY).equals("")){
            sPayMethod.setText(gPUpi.getText().toString());
        }else {
            Intent intent = new Intent(this, UPIActivity.class);
            intent.putExtra("UPI",3);
            startActivity(intent);
        }
        popupWindow.dismiss();
    }


    public void payTmUpi(View view) {
        if (SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.P_UPI_ID_KEY)!=null && !SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.P_UPI_ID_KEY).equals("")){
            sPayMethod.setText(pUpi.getText().toString());
        }else{
            Intent intent = new Intent(this, UPIActivity.class);
            intent.putExtra("UPI", 1);
            startActivity(intent);
        }
        popupWindow.dismiss();
    }

    public void bankDeails(View view) {
        if (SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.B_AC_N_KEY)!=null && !SharedPreferenceData.getBankkkkkObject(this, SharedPreferenceData.B_AC_N_KEY).equals("")){
            sPayMethod.setText(bInfo.getText().toString());
        }else {
            Intent intent = new Intent(this, BankDetailsActivity.class);
            startActivity(intent);
        }
        popupWindow.dismiss();
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
    private void intVariables() {
        sPayMethod = findViewById(R.id.select_pay_method);
        withdrawNotice = findViewById(R.id.withdrawNotice);
        progressBar = findViewById(R.id.prgrss_bar);
        openTime = findViewById(R.id.openTime);
        closeTime = findViewById(R.id.closeTime);
        AppBarLayout appBarLayout = findViewById(R.id.appbarLayout);
        toolbar = appBarLayout.findViewById(R.id.toolbar);
        toolbar.setTitle("Withdraw Points");
        toolbarPoints = appBarLayout.findViewById(R.id.toolbarPoints);
        toolbarPoints.setVisibility(View.VISIBLE);
        inWithdCoins = findViewById(R.id.input_withdraw_Points);
        swipeRefreshLayout = findViewById(R.id.mswipe_ref_lyt);
        recyclerView = findViewById(R.id.a_recyclerView);
        mDataConText = findViewById(R.id.internet_text);
        emptyIV = findViewById(R.id.aempty_image);

        if (SharedPreferenceData.getString(this, SharedPreferenceData.WITHDRAW_NOTICE)!=null)
            withdrawNotice.setText(SharedPreferenceData.getString(this, SharedPreferenceData.WITHDRAW_NOTICE));
        openTime.setText("Open: "+SharedPreferenceData.getString(this, SharedPreferenceData.WITHDRAW_OPEN_TIME));
        closeTime.setText("Close: "+SharedPreferenceData.getString(this, SharedPreferenceData.WITHDRAW_CLOSE_TIME));
    }
    public void phonePeUpi(View view) {
        if (SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.PP_UPI_ID_KEY)!=null && !SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.PP_UPI_ID_KEY).equals("")){
            sPayMethod.setText(pPeUpi.getText().toString());
        }else {
            Intent intent = new Intent(this, UPIActivity.class);
            intent.putExtra("UPI", 2);
            startActivity(intent);
        }
        popupWindow.dismiss();

    }
}