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
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Adapters.MGBiddingAdapter;
import in.kal_777.kal_777_777.Modals.CommonModal;
import in.kal_777.kal_777_777.Modals.ModelPlaying;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Fragments.MBSFragment;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MGBiddingActivity extends AppCompatActivity {

    private int mProceed =0;
    private int mTotalCoins = 0;
    private int mCurrentCoins = 0;
    private String mGameID;
    private MaterialToolbar mToolbar;
    private TextInputEditText mChooseDate;
    private MaterialTextView mtvTotalCoins;
    private RadioGroup mChooseSes;
    private MaterialAutoCompleteTextView mInputD, mInpCloseD;
    private LinearLayout llBidBottom;
    private TextInputLayout ll_open_cd,digit_lyt;
    private TextInputEditText mInputCoins;
    private MaterialRadioButton mOpen, mClose;
    private MaterialButton btnProceed;
    private ArrayList<String> mNumbers, mNumbers2;
    private RecyclerView mRecyclerView;
    private final List<ModelPlaying> modelPlayingList = new ArrayList<>();
    private MGBiddingAdapter MGBiddingAdapter;
    private MaterialTextView mCoins;
    private ProgressBar mProgressBar;
    private MaterialTextView mDataConText;
    private IntentFilter mIntentFilter;
    NetBroadCastClass broadCastClass;

    boolean isOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mg_bidding_screen);
        getWindow().setBackgroundDrawableResource(R.drawable.ic_splash_gradient);
        intVariables();
        loadData();
        confToolbar();
        confRecycler();
    }

    private void loadData() {
        broadCastClass = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
        mProceed = getIntent().getIntExtra("game_name", 0);
        mGameID = getIntent().getStringExtra("games");
        isOpen = getIntent().getBooleanExtra("open", false);
        mCurrentCoins = Integer.parseInt(SharedPreferenceData.getCustttttomerCoins(MGBiddingActivity.this));
        mNumbers = new ArrayList<String>();
        mNumbers2 = new ArrayList<String>();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("EEE dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        mChooseDate.setText(formattedDate);
        if (isOpen){
            mOpen.setEnabled(true);
            mClose.setChecked(false);
            mOpen.setChecked(true);
        }else {
            mOpen.setEnabled(false);
            mClose.setChecked(true);
            mOpen.setChecked(false);
        }

        mCoins.setText(String.valueOf(mCurrentCoins));

        btnProceed.setOnClickListener(this::proceedBtn);
        if (mProceed ==6){
            mOpen.setChecked(true);
            ll_open_cd.setVisibility(View.VISIBLE);
            digit_lyt.setHint("Open Digit");
            ll_open_cd.setHint("Close Panna");
        }
        mChooseSes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mProceed ==6) confToolbar();
            }
        });
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void intVariables() {
        mToolbar = findViewById(R.id.toolbar);
        mCoins = findViewById(R.id.toolbarPoints);
        mCoins.setVisibility(View.VISIBLE);
        mChooseDate = findViewById(R.id.chus_date);
        mChooseSes = findViewById(R.id.radio_group);
        mInputD = findViewById(R.id.complete_txt_d);
        mInputCoins = findViewById(R.id.edit_text_points);
        mInpCloseD = findViewById(R.id.input_cd);
        mtvTotalCoins = findViewById(R.id.mtv_total_points);
        mOpen = findViewById(R.id.open);
        mClose = findViewById(R.id.close);
        MaterialButton mProConform = findViewById(R.id.pro_conf);
        btnProceed = findViewById(R.id.btn_pro);
        mRecyclerView = findViewById(R.id.a_recyclerView);
        llBidBottom = findViewById(R.id.linear_l_bid_bottom);
        mProgressBar = findViewById(R.id.prgrss_bar);
        mDataConText = findViewById(R.id.internet_text);
        ll_open_cd = findViewById(R.id.ll_open_cd);
        digit_lyt = findViewById(R.id.digit_lyt);

    }

    private void confToolbar() {
        mNumbers.clear();
        mNumbers2.clear();
        switch (mProceed){
            case 1:
                mToolbar.setTitle("Single Digit");
                digit_lyt.setHint("Enter Digit");
                for (int i = 0; i <= 9; i++) {
                    mNumbers.add(String.valueOf(i));
                }
                break;
            case 2:
                mToolbar.setTitle("Jodi Digit");
                digit_lyt.setHint("Enter Digit");
                mChooseSes.setVisibility(View.GONE);
                mChooseSes.setVisibility(View.GONE);
                mOpen.setChecked(true);
                for (int i = 0; i <= 9; i++) {
                    for (int j = 0; j<=9; j++){
                        mNumbers.add(String.valueOf(i)+String.valueOf(j));
                    }
                }
                break;
            case 3:
                mToolbar.setTitle("Single Panna");
                digit_lyt.setHint("Enter Panna");
                for (int a =1 ; a<=9; a++){
                    for (int b = 1;b<=9;b++){
                        for (int c = 0;c<=9;c++){
                            if(a!=b && a!=c && b!=c){
                                if (a < b && b<c||c==0&& a<b){
                                    mNumbers.add(String.valueOf(a)+String.valueOf(b)+String.valueOf(c));
                                }
                            }
                        }
                    }
                }
                break;
            case 4:
                mToolbar.setTitle("Double Panna");
                digit_lyt.setHint("Enter Panna");
                for (int a =1 ; a<=9; a++){
                    for (int b = 0;b<=9;b++){
                        for (int c = 0;c<=9;c++){
                            if(a == b && b < c || b == 0 && c == 0 || a == b && c == 0||a<b && b==c){
                                mNumbers.add(String.valueOf(a)+String.valueOf(b)+String.valueOf(c));
                            }
                        }
                    }
                }
                break;
            case 5:
                mToolbar.setTitle("Triple Panna");
                digit_lyt.setHint("Enter Panna");
                for (int a =0 ; a<=9; a++){
                    for (int b = 0;b<=9;b++){
                        for (int c = 0;c<=9;c++){
                            if(a == b && b == c ){
                                mNumbers.add(String.valueOf(a)+String.valueOf(b)+String.valueOf(c));
                            }
                        }
                    }
                }
                break;
            case 6:
                mToolbar.setTitle("Half Sangam");
                if (mOpen.isChecked()){
                    digit_lyt.setHint("Open Digit");
                    ll_open_cd.setHint("Close Panna");
                    for (int a =0 ; a<=9; a++){
                        mNumbers.add(String.valueOf(a));
                    }
                    for (int a =0 ; a<=9; a++){
                        for (int b = 0;b<=9;b++){
                            for (int c = 0;c<=9;c++){
                                if(a > 0 && a <= b && b <= c || b == 0 && c == 0 || c == 0 && a <= b &&a!=0){
                                    mNumbers2.add(String.valueOf(a)+String.valueOf(b)+String.valueOf(c));
                                }
                            }
                        }
                    }
                }else if (mClose.isChecked()){
                    digit_lyt.setHint("Open Panna");
                    ll_open_cd.setHint("Close Digit");
                    for (int a =0 ; a<=9; a++){
                        for (int b = 0;b<=9;b++){
                            for (int c = 0;c<=9;c++){
                                if(a > 0 && a <= b && b <= c || b == 0 && c == 0 || c == 0 && a <= b &&a!=0){
                                    mNumbers.add(String.valueOf(a)+String.valueOf(b)+String.valueOf(c));
                                }
                            }
                        }
                    }
                    for (int a =0 ; a<=9; a++){
                        mNumbers2.add(String.valueOf(a));
                    }
                }
                break;
            case 7:
                mChooseSes.setVisibility(View.GONE);
                mChooseSes.setVisibility(View.GONE);
                ll_open_cd.setVisibility(View.VISIBLE);
                digit_lyt.setHint("Open Panna");
                ll_open_cd.setHint("Close Panna");
                mToolbar.setTitle("Full Sangam");
                for (int a =0 ; a<=9; a++){
                    for (int b = 0;b<=9;b++){
                        for (int c = 0;c<=9;c++){
                            if(a > 0 && a <= b && b <= c || b == 0 && c == 0 || c == 0 && a <= b &&a!=0){
                                mNumbers.add(String.valueOf(a)+String.valueOf(b)+String.valueOf(c));
                                mNumbers2.add(String.valueOf(a)+String.valueOf(b)+String.valueOf(c));
                            }
                        }
                    }
                }

                break;
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, mNumbers);
        ArrayAdapter<String> mAdapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, mNumbers2);

        mInputD.setThreshold(1);//will start working from first character
        mInputD.setAdapter(mAdapter);//setting the adapter data into the AutoCompleteTextView

        mInpCloseD.setThreshold(1);
        mInpCloseD.setAdapter(mAdapter2);
        if (mProceed!=6){
            int maxLength = mNumbers.get(0).length() ;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            mInputD.setFilters(fArray);

            int maxLength2 = 3 ;
            InputFilter[] fArray2 = new InputFilter[1];
            fArray2[0] = new InputFilter.LengthFilter(maxLength2);
            mInpCloseD.setFilters(fArray2);
        }else {
            int maxLength = 3 ;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            mInputD.setFilters(fArray);
            mInpCloseD.setFilters(fArray);
        }
    }

    public void proceedBtn(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (TextUtils.isEmpty(mInputD.getText().toString())){
            snackbar("Please enter digits",view);
            return;
        }
        switch (mProceed){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                if(!mOpen.isChecked() && !mClose.isChecked()){
                    snackbar("Please select session", view);
                    return;
                }
                if (!mNumbers.contains(mInputD.getText().toString())){
                    snackbar("Please Enter Valid digits", view);
                    return;
                }
                if (TextUtils.isEmpty(mInputCoins.getText().toString().trim())){
                    snackbar("Please Enter Points",view);
                    return;
                }
                if (Integer.parseInt(mInputCoins.getText().toString().trim())<Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_OFFER_SUM_KEY))
                        ||Integer.parseInt(mInputCoins.getText().toString().trim())>Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_OFFER_SUM_KEY))){
                    snackbar("Minimum Bid Points "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_OFFER_SUM_KEY)+" and Maximum Bid Points "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_OFFER_SUM_KEY),view);
                    return;
                }
                setRecycleViewData(mProceed,view);
                break;
            case 6:
                if(!mOpen.isChecked() && !mClose.isChecked()){
                    snackbar("Please select session", view);
                    return;
                }
                if (mOpen.isChecked()){
                    if (!mNumbers.contains(mInputD.getText().toString())){
                        snackbar("Please Enter Valid Open Digits", view);
                        return;
                    }
                    if (TextUtils.isEmpty(mInpCloseD.getText().toString().trim())){
                        snackbar("Please Enter Close Panna", view);
                        return;
                    }
                    if (!mNumbers2.contains(mInpCloseD.getText().toString())){
                        snackbar("Please Enter Valid Close Panna", view);
                        return;
                    }
                }else {
                    if (!mNumbers.contains(mInputD.getText().toString())){
                        snackbar("Please Enter Valid Open Panna", view);
                        return;
                    }
                    if (TextUtils.isEmpty(mInpCloseD.getText().toString().trim())){
                        snackbar("Please Enter Close Digits", view);
                        return;
                    }
                    if (!mNumbers2.contains(mInpCloseD.getText().toString())){
                        snackbar("Please Enter Valid Close Digits", view);
                        return;
                    }
                }
                if (TextUtils.isEmpty(mInputCoins.getText().toString().trim())){
                    snackbar("Please Enter Points",view);
                    return;
                }
                if (Integer.parseInt(mInputCoins.getText().toString().trim())<Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_OFFER_SUM_KEY))
                        ||Integer.parseInt(mInputCoins.getText().toString().trim())>Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_OFFER_SUM_KEY))){
                    snackbar("Minimum Bid Points "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_OFFER_SUM_KEY)+" and Maximum Bid Points "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_OFFER_SUM_KEY),view);
                    return;
                }
                setRecycleViewData(mProceed,view);
                break;
            case 7:
                if (!mNumbers.contains(mInputD.getText().toString())){
                    snackbar("Please Enter Valid Open Panna", view);
                    return;
                }
                if (TextUtils.isEmpty(mInpCloseD.getText().toString().trim())){
                    snackbar("Please Enter Close Panna", view);
                    return;
                }
                if (!mNumbers2.contains(mInpCloseD.getText().toString())){
                    snackbar("Please Enter Valid Close Panna", view);
                    return;
                }
                if (TextUtils.isEmpty(mInputCoins.getText().toString().trim())){
                    snackbar("Please Enter Points",view);
                    return;
                }
                if (Integer.parseInt(mInputCoins.getText().toString().trim())<Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_OFFER_SUM_KEY))
                        ||Integer.parseInt(mInputCoins.getText().toString().trim())>Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_OFFER_SUM_KEY))){
                    snackbar("Minimum Bid Points "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_OFFER_SUM_KEY)+" and Maximum Bid Points "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_OFFER_SUM_KEY),view);
                    return;
                }
                setRecycleViewData(mProceed,view);
                break;
        }

    }

    private void setRecycleViewData(int gameProceed, View view) {
        String openNum = mInputD.getText().toString();
        String closeNum = mInpCloseD.getText().toString();
        String points = mInputCoins.getText().toString();
        mTotalCoins += Integer.parseInt(points);
        if(mTotalCoins > mCurrentCoins){
            snackbar("Insufficient Points",view);
            mTotalCoins = mTotalCoins - Integer.parseInt(points);
            return;
        }
        mCoins.setText(String.valueOf(mCurrentCoins - mTotalCoins));
        switch (gameProceed){
            case 1:
                if(mOpen.isChecked()){
                    modelPlayingList.add(new ModelPlaying(mGameID,"single_digit","Open",points,openNum,"","",""));
                }
                else{
                    modelPlayingList.add(new ModelPlaying(mGameID,"single_digit","Close",points,"",openNum,"",""));
                }
                break;
            case 2:
                String open_digit = openNum.substring(0,1);
                String close_digit = openNum.substring(1,2);
                modelPlayingList.add(new ModelPlaying(mGameID,"jodi_digit","Open",points,open_digit,close_digit,"",""));
                break;
            case 3:
                if(mOpen.isChecked()){
                    modelPlayingList.add(new ModelPlaying(mGameID,"single_panna","Open",points,"","",openNum,""));
                }
                else{
                    modelPlayingList.add(new ModelPlaying(mGameID,"single_panna","Close",points,"","","",openNum));
                }
                break;
            case 4:
                if(mOpen.isChecked()){
                    modelPlayingList.add(new ModelPlaying(mGameID,"double_panna","Open",points,"","",openNum,""));
                }
                else{
                    modelPlayingList.add(new ModelPlaying(mGameID,"double_panna","Close",points,"","","",openNum));
                }
                break;
            case 5:
                if(mOpen.isChecked()){
                    modelPlayingList.add(new ModelPlaying(mGameID,"triple_panna","Open",points,"","",openNum,""));
                }
                else{
                    modelPlayingList.add(new ModelPlaying(mGameID,"triple_panna","Close",points,"","","",openNum));
                }
                break;
            case 6:
                if(mOpen.isChecked()){
                    modelPlayingList.add(new ModelPlaying(mGameID,"half_sangam","Open",points,openNum,"","",closeNum));
                }
                else{
                    modelPlayingList.add(new ModelPlaying(mGameID,"half_sangam","Close",points,"",closeNum,openNum,""));
                }
                break;
            case 7:
                modelPlayingList.add(new ModelPlaying(mGameID,"full_sangam","Open",points,"","",openNum,closeNum));
                break;
        }
        mInpCloseD.setText("");
        mInputD.setText("");
        mInputCoins.setText("");

        if(gameProceed!=2) {
            if (isOpen){
                mOpen.setEnabled(true);
                mClose.setChecked(false);
                mOpen.setChecked(true);
            }else {
                mOpen.setEnabled(false);
                mClose.setChecked(true);
                mOpen.setChecked(false);
            }
        }
        mRecyclerView.setVisibility(View.VISIBLE);
        llBidBottom.setVisibility(View.VISIBLE);
        mtvTotalCoins.setText("Total Points : "+ mTotalCoins);
         confRecycler();
        MGBiddingAdapter.notifyDataSetChanged();
    }

    private void snackbar(String messaage, View view) {
        Snackbar.make(view,messaage, 2000).show();
    }

    public void secondProceedBtn(View view) {

        String gsonData = new Gson().toJson(modelPlayingList);
        String serverData = "{ \"bids\":"+gsonData+"}";

        System.out.println("serverData : "+ serverData);

        MBSFragment MBSFragment = new MBSFragment(new MBSFragment.OnConformClick() {
            @Override
            public void onConformClick() {
                if (InternetService.isOnline(MGBiddingActivity.this))
                    conDiaMethod(MGBiddingActivity.this, serverData);
                else
                    Toast.makeText(MGBiddingActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        MBSFragment.show(getSupportFragmentManager(),"bottomSheet");
        MBSFragment.setCancelable(false);
    }

    AlertDialog dialog;
    private void conDiaMethod(MGBiddingActivity activity, String serverData) {

        mProgressBar.setVisibility(View.VISIBLE);
        Call<CommonModal> call = ApiClient.getClient().place_bid(SharedPreferenceData.getLogiiiinInToken(MGBiddingActivity.this), serverData);
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
                        SharedPreferenceData.setUseeeeerCoins(MGBiddingActivity.this, mCoins.getText().toString());
                        modelPlayingList.clear();
                        MGBiddingAdapter.notifyDataSetChanged();
                        llBidBottom.setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MGBiddingActivity.this);
                        LayoutInflater inflater = LayoutInflater.from(MGBiddingActivity.this);
                        View view = inflater.inflate(R.layout.bs_bidding_layout, null);
                        builder.setView(view);
                        dialog = builder.create();
                     //   dialog.show();
                        dialog.getWindow().setLayout(700,LinearLayout.LayoutParams.WRAP_CONTENT);
                    }
                    Toast.makeText(MGBiddingActivity.this, commonModal.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MGBiddingActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<CommonModal> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                System.out.println("Placed Bid OnFailure "+t);
                mProgressBar.setVisibility(View.GONE);
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
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, mIntentFilter);
    }

    private void confRecycler() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        MGBiddingAdapter = new MGBiddingAdapter(this, modelPlayingList, new MGBiddingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int index = position;
                int size = modelPlayingList.size();
                if(position-size>=0){
                    index = size-1;
                }
                int bid_points = Integer.parseInt(modelPlayingList.get(index).getBid_points());
                mTotalCoins = mTotalCoins - bid_points;
                mtvTotalCoins.setText("Total Points : "+ mTotalCoins);
                modelPlayingList.remove(index);
                if(modelPlayingList.size()==0) llBidBottom.setVisibility(View.GONE);
                MGBiddingAdapter.notifyItemRemoved(position);
                mCoins.setText(String.valueOf(mCurrentCoins - mTotalCoins));
            }
        });
        mRecyclerView.setAdapter(MGBiddingAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }
}