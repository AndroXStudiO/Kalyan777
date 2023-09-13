package in.kal_777.kal_777_777.Activities;

import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

import in.kal_777.kal_777_777.BuildConfig;
import in.kal_777.kal_777_777.Fragments.ContactFragment;
import in.kal_777.kal_777_777.Fragments.HomeFragment;
import in.kal_777.kal_777_777.Fragments.ProfileFragment;
import in.kal_777.kal_777_777.Fragments.WalletFragment;
import in.kal_777.kal_777_777.R;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Modals.LoginModal;
import in.kal_777.kal_777_777.Modals.UserStatus;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    static MaterialToolbar mToolbar;
    private NavigationView mNaviView;
    private DrawerLayout mDrawerLayout;
    public static MaterialTextView userName, mMobileNum;
    public static MenuItem  howToLearn, mGameValues, contactUs,changePassword, logout,aWalletStatement,BidHstry,winHstry;

    public static int mAvaPoints =0;
    private SwitchMaterial mNotiSwitchBtn;
    private MaterialTextView mDataConText;
    public static MaterialTextView toolbarMenu, addFundText;
    private IntentFilter mIntentFilter;

    public static MaterialTextView /*marqueText*/ user_name;
    LinearLayout profileBottom, shareBottom,ratingBottom, contactBottom;
    static FragmentManager fragmentManager;
    ShapeableImageView homeBottom;


    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        intVariables();
        confToolbar();
        confiData();
        clickListener();
        toolbarMenu.setOnClickListener(v->{
            Fragment walletFragment = new WalletFragment();
            changeNavigation(walletFragment, "MY WALLET");
        });

        new NetBroadCastClass(mDataConText);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);
    }

    private void clickListener() {

        mNotiSwitchBtn.setOnCheckedChangeListener((buttonView, isChecked) -> SharedPreferenceData.setBiiiinalData(HomeActivity.this, SharedPreferenceData.FIREBSE_ALLOW_KEY, isChecked));

        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId()==R.id.menuChips){
                Fragment walletFragment = new WalletFragment();
                changeNavigation(walletFragment, "MY WALLET");
            }
            return false;
        });

        Fragment fragment = new HomeFragment();
        changeNavigation(fragment, getString(R.string.app_name));

        profileBottom.setOnClickListener(v -> {
            Fragment profileFragment = new ProfileFragment();
            changeNavigation(profileFragment, "My Profile");
        });
        shareBottom.setOnClickListener(v -> {
            String url = Objects.equals(SharedPreferenceData.getString(this, SharedPreferenceData.APP_LINK), "") ?"https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID:SharedPreferenceData.getString(this, SharedPreferenceData.APP_LINK);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    SharedPreferenceData.getString(this, SharedPreferenceData.SHARE_MESSAGE)+"\n"+url);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });
        homeBottom.setOnClickListener(v -> {
            Fragment homeFragment = new HomeFragment();
            changeNavigation(homeFragment, getString(R.string.app_name));
        });
        ratingBottom.setOnClickListener(v -> {
            String url = Objects.equals(SharedPreferenceData.getString(this, SharedPreferenceData.APP_LINK), "") ?"https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID:SharedPreferenceData.getString(this, SharedPreferenceData.APP_LINK);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
        contactBottom.setOnClickListener(v -> {
            Fragment contactFragment = new ContactFragment();
            changeNavigation(contactFragment, "My Contact");
        });
    }

    private void changeNavigation(Fragment fragment, String title) {
        mToolbar.setTitle(title);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.hostFragment, fragment);
        fragmentTransaction.addToBackStack(null); // Add this line if you want to add the fragment to the back stack
        fragmentTransaction.commit();
    }
    public static  void changeNavigation2(Fragment fragment, String title) {
        mToolbar.setTitle(title);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.hostFragment2, fragment);
        fragmentTransaction.addToBackStack(null); // Add this line if you want to add the fragment to the back stack
        fragmentTransaction.commit();
    }
    private void confiData() {
        if (InternetService.isOnline(this)){
            getUserInfoMethod(HomeActivity.this, SharedPreferenceData.getLogiiiinInToken(HomeActivity.this));
        }
        else Toast.makeText(this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }

    private void intVariables() {
        mDrawerLayout = findViewById(R.id.aDrwerLayout);
        AppBarLayout appBarLayout = findViewById(R.id.appbarLayout);
        mToolbar = appBarLayout.findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        mToolbar.setTitleCentered(true);
        mNaviView = findViewById(R.id.navigationView);
        mDataConText = findViewById(R.id.internet_text);
        profileBottom = findViewById(R.id.profileBottom);
        shareBottom = findViewById(R.id.shareBottom);
        ratingBottom = findViewById(R.id.ratingBottom);
        contactBottom = findViewById(R.id.contactBottom);
        homeBottom = findViewById(R.id.homeBottom);
        mNaviView.setItemIconTintList(null);
        userName = mNaviView.getHeaderView(0).findViewById(R.id.clientDName);
        mMobileNum = mNaviView.getHeaderView(0).findViewById(R.id.phn_nav_num);
        mNotiSwitchBtn = mNaviView.getHeaderView(0).findViewById(R.id.anotiSwitchBtn);

        toolbarMenu = appBarLayout.findViewById(R.id.toolbarPoints);
        howToLearn = mNaviView.getMenu().findItem(R.id.way_to_learn);
        aWalletStatement = mNaviView.getMenu().findItem(R.id.aWalletStatement);
        winHstry = mNaviView.getMenu().findItem(R.id.winHstry);
        BidHstry = mNaviView.getMenu().findItem(R.id.BidHstry);
        mGameValues = mNaviView.getMenu().findItem(R.id.b_games_value);
        userName.setText(SharedPreferenceData.getRegistrrrrrrttationObject(this, SharedPreferenceData.USER_NAME_KEY));
        mMobileNum.setText(SharedPreferenceData.getRegistrrrrrrttationObject(this, SharedPreferenceData.PHONE_NUMBER_KEY));
        mNotiSwitchBtn.setChecked(SharedPreferenceData.getBinallllObject(this, SharedPreferenceData.FIREBSE_ALLOW_KEY,true));

        contactUs = mNaviView.getMenu().findItem(R.id.aContactUs);
        changePassword = mNaviView.getMenu().findItem(R.id.ChngePasswrd);
        logout = mNaviView.getMenu().findItem(R.id.usrSignup);

        fragmentManager = getSupportFragmentManager();

      /*  if (SharedPreferenceData.getSharedBooleanStatus(this, SharedPreferenceData.KEY_DEVELOPER_MODE)){
            mToolbar.setTitleCentered(true);
            mToolbar.setTitle(getString(R.string.app_name));
            profile.setVisible(false);
            contactUs.setVisible(false);
            changePassword.setVisible(false);
            logout.setTitle("Exit App");
            userName.setVisibility(View.GONE);
            mMobileNum.setVisibility(View.GONE);
        }else {
            mToolbar.setTitleCentered(false);
            profile.setVisible(true);
            contactUs.setVisible(true);
            changePassword.setVisible(true);
            logout.setTitle("Logout");
            userName.setVisibility(View.VISIBLE);
            mMobileNum.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, mIntentFilter);
        checkUserStatusMethod(this);
    }

    public static void checkUserStatusMethod(Context activity) {
        Call<UserStatus> call = ApiClient.getClient().user_status(SharedPreferenceData.getLogiiiinInToken(activity),"");
        call.enqueue(new Callback<UserStatus>() {
            @Override
            public void onResponse(@NonNull Call<UserStatus> call, @NonNull Response<UserStatus> response) {
                if (response.isSuccessful()){
                    UserStatus userStatus = response.body();

                    if (userStatus.getStatus().equalsIgnoreCase("success")){
                        SharedPreferenceData.setUseeeeerCoins(activity, userStatus.getData().getAvailablePoints());
                        SharedPreferenceData.setTrannnnnnssssmitCoins(activity, userStatus.getData().getTransferPoint().equalsIgnoreCase("1"));
                        SharedPreferenceData.setAddAmmmmmmountUPI(activity, SharedPreferenceData.ADD_COINS_BHIM_ID_KEY, userStatus.getData().getUpiPaymentId());
                        SharedPreferenceData.setAddAmmmmmmountUPI(activity, SharedPreferenceData.ADD_COINS_BHIM_NAME_KEY, userStatus.getData().getUpiName());
                        SharedPreferenceData.setMaxMiiiiinnData(activity, SharedPreferenceData.MAX_ADD_AMOUNT_COINS_KEY, userStatus.getData().getMaximumDeposit());
                        SharedPreferenceData.setMaxMiiiiinnData(activity, SharedPreferenceData.MIN_ADD_AMOUNT_COINS_KEY, userStatus.getData().getMinimumDeposit());
                        SharedPreferenceData.setMaxMiiiiinnData(activity, SharedPreferenceData.MAX_EXTRACT_COINS_KEY, userStatus.getData().getMaximumWithdraw());
                        SharedPreferenceData.setMaxMiiiiinnData(activity, SharedPreferenceData.MIN_EXTRACT_COINS_KEY, userStatus.getData().getMinimumWithdraw());
                        SharedPreferenceData.setMaxMiiiiinnData(activity, SharedPreferenceData.MAX_OFFER_SUM_KEY, userStatus.getData().getMaximumBidAmount());
                        SharedPreferenceData.setMaxMiiiiinnData(activity, SharedPreferenceData.MIN_OFFER_SUM_KEY, userStatus.getData().getMinimumBidAmount());
                        SharedPreferenceData.setMaxMiiiiinnData(activity, SharedPreferenceData.MAX_TRANSMIT_COINS_KEY, userStatus.getData().getMaximumTransfer());
                        SharedPreferenceData.setMaxMiiiiinnData(activity, SharedPreferenceData.MIN_TRANSMIT_COINS_KEY, userStatus.getData().getMinimumTransfer());
                        mAvaPoints = Integer.parseInt(userStatus.getData().getAvailablePoints());
                        toolbarMenu.setText(String.valueOf(mAvaPoints));
                        //mMinWithdCoins.setText("Minimum withdrawal points are - "+userStatus.getData().getMinimumWithdraw());
                    }
                    SharedPreferenceData.setLiveeeeeeUser(activity, userStatus.getStatus().equalsIgnoreCase("success"));
                    updateUserStatus(activity);

                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserStatus> call, Throwable t) {
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                System.out.println("user_status error "+ t);
            }
        });
    }
    public static void updateUserStatus(Context context) {
        if (!SharedPreferenceData.getLiveeeeUser(context)){
            toolbarMenu.setVisibility(View.GONE);
            howToLearn.setVisible(false);
            contactUs.setVisible(false);
            aWalletStatement.setVisible(false);
            BidHstry.setVisible(false);
            winHstry.setVisible(false);
            toolbarMenu.setVisibility(View.GONE);
            HomeFragment.addFundText.setVisibility(View.GONE);
//            mbtn_starLine.setVisibility(View.GONE);
//            mbtn_galiDesawar.setVisibility(View.GONE);
            mGameValues.setVisible(false);
        }else{
            toolbarMenu.setVisibility(View.VISIBLE);
            howToLearn.setVisible(true);
            mGameValues.setVisible(true);
            contactUs.setVisible(true);
            winHstry.setVisible(true);
            BidHstry.setVisible(true);
            aWalletStatement.setVisible(true);
            toolbarMenu.setVisibility(View.VISIBLE);
            HomeFragment.addFundText.setVisibility(View.VISIBLE);
//            mbtn_starLine.setVisibility(View.VISIBLE);
//            mbtn_galiDesawar.setVisibility(View.GONE);
        }
    }

    private void confToolbar() {
        mToolbar.setNavigationOnClickListener(v -> mDrawerLayout.openDrawer(GravityCompat.START));
        mNaviView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    Fragment homeFragment = new HomeFragment();
                    changeNavigation(homeFragment, getString(R.string.app_name));
                    break;
                case R.id.aWalletStatement:
                    Fragment walletFragment = new WalletFragment();
                    changeNavigation(walletFragment, "MY WALLET");
                    break;
                case R.id.winHstry:
                    Intent winHistory = new Intent(HomeActivity.this, WinningHistoryActivity.class);
                    winHistory.putExtra("History", 100);
                    startActivity(winHistory);
                    break;
                case R.id.BidHstry:
                    Intent bidHistory = new Intent(HomeActivity.this, WinningHistoryActivity.class);
                    bidHistory.putExtra("History",200);
                    startActivity(bidHistory);
                    break;
                case R.id.b_games_value:
                    Intent gameRates = new Intent(HomeActivity.this, GameRateHowToActivity.class);
                    gameRates.putExtra("MainActivity", 1);
                    startActivity(gameRates);
                    break;
                case R.id.way_to_learn:
                    Intent howToPlay = new Intent(HomeActivity.this, GameRateHowToActivity.class);
                    howToPlay.putExtra("MainActivity", 2);
                    startActivity(howToPlay);
                    break;
                case R.id.aContactUs:
                    Fragment contactFragment = new ContactFragment();
                    changeNavigation(contactFragment, getString(R.string.app_name));
                    break;
                case R.id.a_shreWithFriends:
                    String urlShare = Objects.equals(SharedPreferenceData.getString(this, SharedPreferenceData.APP_LINK), "") ?"https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID:SharedPreferenceData.getString(this, SharedPreferenceData.APP_LINK);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            SharedPreferenceData.getString(this, SharedPreferenceData.SHARE_MESSAGE)+"\n"+urlShare);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    break;
                case R.id.RteApp:
                    String url = Objects.equals(SharedPreferenceData.getString(this, SharedPreferenceData.APP_LINK), "") ?"https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID:SharedPreferenceData.getString(this, SharedPreferenceData.APP_LINK);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    break;
                case R.id.ChngePasswrd:
                    Intent changePassword = new Intent(HomeActivity.this, ChangePasswordActivity.class);
                    changePassword.putExtra("from", "home");
                    changePassword.putExtra("number", SharedPreferenceData.getRegistrrrrrrttationObject(HomeActivity.this, SharedPreferenceData.PHONE_NUMBER_KEY));
                    startActivity(changePassword);
                    break;
                case R.id.usrSignup:
                    LogOutDialog();
                    mDrawerLayout.closeDrawers();
                    break;
            }
            mDrawerLayout.closeDrawers();
            return true;
        });
    }
    private void LogOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit Application");
        builder.setMessage("Are you sure you want to exit?");
        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent logOut= new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(logOut);
                SharedPreferenceData.setSigniiiiinnnSuccess(HomeActivity.this, false);
            }
        });
        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTypeface(Typeface.DEFAULT_BOLD);
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTypeface(Typeface.DEFAULT_BOLD);

        alertDialog.getWindow().setLayout(900, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    private void getUserInfoMethod(HomeActivity activity, String token) {
        Call<LoginModal> call = ApiClient.getClient().get_user_details(token,"");
        call.enqueue(new Callback<LoginModal>() {
            @Override
            public void onResponse(Call<LoginModal> call, Response<LoginModal> response) {
                if (response.isSuccessful()){
                    LoginModal loginModal = response.body();
                    if (loginModal.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, loginModal.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (loginModal.getStatus().equalsIgnoreCase("success")){
                        SharedPreferenceData.setRegisssstttterData(activity, SharedPreferenceData.USER_NAME_KEY, loginModal.getData().getUsername());
                        SharedPreferenceData.setRegisssstttterData(activity, SharedPreferenceData.PHONE_NUMBER_KEY, loginModal.getData().getMobile());
                        SharedPreferenceData.setPrefrenccccrrrrreStrngData(activity, SharedPreferenceData.CLIENT_EMAIL_KEY, loginModal.getData().getEmail());
                        SharedPreferenceData.setBankInnnttformation(activity, SharedPreferenceData.BANK_USER_NAME_KEY, loginModal.getData().getAccount_holder_name());
                        SharedPreferenceData.setBankInnnttformation(activity, SharedPreferenceData.B_AC_N_KEY, loginModal.getData().getBank_account_no());
                        SharedPreferenceData.setBankInnnttformation(activity, SharedPreferenceData.B_IFSC_C_KEY, loginModal.getData().getIfsc_code());
                        SharedPreferenceData.setBankInnnttformation(activity, SharedPreferenceData.B_N_KEY, loginModal.getData().getBank_name());
                        SharedPreferenceData.setBankInnnttformation(activity, SharedPreferenceData.BRANCH_ADDRESS_KEY, loginModal.getData().getBranch_address());
                        SharedPreferenceData.setPrefrenccccrrrrreStrngData(activity, SharedPreferenceData.P_UPI_ID_KEY, loginModal.getData().getPaytm_mobile_no());
                        SharedPreferenceData.setPrefrenccccrrrrreStrngData(activity, SharedPreferenceData.PP_UPI_ID_KEY, loginModal.getData().getPhonepe_mobile_no());
                        SharedPreferenceData.setPrefrenccccrrrrreStrngData(activity, SharedPreferenceData.G_PAY_UPI_ID_KEY, loginModal.getData().getGpay_mobile_no());
                        userName.setText(loginModal.getData().getUsername());
                        mMobileNum.setText(loginModal.getData().getMobile());
                    }else
                        Toast.makeText(activity, loginModal.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModal> call, Throwable t) {
                System.out.println("getUserDetails error "+t);
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
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
    public void onBackPressed() {
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.hostFragment);
        if (!(currentFragment instanceof HomeFragment)) {
            // If the current fragment is not HomeFragment, navigate back to HomeFragment
            changeNavigation(new HomeFragment(),getString(R.string.app_name));
            return;
        }
        if (mDrawerLayout.isDrawerOpen(mNaviView)){
            mDrawerLayout.closeDrawers();
            return;
        }
       finishAffinity();
    }
}