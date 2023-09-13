package in.kal_777.kal_777_777.Fragments;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Activities.AddCoinActivity;
import in.kal_777.kal_777_777.Activities.GDGameListActivity;
import in.kal_777.kal_777_777.Activities.HomeActivity;
import in.kal_777.kal_777_777.Activities.LoginActivity;
import in.kal_777.kal_777_777.Activities.MGGameTypeActivity;
import in.kal_777.kal_777_777.Activities.SLGameListActivity;
import in.kal_777.kal_777_777.Adapters.MGGameListAdapter;
import in.kal_777.kal_777_777.Adapters.SliderAdapterExample;
import in.kal_777.kal_777_777.Modals.AppDetailsModal;
import in.kal_777.kal_777_777.Modals.MainGameList;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    View view;
    private SwipeRefreshLayout refreshLayout;
    //FrameLayout stripLayout;
    private static RecyclerView recyView;
    private static in.kal_777.kal_777_777.Adapters.MGGameListAdapter MGGameListAdapter;
    public static List<MainGameList.Data> mDataList = new ArrayList<>();
    private static Vibrator mVibe;
    int mCurrentPage = 0;
    LinearLayout ll_play,ll_chart;
    long DELAY_MS = 1000;
    long PERIOD_MS = 2000;
    private  String images[];
    private SliderAdapterExample adapter;
    private ProgressBar mProgressBar;
    private SliderView sliderView;
    private MaterialCardView mbtn_starLine,mbtn_galiDesawar;
    MaterialTextView telegram,whatsappBtn;
    public  static MaterialTextView addFundText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        initIds();
        clickListeners();
        loadData();
        return view;
    }

    private void initIds() {

        refreshLayout= view.findViewById(R.id.mswipe_ref_lyt);
        recyView = view.findViewById(R.id.a_recyclerView);
        whatsappBtn = view.findViewById(R.id.rl_whatsapp);
        telegram = view.findViewById(R.id.telegram);
        mProgressBar = view.findViewById(R.id.prgrss_bar);
        mbtn_starLine = view.findViewById(R.id.mbtn_starLine);
        mbtn_galiDesawar = view.findViewById(R.id.mbtn_galiDesawar);
        addFundText = view.findViewById(R.id.addFundText);

        sliderView=view.findViewById(R.id.main_slider);
        getAppInfoMethod(getContext());
        getGameListMethod(getContext());
    }
    private void clickListeners() {
        refreshLayout.setOnRefreshListener(() -> {
            if (InternetService.isOnline(requireContext())){
                getAppInfoMethod(getContext());
                getGameListMethod(getContext());
                HomeActivity.checkUserStatusMethod(getContext());
            }
            else Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        });
        whatsappBtn.setOnClickListener(v -> {
           /* String msg = "Hello Sir\nMy Name : " +
                    SharedPreferenceData.getPrfrnnnnnceinfo(getContext(), SharedPreferenceData.USER_NAME_KEY) +
                    "\nMy Number : " +
                    SharedPreferenceData.getPrfrnnnnnceinfo(getContext(), SharedPreferenceData.PHONE_NUMBER_KEY);*/

            String url = "https://api.whatsapp.com/send?phone="+SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.WHATSAP_NUMBER_KEY)+"&text="+"";
            Intent i = new Intent(Intent.ACTION_VIEW);

            i.setData(Uri.parse(url));
            startActivity(i);
        });

        /*callBtn.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.CALL_PHONE}, 100);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" +SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.PHONE_NUMBER1_KEY)));
                startActivity(callIntent);
            }
        });*/
        telegram.setOnClickListener(v-> {
                    Intent intent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(SharedPreferenceData.getContaaaaaactObject(getContext(),
                                    SharedPreferenceData.TELEGRAM_KEY)));
                    startActivity(intent);
                });
        mbtn_starLine.setOnClickListener(view -> {
            Intent starlineIntent = new Intent(getContext(), SLGameListActivity.class);
            startActivity(starlineIntent);
        });
        mbtn_galiDesawar.setOnClickListener(view -> {
            Intent galidesawar = new Intent(getContext(), GDGameListActivity.class);
            startActivity(galidesawar);
        });
        addFundText.setOnClickListener(v -> addFund());

    }

    private void getGameListMethod(Context activity) {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<MainGameList> call = ApiClient.getClient().main_game_list(SharedPreferenceData.getLogiiiinInToken(activity), "");
        call.enqueue(new Callback<MainGameList>() {
            @Override
            public void onResponse(@NonNull Call<MainGameList> call, @NonNull Response<MainGameList> response) {
                if (response.isSuccessful()){
                    MainGameList mainGameList = response.body();
                    assert mainGameList != null;
                    if (mainGameList.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, mainGameList.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                    }
                    if(mainGameList.getStatus().equalsIgnoreCase("success")){
                        mDataList = mainGameList.getData();
                        confRecyView();
                    }

                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<MainGameList> call, @NonNull Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                System.out.println("game list Error "+t);
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void loadData() {
       /* marqueText.setText(SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.MAR_TXT_KEY));
        marqueText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        marqueText.setSelected(true);
        marqueText.setSingleLine(true);
        marqueText.setMarqueeRepeatLimit(-1);*/
        mVibe = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);
        images = new String[]{SharedPreferenceData.getPosteeeeerImage(getContext(), SharedPreferenceData.POSTER_IMAGES1_KEY),
                SharedPreferenceData.getPosteeeeerImage(getContext(), SharedPreferenceData.POSTER_IMAGES2_KEY),
                SharedPreferenceData.getPosteeeeerImage(getContext(), SharedPreferenceData.POSTER_IMAGES3_KEY)};
        //  images = new String[]{"https://searchnplay.in/sm_matka/uploads/setting/7518e9fc0dfc0f0349b0f6498638ac56.png","https://searchnplay.in/sm_matka/uploads/setting/6d1a010e5aab1690744ac387f87b390f.png", "https://searchnplay.in/sm_matka/uploads/setting/9f08110968179c58c0914209e7c952a4.png"};
        adapter=new SliderAdapterExample(images, getContext());
        sliderView.setSliderAdapter(adapter);
        sliderView.startAutoCycle();
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
    }

    public void confRecyView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyView.setLayoutManager(layoutManager);
        MGGameListAdapter = new MGGameListAdapter(getContext(), (ArrayList<MainGameList.Data>) mDataList, (data, itemView) -> {
            if (!data.isPlay()){
                ObjectAnimator
                        .ofFloat(itemView, "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0)
                        .setDuration(700)
                        .start();
                mVibe.vibrate(500);
            }else {
                Intent intent = new Intent(getContext(), MGGameTypeActivity.class);
                intent.putExtra("gameName", data.getName());
                intent.putExtra("game", data.getId());
                intent.putExtra("open",data.isOpen());
                startActivity(intent);
            }
        });
        recyView.setAdapter(MGGameListAdapter);
    }
    private void getAppInfoMethod(Context activity) {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<AppDetailsModal> call = ApiClient.getClient().app_details("");
        call.enqueue(new Callback<AppDetailsModal>() {
            @Override
            public void onResponse(@NonNull Call<AppDetailsModal> call, @NonNull Response<AppDetailsModal> response) {
                if (response.isSuccessful()){
                    AppDetailsModal modelAppInfo = response.body();
                    if (modelAppInfo.getStatus().equalsIgnoreCase("success")){
                        SharedPreferenceData.setPrefrenccccrrrrreStrngData(activity, SharedPreferenceData.MAR_TXT_KEY, modelAppInfo.getData().getBanner_marquee());
                        SharedPreferenceData.setContaaaaactUsInfo(activity, SharedPreferenceData.PHONE_NUMBER1_KEY,"+91"+ modelAppInfo.getData().getContact_details().getMobile_no_1());
                        SharedPreferenceData.setContaaaaactUsInfo(activity, SharedPreferenceData.PHONE_NUMBER2_KEY,"+91"+ modelAppInfo.getData().getContact_details().getMobile_no_2());
                        SharedPreferenceData.setContaaaaactUsInfo(activity, SharedPreferenceData.WHATSAP_NUMBER_KEY,"+91"+ modelAppInfo.getData().getContact_details().getWhatsapp_no());
                        SharedPreferenceData.setContaaaaactUsInfo(activity, SharedPreferenceData.REACH_US_EMAIL_KEY, modelAppInfo.getData().getContact_details().getEmail_1());
                        SharedPreferenceData.setContaaaaactUsInfo(activity, SharedPreferenceData.TELEGRAM_KEY, modelAppInfo.getData().getContact_details().getTelegram_no());
                        SharedPreferenceData.setPosteeeeeerrImages(activity, SharedPreferenceData.POSTER_IMAGES1_KEY, modelAppInfo.getData().getBanner_image().getBanner_img_1());
                        SharedPreferenceData.setPosteeeeeerrImages(activity, SharedPreferenceData.POSTER_IMAGES2_KEY, modelAppInfo.getData().getBanner_image().getBanner_img_2());
                        SharedPreferenceData.setPosteeeeeerrImages(activity, SharedPreferenceData.POSTER_IMAGES3_KEY, modelAppInfo.getData().getBanner_image().getBanner_img_3());

                        SharedPreferenceData.setString(activity, SharedPreferenceData.WITHDRAW_OPEN_TIME, modelAppInfo.getData().getWithdraw_open_time());
                        SharedPreferenceData.setString(activity, SharedPreferenceData.WITHDRAW_CLOSE_TIME, modelAppInfo.getData().getWithdraw_close_time());
                        SharedPreferenceData.setString(activity, SharedPreferenceData.APP_LINK, modelAppInfo.getData().getApp_link());
                        SharedPreferenceData.setString(activity, SharedPreferenceData.ADD_FUND_NOTICE, modelAppInfo.getData().getAdd_fund_notice());
                        SharedPreferenceData.setString(activity, SharedPreferenceData.WITHDRAW_NOTICE, modelAppInfo.getData().getWithdraw_notice());
                        SharedPreferenceData.setString(activity, SharedPreferenceData.APP_NOTICE, modelAppInfo.getData().getApp_notice());
                        SharedPreferenceData.setString(activity, SharedPreferenceData.SHARE_MESSAGE, modelAppInfo.getData().getShare_message());
                        images = new String[]{modelAppInfo.getData().getBanner_image().getBanner_img_1(),modelAppInfo.getData().getBanner_image().getBanner_img_2(),modelAppInfo.getData().getBanner_image().getBanner_img_3()};
                        adapter=new SliderAdapterExample(images, activity);
                        sliderView.setSliderAdapter(adapter);
                        sliderView.startAutoCycle();
                        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);

                    }else
                        Toast.makeText(getContext(), modelAppInfo.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<AppDetailsModal> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                System.out.println("getAppDetails error "+t);
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void addFund() {
        Intent starlineIntent = new Intent(getContext(), AddCoinActivity.class);
        startActivity(starlineIntent);
    }
}