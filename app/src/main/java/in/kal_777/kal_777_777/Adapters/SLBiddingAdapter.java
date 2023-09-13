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

import in.kal_777.kal_777_777.Modals.ModelSLB;
import in.kal_777.kal_777_777.R;

public class SLBiddingAdapter extends RecyclerView.Adapter<SLBiddingAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    Context context;
    private final List<ModelSLB> modelSLBList;
    private final OnItemClickListener listener;

    public SLBiddingAdapter(Context context, List<ModelSLB> modelSLBList, OnItemClickListener listener) {
        this.context = context;
        this.modelSLBList = modelSLBList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_bidding_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(modelSLBList.get(position),position, listener);
    }

    @Override
    public int getItemCount() {
        return modelSLBList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView digit;
        private final MaterialTextView points;
        private final MaterialTextView digitsText;
        private final ShapeableImageView crossBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            digit = itemView.findViewById(R.id.adigit);
            points = itemView.findViewById(R.id.apoints);
            digitsText = itemView.findViewById(R.id.adigitsText);
            crossBtn = itemView.findViewById(R.id.acrossBtn);
        }

        public void bind(ModelSLB modelSLB, int position, OnItemClickListener listener) {
            points.setText(modelSLB.getBid_points());
            switch (modelSLB.getGame_type()){
                case "single_digit":
                    digitsText.setText("Single Digit");
                    digit.setText(modelSLB.getDigit());

                    break;
                case "single_panna":
                    digitsText.setText("Single Panna");
                    digit.setText(modelSLB.getPanna());

                    break;
                case "double_panna":
                    digitsText.setText("Double Panna");
                    digit.setText(modelSLB.getPanna());

                    break;
                case "triple_panna":
                    digitsText.setText("Triple Panna");
                    digit.setText(modelSLB.getPanna());
                    break;

            }

            crossBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
        }
    }
}
