package in.kal_777.kal_777_777.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import in.kal_777.kal_777_777.APIs.ApiClient;
import in.kal_777.kal_777_777.Activities.LoginActivity;
import in.kal_777.kal_777_777.Adapters.WalletAdapter;
import in.kal_777.kal_777_777.Modals.WalletStatementModal;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;
import in.kal_777.kal_777_777.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletStatementFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    public List<WalletStatementModal.Data.Statement> modelWalletArrayList = new ArrayList<>();
    private ShapeableImageView mEmptyView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wallet_statement, container, false);
        intVariables();

        confRecycler();
        purseStatementMethod(getContext());
        refreshLayout.setOnRefreshListener(() -> {
            purseStatementMethod(getContext());
        });
        return view;
    }

    private void intVariables() {
        recyclerView = view.findViewById(R.id.recyclerViewWallet);
        mEmptyView = view.findViewById(R.id.aempty_image);
        refreshLayout = view.findViewById(R.id.mswipe_ref_lyt);
    }
    public void confRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        WalletAdapter walletAdapter = new WalletAdapter(getContext(), modelWalletArrayList);
        recyclerView.setAdapter(walletAdapter);
        if (!modelWalletArrayList.isEmpty()){
            mEmptyView.setVisibility(View.GONE);
        }else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }
    private void purseStatementMethod(Context activity) {
        refreshLayout.setRefreshing(true);
        Call<WalletStatementModal> call = ApiClient.getClient().wallet_statement(SharedPreferenceData.getLogiiiinInToken(activity),"");
        call.enqueue(new Callback<WalletStatementModal>() {
            @Override
            public void onResponse(Call<WalletStatementModal> call, Response<WalletStatementModal> response) {
                if (response.isSuccessful()){
                    WalletStatementModal walletStatementModal = response.body();
                    if (walletStatementModal.getCode().equalsIgnoreCase("505")){
                        SharedPreferenceData.setCllllleaninfo(activity);
                        Toast.makeText(activity, walletStatementModal.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    }
                    if (walletStatementModal.getStatus().equalsIgnoreCase("success")){
                        SharedPreferenceData.setUseeeeerCoins(activity, walletStatementModal.getData().getAvailablePoints());
                        modelWalletArrayList = walletStatementModal.getData().getStatement();
                        confRecycler();
                    }
                }else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<WalletStatementModal> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                System.out.println("walletStatement error "+t);
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}