package in.kal_777.kal_777_777.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;

public class ContactFragment extends Fragment {

    View view;
    private TextInputEditText mn1, mN2, whatsAN,emailId,telegram;
    private TextInputLayout whatsapp_lyt, email_lyt, call1, call2, telegram_lyt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        view=  inflater.inflate(R.layout.fragment_contact, container, false);
        mn1 = view.findViewById(R.id.phn_call_1);
        mN2 = view.findViewById(R.id.phn_call_2);
        whatsAN = view.findViewById(R.id.awhts_app_num);
        emailId = view.findViewById(R.id.mailText);
        whatsapp_lyt = view.findViewById(R.id.whatsapp_lyt);
        email_lyt = view.findViewById(R.id.mail_lyt);
        call1 = view.findViewById(R.id.call1_lyt);
        call2 = view.findViewById(R.id.call2_lyt);
        telegram = view.findViewById(R.id.telegramText);
        telegram_lyt = view.findViewById(R.id.telegram_lyt);
        loadData();
        clickListener();
        return view;
    }

    private void clickListener() {
        whatsapp_lyt.setOnClickListener(v -> whatsAppBtn());
        whatsAN.setOnClickListener(v -> whatsAppBtn());
        email_lyt.setOnClickListener(v -> emailBtn());
        emailId.setOnClickListener(v -> emailBtn());
        call1.setOnClickListener(v -> phoneNum(SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.PHONE_NUMBER1_KEY)));
        mn1.setOnClickListener(v -> phoneNum(SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.PHONE_NUMBER1_KEY)));
        call2.setOnClickListener(v -> phoneNum(SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.PHONE_NUMBER2_KEY)));
        mN2.setOnClickListener(v -> phoneNum(SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.PHONE_NUMBER2_KEY)));
        telegram.setOnClickListener(v -> telegram(SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.TELEGRAM_KEY)));
        telegram_lyt.setOnClickListener(v -> telegram(SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.TELEGRAM_KEY)));}

    private void telegram(String link) {
        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(SharedPreferenceData.getContaaaaaactObject(getContext(),
                        SharedPreferenceData.TELEGRAM_KEY)));
        startActivity(intent);
    }

    private void loadData() {
        mn1.setText(SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.PHONE_NUMBER1_KEY));
        mN2.setText(SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.PHONE_NUMBER2_KEY));
        whatsAN.setText(SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.WHATSAP_NUMBER_KEY));
        emailId.setText(SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.REACH_US_EMAIL_KEY));
        telegram.setText(SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.TELEGRAM_KEY));
    }

    public void phoneNum(String number) {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CALL_PHONE}, 100);
        }else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+ number));
            startActivity(callIntent);
        }
    }
    public void whatsAppBtn() {
        String url = "https://api.whatsapp.com/send?phone="+ SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.WHATSAP_NUMBER_KEY);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void emailBtn() {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{SharedPreferenceData.getContaaaaaactObject(getContext(), SharedPreferenceData.REACH_US_EMAIL_KEY)});
        email.putExtra(Intent.EXTRA_SUBJECT, "Mail Subject");
        email.putExtra(Intent.EXTRA_TEXT, "Mail message");
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

}