package in.kal_777.kal_777_777.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import in.kal_777.kal_777_777.R;

public class MBSFragment extends BottomSheetDialogFragment {

    private View view;
    public MaterialTextView mtv_bottom_msg;
    private BottomSheetDialog bottomSheetDialog;
    public interface OnConformClick{
        void onConformClick();
    }
    private final OnConformClick conformClick;
    private MaterialButton conform, cancel;

    public MBSFragment(OnConformClick conformClick) {
        this.conformClick = conformClick;
    }

    @NonNull @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> bottomSheetDialog = (BottomSheetDialog) dialogInterface);
        return  dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bs_transaction_layout, container, false);
        intVariables();
        listeners();

        return view;

    }

    private void listeners() {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        conform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conformClick.onConformClick();
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void intVariables() {
        cancel = view.findViewById(R.id.cancel);
        conform = view.findViewById(R.id.confirm);
        mtv_bottom_msg = view.findViewById(R.id.mtv_bottom_msg);
    }

}
