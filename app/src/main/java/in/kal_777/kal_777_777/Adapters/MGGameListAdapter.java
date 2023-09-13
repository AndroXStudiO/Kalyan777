package in.kal_777.kal_777_777.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import in.kal_777.kal_777_777.R;
import in.kal_777.kal_777_777.Activities.WebViewActivity;
import in.kal_777.kal_777_777.Modals.MainGameList;
import in.kal_777.kal_777_777.Others.SharedPreferenceData;

public class MGGameListAdapter extends RecyclerView.Adapter<MGGameListAdapter.ViewHolder>{

    public interface OnItemClickListener{
        void onItemClick(MainGameList.Data data, View itemView);
    }
    Context context;
    private final ArrayList<MainGameList.Data> datalArrayList;

    private final OnItemClickListener listener;

    public MGGameListAdapter(Context context, ArrayList<MainGameList.Data> datalArrayList, OnItemClickListener listener) {
        this.context = context;
        this.datalArrayList = datalArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_mg_game_list_layout_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.attach(datalArrayList.get(position), listener, context, position);
    }

    @Override
    public int getItemCount() {
        return datalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView eventNumber;
        private final MaterialTextView openingTime;
        private final MaterialTextView closingTime;
        private final MaterialTextView marketOpen;
        private final MaterialTextView eventType;
        private ImageView ll_chart, ll_play;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventType = itemView.findViewById(R.id.aeventType);
            eventNumber = itemView.findViewById(R.id.aeventNumber);
            openingTime = itemView.findViewById(R.id.aopeningTime);
            closingTime = itemView.findViewById(R.id.aclosingTime);
            marketOpen = itemView.findViewById(R.id.amarketOpen);
            ll_play = itemView.findViewById(R.id.ll_play);
            ll_chart = itemView.findViewById(R.id.ll_chart);
        }

        public void attach(MainGameList.Data data, OnItemClickListener listener, Context context, int position) {
            if (!SharedPreferenceData.getLiveeeeUser(context)){
                ll_chart.setVisibility(View.GONE);
                ll_play.setVisibility(View.GONE);
            }else {
                ll_play.setVisibility(View.VISIBLE);
                ll_chart.setVisibility(View.VISIBLE);
                if (data.isMarket_open() && data.isPlay()){
                    marketOpen.setText("Market Running");
                    ll_play.setImageResource(R.drawable.ic_market_open);
                 //   marketOpen.setTextColor(ContextCompat.getColor(context, R.color.green));
                }else {
                    ll_play.setImageResource(R.drawable.ic_market_closed);
                //    marketOpen.setTextColor(ContextCompat.getColor(context, R.color.red));
                    marketOpen.setText("Market Closed");
                }
                userDataMethod(data);
            }

            eventType.setText(data.getName());
            openingTime.setText("Open: "+data.getOpen_time());
            closingTime.setText("Close: "+data.getClose_time());
            eventNumber.setText(data.getResult());
            itemView.setOnClickListener(v ->{
                if(SharedPreferenceData.getLiveeeeUser(context)){
                    listener.onItemClick(data, v);
                }
            });

            ll_chart.setOnClickListener(v -> {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("Chart", data.getChart_url());
                context.startActivity(intent);
            });


        }

        private void userDataMethod(MainGameList.Data data) {
            eventType.setText(data.getName());
            openingTime.setText("Open: "+data.getOpen_time());
            closingTime.setText("Close: "+data.getClose_time());
            eventNumber.setText(data.getResult());

            if (data.isMarket_open() && data.isPlay()){
                //marketOpen.setTextColor(ContextCompat.getColor(context, R.color.green));
                marketOpen.setText("Market Running");
            }else {
             //   marketOpen.setTextColor(ContextCompat.getColor(context, R.color.red));
                marketOpen.setText("Market Closed");
            }
            Animation  animation = AnimationUtils.loadAnimation(context, R.anim.ic_shake);
            eventNumber.setAnimation(animation);
        }
    }
}