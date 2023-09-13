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
import in.kal_777.kal_777_777.Adapters.GDBiddingAdapter;
import in.kal_777.kal_777_777.Fragments.MBSFragment;
import in.kal_777.kal_777_777.Modals.CommonModal;
import in.kal_777.kal_777_777.Modals.ModelDesawarBid;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GDBiddingActivity extends AppCompatActivity {

    private int TuranPro =0;
    private int tPoints = 0;
    private int remberC = 0;
    private String turnaID;
    private MaterialToolbar atToolbar;
    private MaterialTextView aTCoins;
    private MaterialAutoCompleteTextView completeTextView;
    private LinearLayout llBB;
    private TextInputEditText edtTxtCoins;
    private ArrayList<String> dgt;
    private RecyclerView aRecyView;
    private final List<ModelDesawarBid> desawarBidArrayList = new ArrayList<>();
    private GDBiddingAdapter GDBiddingAdapter;
    private MaterialTextView intemCoins;
    private ProgressBar aProgressBar;
    private MaterialTextView internetText;
    private IntentFilter aIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd_bidding_screen);
        initializeIDs();
        clickListener();
        recyViewMethod();
        syncData();
    }

    private void initializeIDs() {
        atToolbar = findViewById(R.id.toolbar);
        intemCoins = findViewById(R.id.toolbarPoints);
        TextInputEditText chuseDate = findViewById(R.id.chus_date);
        completeTextView = findViewById(R.id.complete_txt_d);
        edtTxtCoins = findViewById(R.id.edit_text_points);
        aTCoins = findViewById(R.id.mtv_total_points);
        MaterialButton btn_pro = findViewById(R.id.btn_pro);
        aRecyView = findViewById(R.id.a_recyclerView);
        llBB = findViewById(R.id.linear_l_bid_bottom);
        aProgressBar = findViewById(R.id.prgrss_bar);
        internetText = findViewById(R.id.internet_text);
        TuranPro = getIntent().getIntExtra("game_name", 12);
        turnaID = getIntent().getStringExtra("games");

        atToolbar.setTitle(TuranPro);
        remberC = Integer.parseInt(SharedPreferenceData.getCustttttomerCoins(GDBiddingActivity.this));
        dgt = new ArrayList<String>();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("EEE dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        chuseDate.setText(formattedDate);
        btn_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstProceed(v);
            }
        });

    }

    private void syncData() {
        NetBroadCastClass netBroadcastClass = new NetBroadCastClass(internetText);
        aIntentFilter = new IntentFilter();
        aIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
    }

    private void recyViewMethod() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        aRecyView.setLayoutManager(layoutManager);
        GDBiddingAdapter = new GDBiddingAdapter(this, desawarBidArrayList, new GDBiddingAdapter.OnItemClickListenerInter() {
            @Override
            public void onItemClickFun(int position) {
                int index = position;
                int size = desawarBidArrayList.size();
                if(position-size>=0){
                    index = size-1;
                }
                int bid_points = Integer.parseInt(desawarBidArrayList.get(index).getBid_points());
                tPoints = tPoints - bid_points;
                aTCoins.setText("Total Points : "+ tPoints);
                desawarBidArrayList.remove(index);
                if(desawarBidArrayList.size()==0) llBB.setVisibility(View.GONE);
                GDBiddingAdapter.notifyItemRemoved(position);
                intemCoins.setText(String.valueOf(remberC - tPoints));
            }
        });
        aRecyView.setAdapter(GDBiddingAdapter);
    }


    private void clickListener() {
        setSupportActionBar(atToolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        atToolbar.setTitleTextColor(Color.WHITE);
        atToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        switch (TuranPro){
            case 12:
                actionbar.setTitle("Left Digit");
                for (int i = 0; i <= 9; i++) {
                    dgt.add(String.valueOf(i));
                }
                break;
            case 13:
                actionbar.setTitle("Right Digit");
                for (int i = 0; i <= 9; i++) {
                    dgt.add(String.valueOf(i));
                }                break;
            case 14:
                actionbar.setTitle("Jodi Digit");
                for (int i = 0; i <= 9; i++) {
                    for (int j = 0; j<=9; j++){
                        dgt.add(String.valueOf(i)+String.valueOf(j));
                    }
                }                break;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, dgt);

        completeTextView.setThreshold(1);//will start working from first character
        completeTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        int maxLength = dgt.get(0).length() ;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        completeTextView.setFilters(fArray);
    }

    public void firstProceed(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (TextUtils.isEmpty(completeTextView.getText().toString())){
            snackbarMethod("Please Enter Points",view);
            return;
        }
        if (!dgt.contains(completeTextView.getText().toString())){
            snackbarMethod("Please Enter Valid Points", view);
            return;
        }
        if (TextUtils.isEmpty(edtTxtCoins.getText().toString().trim())){
            snackbarMethod("Please Enter Points",view);
            return;
        }
        if (Integer.parseInt(edtTxtCoins.getText().toString().trim())<Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_OFFER_SUM_KEY))
                ||Integer.parseInt(edtTxtCoins.getText().toString().trim())>Integer.parseInt(SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_OFFER_SUM_KEY))){
            snackbarMethod("Minimum Bid Points "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MIN_OFFER_SUM_KEY)+" and Maximum Bid Points "+ SharedPreferenceData.getMaxMiiinnnObject(this, SharedPreferenceData.MAX_OFFER_SUM_KEY),view);
            return;
        }
        setRecycleBids(TuranPro,view);

    }

    private void setRecycleBids(int gameProceed, View view) {
        String openNum = completeTextView.getText().toString();
        String coins = edtTxtCoins.getText().toString();
        tPoints += Integer.parseInt(coins);
        if(tPoints > remberC){
            snackbarMethod("Insufficient Points",view);
            tPoints = tPoints - Integer.parseInt(coins);
            return;
        }
        intemCoins.setText(String.valueOf(remberC - tPoints));
        switch (gameProceed){
            case 12:
                desawarBidArrayList.add(new ModelDesawarBid(turnaID,"left_digit",coins,openNum,""));
                break;
            case 13:
                desawarBidArrayList.add(new ModelDesawarBid(turnaID,"right_digit",coins,"",openNum));
                break;
            case 14:
                String left_digit = openNum.substring(0,1);
                String right_digit = openNum.substring(1,2);
                desawarBidArrayList.add(new ModelDesawarBid(turnaID,"jodi_digit",coins,left_digit,right_digit));
                break;
        }
        completeTextView.setText("");
        edtTxtCoins.setText("");
        aRecyView.setVisibility(View.VISIBLE);
        llBB.setVisibility(View.VISIBLE);
        aTCoins.setText("Total Points : "+ tPoints);
        GDBiddingAdapter.notifyDataSetChanged();
    }

    private void snackbarMethod(String messaage, View view) {
        Snackbar.make(view,messaage, 2000).show();
    }


    AlertDialog dialog;
    private void confirmDialog(GDBiddingActivity activity, String serverData) {

        aProgressBar.setVisibility(View.VISIBLE);
        Call<CommonModal> call = ApiClient.getClient().gali_disawar_place_bid(SharedPreferenceData.getLogiiiinInToken(GDBiddingActivity.this), serverData);
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
                        SharedPreferenceData.setUseeeeerCoins(GDBiddingActivity.this, intemCoins.getText().toString());
                        desawarBidArrayList.clear();
                        GDBiddingAdapter.notifyDataSetChanged();
                        llBB.setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(GDBiddingActivity.this);
                        LayoutInflater inflater = LayoutInflater.from(GDBiddingActivity.this);
                        View view = inflater.inflate(R.layout.bs_bidding_layout, null);
                        builder.setView(view);
                        dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setLayout(700,LinearLayout.LayoutParams.WRAP_CONTENT);
                    }
                    Toast.makeText(GDBiddingActivity.this, commonModal.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(GDBiddingActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
                aProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<CommonModal> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                System.out.println("starlinePlaceBid OnFailure "+t);
                aProgressBar.setVisibility(View.GONE);
            }
        });

    }
    public void secondProceedBtn(View view) {

        String gsonData = new Gson().toJson(desawarBidArrayList);
        String serverData = "{ \"bids\":"+gsonData+"}";

        MBSFragment MBSFragment = new MBSFragment(new MBSFragment.OnConformClick() {
            @Override
            public void onConformClick() {
                if (InternetService.isOnline(GDBiddingActivity.this))
                    confirmDialog(GDBiddingActivity.this, serverData);
                else
                    Toast.makeText(GDBiddingActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        MBSFragment.show(getSupportFragmentManager(),"bottomSheet");
        MBSFragment.setCancelable(false);

    }

    public void DismisBtn(View view) {
        dialog.dismiss();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(myReceiver, aIntentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, aIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

}