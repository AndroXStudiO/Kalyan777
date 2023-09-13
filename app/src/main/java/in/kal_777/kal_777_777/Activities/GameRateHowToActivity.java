package in.kal_777.kal_777_777.Activities;


import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Modals.GameRateListModal;
import in.kal_777.kal_777_777.Modals.HowToPlayModal;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameRateHowToActivity extends AppCompatActivity {

    private MaterialToolbar mToolbar;
    private MaterialCardView mSingle, mJodi, mSingleP, mDoubleD, mTripleD, mHalfS, mFullS;
    private MaterialTextView mSingleDValue, mJodiDValue, mSinglePValue, mDoubleDValue, mTripleDValue, mHalfSValue, mFullSValue;
    private int activity=0;
    private ShapeableImageView images;;
    private MaterialButton mWatchYT;
    private MaterialTextView mHowToLarnText;
    private ProgressBar mProgressBar;
    private IntentFilter mIntentFilter;
    NetBroadCastClass broadCastClass;
    LinearLayout gameRatesLyt;
    LinearLayout howToPlayLyt;
    AppBarLayout appBarLayout;
    String videoLink="";

    List<MaterialTextView> mDigitValue = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rate_how_to_screen);
        intVariables();
        LoadDataMethod();
    }

    private void intVariables() {
        appBarLayout = findViewById(R.id.appbarLayout);
        mToolbar = appBarLayout.findViewById(R.id.toolbar);
        gameRatesLyt = findViewById(R.id.gameRatesLyt);
        howToPlayLyt = findViewById(R.id.howToPlayLyt);
        mSingle = findViewById(R.id.sing_d);
        mSingleDValue = findViewById(R.id.singgg_d_v);
        mJodi = findViewById(R.id.jodiii_d);
        mJodiDValue = findViewById(R.id.jodiiii_d_value);
        mSingleP = findViewById(R.id.sing_p);
        mSinglePValue = findViewById(R.id.singgg_p_v);
        mDoubleD = findViewById(R.id.double_d);
        mDoubleDValue = findViewById(R.id.doubb_d_v);
        mTripleD = findViewById(R.id.triple_d);
        mTripleDValue = findViewById(R.id.tripop_d_v);
        mHalfS = findViewById(R.id.halfff_s);
        mHalfSValue = findViewById(R.id.halffff_s_v);
        mFullS = findViewById(R.id.fulllll_s);
        mFullSValue = findViewById(R.id.fulllll_s_v);
        mHowToLarnText = findViewById(R.id.howTOText);
        mProgressBar = findViewById(R.id.prgrss_bar);

        images = findViewById(R.id.card_icon);
        mWatchYT = findViewById(R.id.watcch_yt);

        MaterialTextView mDataConText = findViewById(R.id.internet_text);
        broadCastClass = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);

        activity = getIntent().getIntExtra("MainActivity", 0);
    }

    private void LoadDataMethod() {
        if (activity==1){
            mToolbar.setTitle("Game Rates");
            gameRatesLyt.setVisibility(View.VISIBLE);
            gameValuesMethod(GameRateHowToActivity.this);
        }else if (activity==2){
            mToolbar.setTitle("How to Play");
            howToPlayLyt.setVisibility(View.VISIBLE);
            getHowToLearnMethod(GameRateHowToActivity.this);

        }

        mDigitValue.add(mSingleDValue);
        mDigitValue.add(mJodiDValue);
        mDigitValue.add(mSinglePValue);
        mDigitValue.add(mDoubleDValue);
        mDigitValue.add(mTripleDValue);
        mDigitValue.add(mHalfSValue);
        mDigitValue.add(mFullSValue);

        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void gameValuesMethod(GameRateHowToActivity activity) {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<GameRateListModal> call = ApiClient.getClient().game_rate_list(SharedPreferenceData.getLogiiiinInToken(activity),"");
        call.enqueue(new Callback<GameRateListModal>() {
            @Override
            public void onResponse(Call<GameRateListModal> call, Response<GameRateListModal> response) {
                if (response.isSuccessful()){
                    GameRateListModal modelValue = response.body();
                    if (modelValue.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, modelValue.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if(modelValue.getStatus().equalsIgnoreCase("success")) {
                        List<GameRateListModal.Data> gameRateData = modelValue.getData();
                        for (int i = 0; i < gameRateData.size(); i++) {
                            String value = gameRateData.get(i).getCost_amount() + "-" + gameRateData.get(i).getEarning_amount();
                            mDigitValue.get(i).setText(value);
                        }
                    }
                    Toast.makeText(GameRateHowToActivity.this, modelValue.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GameRateListModal> call, Throwable t) {
                System.out.println("gameRateList Error "+t);
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getHowToLearnMethod(GameRateHowToActivity activity) {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<HowToPlayModal> call = ApiClient.getClient().how_to_play(SharedPreferenceData.getLogiiiinInToken(activity),"");
        call.enqueue(new Callback<HowToPlayModal>() {
            @Override
            public void onResponse(Call<HowToPlayModal> call, Response<HowToPlayModal> response) {
                if (response.isSuccessful()){
                    HowToPlayModal howToPlayModel = response.body();
                    if (howToPlayModel.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, howToPlayModel.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (howToPlayModel.getStatus().equals("success")){
                        mHowToLarnText.setText(howToPlayModel.getData().getHow_to_play());
                        videoLink = howToPlayModel.getData().getVideo_link();
                    }
                    Toast.makeText(GameRateHowToActivity.this, howToPlayModel.getMessage(), Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<HowToPlayModal> call, Throwable t) {
                System.out.println("howToPlay Error "+t);
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
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

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    public void WatchItOnYoutubeBtn(View view) {
        if (!Objects.equals(videoLink,"")){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoLink));
            startActivity(intent);
        }else Toast.makeText(this, "Youtube video not added yet", Toast.LENGTH_SHORT).show();
    }

}