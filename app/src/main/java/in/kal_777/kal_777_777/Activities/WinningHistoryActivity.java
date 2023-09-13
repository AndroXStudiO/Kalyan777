package in.kal_777.kal_777_777.Activities;


import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Adapters.GDHistoryAdapter;
import in.kal_777.kal_777_777.Adapters.SLWonHistoryAdapter;
import in.kal_777.kal_777_777.Adapters.WonHistoryAdapter;
import in.kal_777.kal_777_777.Modals.GaliWinModal;
import in.kal_777.kal_777_777.Modals.StarlineWinModal;
import in.kal_777.kal_777_777.Modals.WinHistoryModal;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WinningHistoryActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextInputEditText from_date, to_date;
    private Date f_date, t_date;
    private final SimpleDateFormat userSF = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private final SimpleDateFormat serverSF = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
    private final Calendar fromCalender = Calendar.getInstance();
    private final Calendar toCalendar = Calendar.getInstance();
    private final Calendar todayCalwndar = Calendar.getInstance();
    private ShapeableImageView emptyImage;

    private int his =0;
    private RecyclerView recyclerView;
    private WonHistoryAdapter wonHistoryAdapter;
    private SLWonHistoryAdapter SLWonHistoryAdapter;
    private GDHistoryAdapter disaWonHistoryAdapter;
    private List<WinHistoryModal.Data> dataArrayList = new ArrayList<>();
    private List<StarlineWinModal.Data> slWonModelList = new ArrayList<>();
    private List<GaliWinModal.Data> disaWonModelList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private Call<WinHistoryModal> call;
    private Call<StarlineWinModal> sLCall;
    private Call<GaliWinModal> disaCall;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning_history_screen);
        intVariables();
        loadData();
        toolbarMethod();
    }

    private void loadData() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);

        f_date = Calendar.getInstance().getTime();
        t_date = Calendar.getInstance().getTime();
        from_date.setText(userSF.format(f_date));
        to_date.setText(userSF.format(t_date));
        his = getIntent().getIntExtra("History", 0);
        if (his ==100 || his ==200){
            historyMethod(WinningHistoryActivity.this, f_date, t_date);
        }
        if (his ==300 || his ==400){
            winHistoryHistoryMethod(WinningHistoryActivity.this, f_date, t_date);
        }
        if (his==500|| his==600){
            desawarHistoryMethod(this, f_date, t_date);
        }
    }


    private void intVariables() {
        AppBarLayout appBarLayout = findViewById(R.id.appbarLayout);
        toolbar = appBarLayout.findViewById(R.id.toolbar);
        from_date = findViewById(R.id.from_date);
        to_date = findViewById(R.id.to_date);
        recyclerView = findViewById(R.id.a_recyclerView);
        emptyImage = findViewById(R.id.aempty_image);
        swipeRefreshLayout = findViewById(R.id.mswipe_ref_lyt);

        MaterialTextView dataConText = findViewById(R.id.internet_text);
        NetBroadCastClass broadCastClass = new NetBroadCastClass(dataConText);

    }
    DatePickerDialog.OnDateSetListener toDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            toCalendar.set(Calendar.YEAR, year);
            toCalendar.set(Calendar.MONTH, monthOfYear);
            toCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            if(toCalendar.getTimeInMillis()< fromCalender.getTimeInMillis()){
                Toast.makeText(WinningHistoryActivity.this, "To Date can't be smaller then From Date", Toast.LENGTH_SHORT).show();
                return;
            }
            t_date = toCalendar.getTime();
            to_date.setText(userSF.format(t_date));
        }
    };

    DatePickerDialog.OnDateSetListener fromDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            fromCalender.set(Calendar.YEAR, year);
            fromCalender.set(Calendar.MONTH, monthOfYear);
            fromCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            f_date = fromCalender.getTime();
            from_date.setText(userSF.format(f_date));
        }
    };

    private void toolbarMethod() {
        if (his ==100 ||his ==300||his==500){
            toolbar.setTitle("Win History");
        }else {
            toolbar.setTitle("Bid History");
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (his ==100 || his ==200){
                historyMethod(WinningHistoryActivity.this, f_date, t_date);
            }
            if (his ==300 || his ==400){
                winHistoryHistoryMethod(WinningHistoryActivity.this, f_date, t_date);
            }
            if (his==500|| his==600){
                desawarHistoryMethod(WinningHistoryActivity.this, f_date, t_date);
            }
        });
    }
    public void fromDate(View view) {
        DatePickerDialog datePickerDialog=  new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Panel, fromDatePicker, fromCalender
                .get(Calendar.YEAR), fromCalender.get(Calendar.MONTH), fromCalender.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        long maxDate = todayCalwndar.getTime().getTime() ;
        datePickerDialog.getDatePicker().setMaxDate(maxDate);
        datePickerDialog.setCanceledOnTouchOutside(true);
        datePickerDialog.getDatePicker().setBackgroundColor(getResources().getColor(R.color.white));
        datePickerDialog.show();
    }

    public void toDate(View view) {
        DatePickerDialog datePickerDialog=  new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Panel, toDatePicker, toCalendar
                .get(Calendar.YEAR), toCalendar.get(Calendar.MONTH), toCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


        long maxDate = todayCalwndar.getTime().getTime() ;
        datePickerDialog.getDatePicker().setMaxDate(maxDate);
        datePickerDialog.setCanceledOnTouchOutside(true);
        datePickerDialog.getDatePicker().setBackgroundColor(getResources().getColor(R.color.white));
        datePickerDialog.show();
    }



    public void submitWinHistory(View view) {

        if (his ==100 || his ==200){
            historyMethod(WinningHistoryActivity.this, f_date, t_date);
        }
        if (his ==300 || his ==400){
            winHistoryHistoryMethod(WinningHistoryActivity.this, f_date, t_date);
        }
        if (his==500|| his==600){
            desawarHistoryMethod(this, f_date, t_date);
        }
    }

    private void desawarHistoryMethod(WinningHistoryActivity activity, Date fDate, Date tDate) {
        String fromDate = serverSF.format(fDate) + " 00:00:00";
        String toDate = serverSF.format(tDate) + " 23:59:59";
        swipeRefreshLayout.setRefreshing(true);
        switch (his){
            case 500:
                disaCall = ApiClient.getClient().gali_disawar_bid_history(SharedPreferenceData.getLogiiiinInToken(activity),fromDate, toDate);
                break;
            case 600:
                disaCall = ApiClient.getClient().gali_disawar_win_history(SharedPreferenceData.getLogiiiinInToken(activity),fromDate, toDate);
                break;
        }

        disaCall.enqueue(new Callback<GaliWinModal>() {
            @Override
            public void onResponse(@NonNull Call<GaliWinModal> call, @NonNull Response<GaliWinModal> response) {
                if (response.isSuccessful()){
                    GaliWinModal GaliWinModal = response.body();
                    if (GaliWinModal.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, GaliWinModal.getMsg(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (GaliWinModal.getStatus().equalsIgnoreCase("success")){
                        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
                        disaWonModelList = GaliWinModal.getData();

                        recyclerView.setLayoutManager(layoutManager);
                        disaWonHistoryAdapter = new GDHistoryAdapter(activity, disaWonModelList);
                        recyclerView.setAdapter(disaWonHistoryAdapter);
                        emptyImage.setVisibility(View.GONE);

                    }else {
                        recyclerView.setVisibility(View.GONE);
                        emptyImage.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(activity, GaliWinModal.getMsg(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<GaliWinModal> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("bidHistory error "+t);
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void winHistoryHistoryMethod(WinningHistoryActivity activity, Date fDate, Date tDate) {
        String fromDate = serverSF.format(fDate) + " 00:00:00";
        String toDate = serverSF.format(tDate) + " 23:59:59";

        swipeRefreshLayout.setRefreshing(true);
        switch (his){
            case 300:
                sLCall = ApiClient.getClient().starline_win_history(SharedPreferenceData.getLogiiiinInToken(activity),fromDate, toDate);
                break;
            case 400:
                sLCall = ApiClient.getClient().starline_bid_history(SharedPreferenceData.getLogiiiinInToken(activity),fromDate, toDate);
                break;
        }

        sLCall.enqueue(new Callback<StarlineWinModal>() {
            @Override
            public void onResponse(@NonNull Call<StarlineWinModal> call, @NonNull Response<StarlineWinModal> response) {
                if (response.isSuccessful()){
                    StarlineWinModal winModal = response.body();
                    if (winModal.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, winModal.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (winModal.getStatus().equalsIgnoreCase("success")){
                        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);

                        slWonModelList = winModal.getData();
                        recyclerView.setLayoutManager(layoutManager);
                        SLWonHistoryAdapter = new SLWonHistoryAdapter(activity, slWonModelList);
                        recyclerView.setAdapter(SLWonHistoryAdapter);
                        emptyImage.setVisibility(View.GONE);
                    }else {
                        emptyImage.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(activity, winModal.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<StarlineWinModal> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("bidHistory error "+t);
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void historyMethod(WinningHistoryActivity activity, Date fDate, Date tDate) {
        String fromDate = serverSF.format(fDate) + " 00:00:00";
        String toDate = serverSF.format(tDate) + " 23:59:59";
        swipeRefreshLayout.setRefreshing(true);
        switch (his){
            case 100:
                call = ApiClient.getClient().win_history(SharedPreferenceData.getLogiiiinInToken(activity),fromDate, toDate);
                break;
            case 200:
                call = ApiClient.getClient().bid_history(SharedPreferenceData.getLogiiiinInToken(activity),fromDate, toDate);
                break;
        }

        call.enqueue(new Callback<WinHistoryModal>() {
            @Override
            public void onResponse(@NonNull Call<WinHistoryModal> call, @NonNull Response<WinHistoryModal> response) {
                if (response.isSuccessful()){
                    WinHistoryModal winHistoryModal = response.body();
                    if (winHistoryModal.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, winHistoryModal.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    System.out.println("winModel.getStatus() "+ winHistoryModal.getStatus());
                    if (winHistoryModal.getStatus().equalsIgnoreCase("success")){
                        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);

                        dataArrayList = winHistoryModal.getData();
                        recyclerView.setLayoutManager(layoutManager);
                        wonHistoryAdapter = new WonHistoryAdapter(activity, dataArrayList);
                        recyclerView.setAdapter(wonHistoryAdapter);
                        emptyImage.setVisibility(View.GONE);
                    }else {
                        emptyImage.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(activity, winHistoryModal.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<WinHistoryModal> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("bidHistory error "+t);
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();

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
}