package in.kal_777.kal_777_777.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Adapters.GDGameListAdapter;
import in.kal_777.kal_777_777.Modals.GaliGameList;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GDGameListActivity extends AppCompatActivity {
    private RecyclerView arecSL;
    public MaterialTextView asinDV, asinPV, adobPV;
    public String astringURL = "";
    public List<GaliGameList.Data.GaliDesawarGame> galiDesawarGameList = new ArrayList<>();
    public List<GaliGameList.Data.GalidesawarRates> galidesawarRates;
    private Vibrator aVibe;
    private MaterialButton awHisBtn, abHisBtn, achartTableBtn;
    private SwipeRefreshLayout arefreshLayout;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd_game_list_screen);
        intializeIDs();
        RecyclerMethod();
        clickLisssssssssssteners();
        TurnaList(GDGameListActivity.this);
    }
    private void intializeIDs() {
        galidesawarRates = new ArrayList<>();
        arecSL = findViewById(R.id.a_recy_s_l);
        asinDV = findViewById(R.id.a_left_d_v);
        asinPV = findViewById(R.id.a_right_d_v);
        adobPV = findViewById(R.id.a_jodi_d_v);
        aVibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
        abHisBtn = findViewById(R.id.a_b_his_btn);
        awHisBtn = findViewById(R.id.a_w_his_btn);
        achartTableBtn = findViewById(R.id.a_chart_table_btn);
        arefreshLayout = findViewById(R.id.mswipe_ref_lyt);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Galidesawar Game");
    }
    private void clickLisssssssssssteners() {

        abHisBtn.setOnClickListener(v -> {
            Intent bidHistory = new Intent(this, WinningHistoryActivity.class);
            bidHistory.putExtra("History", 500);
            startActivity(bidHistory);
        });
        awHisBtn.setOnClickListener(v -> {
            Intent winHistory = new Intent(this, WinningHistoryActivity.class);
            winHistory.putExtra("History", 600);
            startActivity(winHistory);
        });
        achartTableBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("Chart", astringURL);
            startActivity(intent);
        });
        arefreshLayout.setOnRefreshListener(() -> {
            TurnaList(GDGameListActivity.this);
        });

        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

    }
    public void RecyclerMethod() {
        GDGameListAdapter GDGameListAdapter = new GDGameListAdapter(this, galiDesawarGameList, (galiDesawarGame, itemView) -> {
            if (!galiDesawarGame.isPlay()) {
                ObjectAnimator
                        .ofFloat(itemView, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                        .setDuration(700)
                        .start();
                aVibe.vibrate(500);
            } else {
                Intent intent = new Intent(this, GDGameTypeActivity.class);
                intent.putExtra("game", galiDesawarGame.getId());
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        arecSL.setLayoutManager(layoutManager);
        arecSL.setAdapter(GDGameListAdapter);
    }

    private void TurnaList(Context activity) {
        arefreshLayout.setRefreshing(true);
        Call<GaliGameList> call = ApiClient.getClient().gali_disawar_game(SharedPreferenceData.getLogiiiinInToken(activity), "");
        call.enqueue(new Callback<GaliGameList>() {
            @Override
            public void onResponse(@NonNull Call<GaliGameList> call, @NonNull Response<GaliGameList> response) {
                if (response.isSuccessful()) {
                    GaliGameList modelDesawarList = response.body();
                    assert modelDesawarList != null;
                    if (modelDesawarList.getCode().equalsIgnoreCase("505")) {
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, modelDesawarList.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (modelDesawarList.getStatus().equalsIgnoreCase("success")) {
                        GaliGameList.Data data = modelDesawarList.getData();
                        astringURL = data.getGali_disawar_chart();
                        galidesawarRates = data.getGalidesawarRates();
                        for (int i = 0; i < galidesawarRates.size(); i++) {
                            String value = galidesawarRates.get(i).getCost_amount() + "-" + galidesawarRates.get(i).getEarning_amount();
                            if(i==0) asinDV.setText(value);
                            if(i==1) asinPV.setText(value);
                            if(i==2) adobPV.setText(value);
                        }

                        galiDesawarGameList = data.getGaliDesawarGame();
                        RecyclerMethod();
                    }
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                arefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<GaliGameList> call, @NonNull Throwable t) {
                arefreshLayout.setRefreshing(false);
                System.out.println("game list Error "+t);
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}