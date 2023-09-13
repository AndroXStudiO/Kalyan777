package in.kal_777.kal_777_777.Activities;


import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Adapters.SLBiddingAdapter;
import in.kal_777.kal_777_777.Modals.CommonModal;
import in.kal_777.kal_777_777.Modals.ModelSLB;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Fragments.MBSFragment;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SLBiddingActivity extends AppCompatActivity {

    private int TuranPro =0;
    private int totalCoins = 0;
    private int currentCoins = 0;
    private String turnamentID;
    private final boolean isOpen = false;
    private MaterialToolbar toolbar;
    private MaterialTextView mtotalCoins;
    private MaterialAutoCompleteTextView inD;
    private LinearLayout ll_b_b;
    private LinearLayout ll_open_cd;
    private TextInputEditText inputCoins;
    private ArrayList<String> digits;
    private RecyclerView recyclerView;
    private final List<ModelSLB> modelSLBList = new ArrayList<>();
    private SLBiddingAdapter SLBiddingAdapter;
    private MaterialTextView coins;
    private ProgressBar progressBar;
    private MaterialTextView dataConText;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sl_bidding_screen);
        intVariables();
        confToolbar();
        confRecycler();
        loadData();
    }

    private void loadData() {
        NetBroadCastClass broadCastClass = new NetBroadCastClass(dataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
    }

    private void intVariables() {
        toolbar = findViewById(R.id.toolbar);
        coins = findViewById(R.id.toolbarPoints);
        MaterialTextView chooseDate = findViewById(R.id.chus_date);
        inD = findViewById(R.id.complete_txt_d);
        inputCoins = findViewById(R.id.edit_text_points);
        mtotalCoins = findViewById(R.id.mtv_total_points);
        MaterialButton btn_proceed = findViewById(R.id.btn_pro);
        recyclerView = findViewById(R.id.a_recyclerView);
        ll_b_b = findViewById(R.id.linear_l_bid_bottom);
        progressBar = findViewById(R.id.prgrss_bar);
        dataConText = findViewById(R.id.internet_text);
        TuranPro = getIntent().getIntExtra("game_name", 8);
        turnamentID = getIntent().getStringExtra("games");
        currentCoins = Integer.parseInt(SharedPreferenceData.getCustttttomerCoins(SLBiddingActivity.this));
        coins.setText(String.valueOf(currentCoins));
        digits = new ArrayList<String>();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("EEE dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        chooseDate.setText(formattedDate);
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceed(v);
            }
        });

    }
    private void confRecycler() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        SLBiddingAdapter = new SLBiddingAdapter(this, modelSLBList, new SLBiddingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int index = position;
                int size = modelSLBList.size();
                if(position-size>=0){
                    index = size-1;
                }
                int bid_points = Integer.parseInt(modelSLBList.get(index).getBid_points());
                totalCoins = totalCoins - bid_points;
                mtotalCoins.setText("Total Points : "+ totalCoins);
                modelSLBList.remove(index);
                if(modelSLBList.size()==0) ll_b_b.setVisibility(View.GONE);
                SLBiddingAdapter.notifyItemRemoved(position);
                coins.setText(String.valueOf(currentCoins - totalCoins));
            }
        });
        recyclerView.setAdapter(SLBiddingAdapter);
    }

    private void confToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        switch (TuranPro){
            case 8:
                actionbar.setTitle("Single Digit");
                for (int i = 0; i <= 9; i++) {
                    digits.add(String.valueOf(i));
                }
                break;
            case 9:
                actionbar.setTitle("Single Panna");
                for (int a =1 ; a<=9; a++){
                    for (int b = 1;b<=9;b++){
                        for (int c = 0;c<=9;c++){
                            if(a!=b && a!=c && b!=c){
                                if (a < b && b<c||c==0&& a<b){
                                    digits.add(String.valueOf(a)+String.valueOf(b)+String.valueOf(c));
                                }
                            }
                        }
                    }
                }
                break;
            case 10:
                actionbar.setTitle("Double Panna");
                for (int a =1 ; a<=9; a++){
                    for (int b = 0;b<=9;b++){
                        for (int c = 0;c<=9;c++){
                            if(a == b && b < c || b == 0 && c == 0 || a == b && c == 0||a<b && b==c){
                                digits.add(String.valueOf(a)+String.valueOf(b)+String.valueOf(c));
                            }
                        }
                    }
                }
                break;
            case 11:
                actionbar.setTitle("Triple Panna");
                for (int a =0 ; a<=9; a++){
                    for (int b = 0;b<=9;b++){
                        for (int c = 0;c<=9;c++){
                            if(a == b && b == c ){
                                digits.add(String.valueOf(a)+String.valueOf(b)+String.valueOf(c));
                            }
                        }
                    }
                }
                break;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, digits);

        inD.setThreshold(1);//will start working from first character
        inD.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        int maxLength = digits.get(0).length() ;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        inD.setFilters(fArray);
    }

    public void proceed(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (TextUtils.isEmpty(inD.getText().toString())){
            snackbar("Please enter digits",view);
            return;
        }
        if (!digits.contains(inD.getText().toString())){
            snackbar("Please Enter Valid digits", view);
            return;
        }
        if (TextUtils.isEmpty(inputCoins.getText().toString().trim())){
            snackbar("Please Enter Points",view);
            return;
        }
        if (Integer.parseInt(inputCoins.getText().toString().trim())<Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_OFFER_SUM_KEY))
                ||Integer.parseInt(inputCoins.getText().toString().trim())>Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_OFFER_SUM_KEY))){
            snackbar("Minimum Bid Points "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_OFFER_SUM_KEY)+" and Maximum Bid Points "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_OFFER_SUM_KEY),view);
            return;
        }
        setRecycleData(TuranPro,view);

    }

    private void setRecycleData(int gameProceed, View view) {
        String openNum = inD.getText().toString();
        String points = inputCoins.getText().toString();
        totalCoins += Integer.parseInt(points);
        if(totalCoins > currentCoins){
            snackbar("Insufficient Points",view);
            totalCoins = totalCoins - Integer.parseInt(points);
            return;
        }
        coins.setText(String.valueOf(currentCoins - totalCoins));
        switch (gameProceed){
            case 8:
                modelSLBList.add(new ModelSLB(turnamentID,"single_digit",points,openNum,""));
                break;
            case 9:
                modelSLBList.add(new ModelSLB(turnamentID,"single_panna",points,"",openNum));
                break;
            case 10:
                modelSLBList.add(new ModelSLB(turnamentID,"double_panna",points,"",openNum));
                break;
            case 11:
                modelSLBList.add(new ModelSLB(turnamentID,"triple_panna",points,"",openNum));
                break;
        }
        inD.setText("");
        inputCoins.setText("");
        recyclerView.setVisibility(View.VISIBLE);
        ll_b_b.setVisibility(View.VISIBLE);
        mtotalCoins.setText("Total Points : "+ totalCoins);
        SLBiddingAdapter.notifyDataSetChanged();
    }

    private void snackbar(String messaage, View view) {
        Snackbar.make(view,messaage, 2000).show();
    }

    public void proceedConformBtn(View view) {

        String gsonData = new Gson().toJson(modelSLBList);
        String serverData = "{ \"bids\":"+gsonData+"}";

        MBSFragment MBSFragment = new MBSFragment(new MBSFragment.OnConformClick() {
            @Override
            public void onConformClick() {
                if (InternetService.isOnline(SLBiddingActivity.this))
                    conformDialog(SLBiddingActivity.this, serverData);
                else
                    Toast.makeText(SLBiddingActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        MBSFragment.show(getSupportFragmentManager(),"bottomSheet");
        MBSFragment.setCancelable(false);
    }

    AlertDialog dialog;
    private void conformDialog(SLBiddingActivity activity, String serverData) {

        progressBar.setVisibility(View.VISIBLE);
        Call<CommonModal> call = ApiClient.getClient().starline_place_bid(SharedPreferenceData.getLogiiiinInToken(SLBiddingActivity.this), serverData);
        call.enqueue(new Callback<CommonModal>() {
            @Override
            public void onResponse(@NonNull Call<CommonModal> call, @NonNull Response<CommonModal> response) {
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
                    if (commonModal.getStatus().equals("success")){
                        SharedPreferenceData.setUseeeeerCoins(SLBiddingActivity.this, coins.getText().toString());
                        modelSLBList.clear();
                        SLBiddingAdapter.notifyDataSetChanged();
                        ll_b_b.setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(SLBiddingActivity.this);
                        LayoutInflater inflater = LayoutInflater.from(SLBiddingActivity.this);
                        View view = inflater.inflate(R.layout.bs_bidding_layout, null);
                        builder.setView(view);
                        dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setLayout(700,LinearLayout.LayoutParams.WRAP_CONTENT);
                    }
                    Toast.makeText(SLBiddingActivity.this, commonModal.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SLBiddingActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<CommonModal> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                System.out.println("starlinePlaceBid OnFailure "+t);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void DismisBtn(View view) {
        dialog.dismiss();
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