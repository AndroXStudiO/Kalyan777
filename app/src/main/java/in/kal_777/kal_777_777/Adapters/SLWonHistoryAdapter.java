package in.kal_777.kal_777_777.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import in.kal_777.kal_777_777.Modals.StarlineWinModal;
import in.kal_777.kal_777_777.R;

public class SLWonHistoryAdapter extends RecyclerView.Adapter<SLWonHistoryAdapter.ViewHolder> {

    Context context;
    private final List<StarlineWinModal.Data> dataList;

    public SLWonHistoryAdapter(Context context, List<StarlineWinModal.Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_history_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StarlineWinModal.Data data = dataList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView gameName;
        private final MaterialTextView gameDate;
        private final MaterialTextView bidPoints;
        private final MaterialTextView winPoints;
        private final MaterialTextView gameNumberOpen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gameName = itemView.findViewById(R.id.agameName);
            gameDate = itemView.findViewById(R.id.agameDate);
            bidPoints = itemView.findViewById(R.id.abidPoints);
            winPoints = itemView.findViewById(R.id.awinPoints);
            MaterialTextView session = itemView.findViewById(R.id.agameSession);
            gameNumberOpen = itemView.findViewById(R.id.agameNumberOpen);
            MaterialTextView gameNumberClose = itemView.findViewById(R.id.agameNumberClose);
            winPoints.setVisibility(View.GONE);
            gameNumberClose.setVisibility(View.GONE);
            session.setVisibility(View.GONE);
        }

        public void bind(StarlineWinModal.Data data) {
            String gameNameStr = data.getGameName();
            bidPoints.setText(data.getBidPoints()+" Points");
            gameDate.setText(data.getBiddedAt());
            if(!TextUtils.isEmpty(data.getWinPoints())){
                winPoints.setText(data.getWinPoints()+" Points");
                winPoints.setVisibility(View.VISIBLE);
                gameDate.setText(data.getWonAt());
            }
            switch (data.getGameType()){
                case "single_digit":
                    gameName.setText(gameNameStr+"( Single Digit )");
                    gameNumberOpen.setText("Game Number : "+data.getDigit());
                    break;
                case "single_panna":
                    gameName.setText(gameNameStr+"( Single Panna )");
                    gameNumberOpen.setText("Game Number : "+data.getPanna());
                    break;
                case "double_panna":
                    gameName.setText(gameNameStr+"( Double Panna )");
                    gameNumberOpen.setText("Game Number : "+data.getPanna());
                    break;
                case "triple_panna":
                    gameName.setText(gameNameStr+"( Triple Panna )");
                    gameNumberOpen.setText("Game Number : "+data.getPanna());
                    break;
            }
        }
    }
}
