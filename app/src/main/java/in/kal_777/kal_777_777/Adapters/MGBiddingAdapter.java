package in.kal_777.kal_777_777.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import in.kal_777.kal_777_777.Modals.ModelPlaying;
import in.kal_777.kal_777_777.R;

public class MGBiddingAdapter extends RecyclerView.Adapter<MGBiddingAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public Context context;
    private final List<ModelPlaying> modelPlayingList;
    private final OnItemClickListener listener;

    public MGBiddingAdapter(Context context, List<ModelPlaying> modelPlayingList, OnItemClickListener listener) {
        this.context = context;
        this.modelPlayingList = modelPlayingList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_mg_bidding_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(modelPlayingList.get(position),position, listener);
    }

    @Override
    public int getItemCount() {
        return modelPlayingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView digit;
        private final MaterialTextView panna;
        private final MaterialTextView points;
        private final MaterialTextView session;
        private final MaterialTextView digitsText;
        private final MaterialTextView pannaText;
        private final LinearLayout ll_panna;
        private final LinearLayout ll_session;
        private final ShapeableImageView crossBtn;
        private final View viewSession;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            panna = itemView.findViewById(R.id.apanna);
            digit = itemView.findViewById(R.id.adigit);
            crossBtn = itemView.findViewById(R.id.acrossBtn);
            session = itemView.findViewById(R.id.asession);
            points = itemView.findViewById(R.id.apoints);
            viewSession = itemView.findViewById(R.id.aviewSession);
            ll_session = itemView.findViewById(R.id.all_session);
            pannaText = itemView.findViewById(R.id.apannaText);
            digitsText = itemView.findViewById(R.id.adigitsText);
            ll_panna = itemView.findViewById(R.id.all_panna);
        }

        public void bind(ModelPlaying modelPlaying, int position, OnItemClickListener listener) {
            points.setText(modelPlaying.getBid_points());
            session.setText(modelPlaying.getSession());
            switch (modelPlaying.getGame_type()){
                case "single_digit":
                    ll_panna.setVisibility(View.GONE);
                    digitsText.setText("Single Digit");
                    if(modelPlaying.getSession().equalsIgnoreCase("open")){
                        digit.setText(modelPlaying.getOpen_digit());
                    }
                    else{
                        digit.setText(modelPlaying.getClose_digit());
                    }
                    break;
                case "jodi_digit":
                    ll_panna.setVisibility(View.GONE);
                    viewSession.setVisibility(View.GONE);
                    ll_session.setVisibility(View.GONE);
                    digitsText.setText("Jodi Digit");
                    String jodi = modelPlaying.getOpen_digit()+""+ modelPlaying.getClose_digit();
                    digit.setText(jodi);
                    break;
                case "single_panna":
                    digitsText.setText("Single Panna");
                    ll_panna.setVisibility(View.GONE);
                    if(modelPlaying.getSession().equalsIgnoreCase("open")){
                        digit.setText(modelPlaying.getOpen_panna());
                    }
                    else{
                        digit.setText(modelPlaying.getClose_panna());
                    }
                    break;
                case "double_panna":
                    digitsText.setText("Double Panna");
                    ll_panna.setVisibility(View.GONE);
                    if(modelPlaying.getSession().equalsIgnoreCase("open")){
                        digit.setText(modelPlaying.getOpen_panna());
                    }
                    else{
                        digit.setText(modelPlaying.getClose_panna());
                    }
                    break;
                case "triple_panna":
                    digitsText.setText("Triple Panna");
                    ll_panna.setVisibility(View.GONE);
                    if(modelPlaying.getSession().equalsIgnoreCase("open")){
                        digit.setText(modelPlaying.getOpen_panna());
                    }
                    else{
                        digit.setText(modelPlaying.getClose_panna());
                    }
                    break;
                case "half_sangam":
                    if(modelPlaying.getSession().equalsIgnoreCase("open")){
                        digit.setText(modelPlaying.getOpen_digit());
                        panna.setText(modelPlaying.getClose_panna());
                        digitsText.setText("Open Digit");
                        pannaText.setText("Close Panna");
                    }
                    else{
                        digit.setText(modelPlaying.getOpen_panna());
                        panna.setText(modelPlaying.getClose_digit());
                        digitsText.setText("Open Panna");
                        pannaText.setText("Close Digit");
                    }
                    break;
                case "full_sangam":
                    digitsText.setText("Open Panna");
                    pannaText.setText("Close Panna");
                    viewSession.setVisibility(View.GONE);
                    ll_session.setVisibility(View.GONE);
                    digit.setText(modelPlaying.getOpen_panna());
                    panna.setText(modelPlaying.getClose_panna());
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
