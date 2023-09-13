package in.kal_777.kal_777_777.Activities;

import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Adapters.SLListAdapter;
import in.kal_777.kal_777_777.Modals.StarlineGameModel;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SLGameListActivity extends AppCompatActivity {

    private RecyclerView recSL;
    private SLListAdapter SLListAdapter;
    public  MaterialTextView sinDV, sinPV, dobPV, triPV;
    public  String stringURL = "";
    public  List<StarlineGameModel.Data.StarlineGame> starlineGameList = new ArrayList<>();
    public  List<StarlineGameModel.Data.StarlineRates> starlineRatesList;
    private Vibrator mVibe;
    private MaterialButton wHisBtn, bHisBtn,chartTableBtn;
    MaterialToolbar toolbar;
    private MaterialTextView mDataConText;
    private IntentFilter mIntentFilter;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sl_game_list_screen);
        intVariables();
        confRecycler();
        clickListeners();
        getGameList(SLGameListActivity.this);
        NetBroadCastClass broadCastClass = new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
    }
    private void intVariables() {
        starlineRatesList = new ArrayList<>();
        recSL = findViewById(R.id.a_recy_s_l);
        sinDV = findViewById(R.id.singgg_d_v);
        sinPV = findViewById(R.id.singgg_p_v);
        dobPV = findViewById(R.id.double_p_v);
        triPV = findViewById(R.id.triple_p_v);
        mVibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
        bHisBtn = findViewById(R.id.a_b_his_btn);
        wHisBtn = findViewById(R.id.a_w_his_btn);
        chartTableBtn = findViewById(R.id.a_chart_table_btn);
        refreshLayout = findViewById(R.id.mswipe_ref_lyt);
        mDataConText = findViewById(R.id.internet_text);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Starline Game");

    }
    private void clickListeners() {

        bHisBtn.setOnClickListener(v -> {
            Intent bidHistory = new Intent(this, WinningHistoryActivity.class);
            bidHistory.putExtra("History", 400);
            startActivity(bidHistory);
        });
        wHisBtn.setOnClickListener(v -> {
            Intent winHistory = new Intent(this, WinningHistoryActivity.class);
            winHistory.putExtra("History", 300);
            startActivity(winHistory);
        });
        chartTableBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("Chart", stringURL);
            startActivity(intent);
        });

        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
    }
    public void confRecycler() {
        SLListAdapter = new SLListAdapter(this, starlineGameList, (starlineGame, itemView) -> {
            if (!starlineGame.isPlay()){
                ObjectAnimator
                        .ofFloat(itemView, "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0)
                        .setDuration(700)
                        .start();
                mVibe.vibrate(500);
            }else {
                Intent intent = new Intent(this, SLGameTypeActivity.class);
                intent.putExtra("Game", starlineGame.getId());
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recSL.setLayoutManager(layoutManager);
        recSL.setAdapter(SLListAdapter);
    }
    private void getGameList(SLGameListActivity activity) {
        refreshLayout.setRefreshing(true);
        Call<StarlineGameModel> call = ApiClient.getClient().starline_game(SharedPreferenceData.getLogiiiinInToken(activity), "");
        call.enqueue(new Callback<StarlineGameModel>() {
            @Override
            public void onResponse(@NonNull Call<StarlineGameModel> call, @NonNull Response<StarlineGameModel> response) {
                if (response.isSuccessful()) {
                    StarlineGameModel starlineGameModel = response.body();
                    assert starlineGameModel != null;
                    if (starlineGameModel.getCode().equalsIgnoreCase("505")) {
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, starlineGameModel.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (starlineGameModel.getStatus().equalsIgnoreCase("success")) {
                        StarlineGameModel.Data data = starlineGameModel.getData();
                        stringURL = data.getStarlineChart();
                        starlineRatesList = data.getStarlineRates();
                        for (int i = 0; i < starlineRatesList.size(); i++) {
                            String value = starlineRatesList.get(i).getCost_amount() + "-" + starlineRatesList.get(i).getEarning_amount();
                            if(i==0)sinDV.setText(value);
                            if(i==1)sinPV.setText(value);
                            if(i==2)dobPV.setText(value);
                            if(i==3)triPV.setText(value);
                        }

                        starlineGameList = data.getStarlineGame();
                        confRecycler();
                    }
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<StarlineGameModel> call, @NonNull Throwable t) {
                refreshLayout.setRefreshing(false);
                System.out.println("game list Error "+t);
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}