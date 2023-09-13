package in.kal_777.kal_777_777.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import in.kal_777.kal_777_777.Modals.ModelDesawarBid;
import in.kal_777.kal_777_777.R;

public class GDBiddingAdapter extends RecyclerView.Adapter<GDBiddingAdapter.ViewHolder> {

    public interface OnItemClickListenerInter {
        void onItemClickFun(int position);
    }

    Context context;
    private final List<ModelDesawarBid> modelDesawarBidList;
    private final OnItemClickListenerInter listener;

    public GDBiddingAdapter(Context context, List<ModelDesawarBid> modelDesawarBidList, OnItemClickListenerInter listener) {
        this.context = context;
        this.modelDesawarBidList = modelDesawarBidList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_bidding_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binddddd(modelDesawarBidList.get(position),position, listener);
    }

    @Override
    public int getItemCount() {
        return modelDesawarBidList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView digggggit;
        private final MaterialTextView poinnnnts;
        private final MaterialTextView digitsssssText;
        private final ShapeableImageView crossssssBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            digggggit = itemView.findViewById(R.id.adigit);
            poinnnnts = itemView.findViewById(R.id.apoints);
            digitsssssText = itemView.findViewById(R.id.adigitsText);
            crossssssBtn = itemView.findViewById(R.id.acrossBtn);
        }

        public void binddddd(ModelDesawarBid modelDesawarBid, int position, OnItemClickListenerInter listener) {
            poinnnnts.setText(modelDesawarBid.getBid_points());
            switch (modelDesawarBid.getGame_type()){
                case "left_digit":
                    digitsssssText.setText("Left Digit");
                    digggggit.setText(modelDesawarBid.getLeft_digit());

                    break;
                case "right_digit":
                    digitsssssText.setText("Right Digit");
                    digggggit.setText(modelDesawarBid.getRight_digit());

                    break;
                case "jodi_digit":
                    digitsssssText.setText("Jodi Digit");
                    digggggit.setText(modelDesawarBid.getLeft_digit()+modelDesawarBid.getRight_digit());

                    break;
            }

            crossssssBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickFun(position);
                }
            });
        }
    }
}
