package in.kal_777.kal_777_777.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Activities.HomeActivity;
import in.kal_777.kal_777_777.Activities.LoginActivity;
import in.kal_777.kal_777_777.Modals.LoginModal;
import in.kal_777.kal_777_777.Others.InternetService;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    View view;
    private MaterialButton materialButton;
    private ProgressBar progressBar;
    private TextInputEditText editTextName, editTextEmail;
    MaterialTextView editTextNumbar;
    boolean i=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        initIds();

        materialButton.setOnClickListener(v -> submitBtn());
        return view;
    }

    private void initIds() {
        editTextName = view.findViewById(R.id.client_name);
        editTextEmail = view.findViewById(R.id.edt_txt_mail);
        editTextNumbar = view.findViewById(R.id.phn_num);
        materialButton = view.findViewById(R.id.SbmtBtn);
        progressBar = view.findViewById(R.id.prgrss_bar);

        loadData();
    }
    private void loadData() {
        editTextName.setText(SharedPreferenceData.getRegistrrrrrrttationObject(getContext(), SharedPreferenceData.USER_NAME_KEY));
        editTextNumbar.setText(SharedPreferenceData.getRegistrrrrrrttationObject(getContext(), SharedPreferenceData.PHONE_NUMBER_KEY));
        editTextEmail.setText(SharedPreferenceData.getPrfrnnnnnceinfo(getContext(), SharedPreferenceData.CLIENT_EMAIL_KEY));
    }


    public void submitBtn() {
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
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
        if (InternetService.isOnline(requireActivity()))
            updateInfoMethod(getContext(), editTextEmail.getText().toString(), editTextName.getText().toString());
        else Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
    }
    private void updateInfoMethod(Context activity, String email, String name) {
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
                        requireActivity().finish();
                    }
                    if (loginModal.getStatus().equalsIgnoreCase("success")){
                        HomeActivity.userName.setText(name);
                        SharedPreferenceData.setRegisssstttterData(activity, SharedPreferenceData.USER_NAME_KEY, loginModal.getData().getUsername());
                        SharedPreferenceData.setPrefrenccccrrrrreStrngData(activity, SharedPreferenceData.CLIENT_EMAIL_KEY, loginModal.getData().getEmail());
                    }
                    Toast.makeText(getContext(), loginModal.getMessage(), Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
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
}