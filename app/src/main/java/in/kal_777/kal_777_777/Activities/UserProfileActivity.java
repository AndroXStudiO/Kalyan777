package in.kal_777.kal_777_777.Activities;


import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.myReceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Modals.LoginModal;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.NetBroadCastClass;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private MaterialButton materialButton;
    private ProgressBar progressBar;
    private TextInputEditText editTextName, editTextNumbar, editTextEmail;
    private IntentFilter mIntentFilter;
    private ExtendedFloatingActionButton fb_edit;
    boolean i=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_screen);
        intVariables();
        loadData();
        toolbarMethod();
    }

    private void loadData() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BCStrForAction);
        Intent serviceIntent = new Intent(this, InternetService.class);
        startService(serviceIntent);


        editTextName.setText(SharedPreferenceData.getRegistrrrrrrttationObject(this, SharedPreferenceData.USER_NAME_KEY));
        editTextNumbar.setText(SharedPreferenceData.getRegistrrrrrrttationObject(this, SharedPreferenceData.PHONE_NUMBER_KEY));
        editTextEmail.setText(SharedPreferenceData.getPrfrnnnnnceinfo(this, SharedPreferenceData.CLIENT_EMAIL_KEY));
        editTextName.setSelection(editTextName.getText().length());
    }


    private void intVariables() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("User Profile");
        editTextName = findViewById(R.id.client_name);
        editTextEmail = findViewById(R.id.edt_txt_mail);
        editTextNumbar = findViewById(R.id.phn_num);
        materialButton = findViewById(R.id.SbmtBtn);
        progressBar = findViewById(R.id.prgrss_bar);
        fb_edit = findViewById(R.id.fb_edit);

        MaterialTextView mDataConText = findViewById(R.id.internet_text);
        NetBroadCastClass broadCastClass = new NetBroadCastClass(mDataConText);
    }
    private void toolbarMethod() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void editBtn(View view) {
        if (i){
            fb_edit.setText("Submit");
            i=false;
            editTextName.setEnabled(true);
            editTextEmail.setEnabled(true);
            editTextName.requestFocus();
            editTextName.setSelection(editTextName.getText().length());
            return;

        }
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (TextUtils.isEmpty(editTextName.getText().toString())){
            Snackbar.make(view, "Please Enter Your Name", 2000).show();
            return;
        }
        if (TextUtils.isEmpty(editTextEmail.getText().toString())){
            Snackbar.make(view, "Please Enter Your email", 2000).show();
            return;
        }
        if (!isValidEmail(editTextEmail.getText())){
            Snackbar.make(view, "Please Enter Valid Email", 2000).show();
            return;
        }
        if (InternetService.isOnline(this))
            updateInfoMethod(UserProfileActivity.this, editTextEmail.getText().toString(), editTextName.getText().toString());
        else Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
    }

    public void updatBtn(View view) {

    }

    private void updateInfoMethod(UserProfileActivity activity, String email, String name) {
        progressBar.setVisibility(View.VISIBLE);
        Call<LoginModal> call = ApiClient.getClient().update_profile(SharedPreferenceData.getLogiiiinInToken(activity),email,name);
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
                        materialButton.setVisibility(View.GONE);
                        editTextName.setEnabled(false);
                        editTextEmail.setEnabled(false);
                        HomeActivity.userName.setText(name);
                        //user_name.setText("Hello, "+ name);
                        i=true;
                        fb_edit.setText("Edit");
                        SharedPreferenceData.setRegisssstttterData(activity, SharedPreferenceData.USER_NAME_KEY, loginModal.getData().getUsername());
                        SharedPreferenceData.setPrefrenccccrrrrreStrngData(activity, SharedPreferenceData.CLIENT_EMAIL_KEY, loginModal.getData().getEmail());
                    }
                    Toast.makeText(UserProfileActivity.this, loginModal.getMessage(), Toast.LENGTH_SHORT).show();
                }else
                Toast.makeText(UserProfileActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LoginModal> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                System.out.println("updateProfile error "+t);
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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