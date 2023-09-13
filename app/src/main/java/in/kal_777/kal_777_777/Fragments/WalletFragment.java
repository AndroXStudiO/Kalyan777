package in.kal_777.kal_777_777.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.Activities.AddCoinActivity;
import in.kal_777.kal_777_777.Activities.HomeActivity;
import in.kal_777.kal_777_777.Activities.PaymentMethodActivity;
import in.kal_777.kal_777_777.Activities.TakeOutActivity;
import in.kal_777.kal_777_777.Activities.TransferPointsActivity;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;

public class WalletFragment extends Fragment {
    View view;
    MaterialTextView withdraw, addPoints, method, transfer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wallet, container, false);
        withdraw = view.findViewById(R.id.withdraw);
        addPoints = view.findViewById(R.id.addPoints);
        method = view.findViewById(R.id.method);
        transfer = view.findViewById(R.id.transfer);
        clickListeners();
        Fragment walletFragment = new WalletStatementFragment();
        HomeActivity.changeNavigation2(walletFragment, "MY WALLET");
        return view;
    }
    private void clickListeners() {
        withdraw.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TakeOutActivity.class);
            startActivity(intent);
        });
        addPoints.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddCoinActivity.class);
            startActivity(intent);
        });
        transfer.setOnClickListener(v->{
            if (SharedPreferenceData.getTransssssmitCoins(getContext())){
                Intent transferCoins = new Intent(getContext(), TransferPointsActivity.class);
                startActivity(transferCoins);
            }else {
                Toast.makeText(getContext(), "Transfer is not enable in your account", Toast.LENGTH_SHORT).show();
            }
        });
        method.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PaymentMethodActivity.class);
            startActivity(intent);
        });
    }
}