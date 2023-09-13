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

import in.kal_777.kal_777_777.Modals.GaliWinModal;
import in.kal_777.kal_777_777.R;

public class GDHistoryAdapter extends RecyclerView.Adapter<GDHistoryAdapter.ViewHolder> {

    Context context;
    private final List<GaliWinModal.Data> dataList;

    public GDHistoryAdapter(Context context, List<GaliWinModal.Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_history_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GaliWinModal.Data data = dataList.get(position);
        holder.bindddddddddd(data);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView agameName;
        private final MaterialTextView agameDate;
        private final MaterialTextView abidPoints;
        private final MaterialTextView awinPoints;
        private final MaterialTextView agameNumberOpen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            agameName = itemView.findViewById(R.id.agameName);
            agameDate = itemView.findViewById(R.id.agameDate);
            abidPoints = itemView.findViewById(R.id.abidPoints);
            awinPoints = itemView.findViewById(R.id.awinPoints);
            MaterialTextView session = itemView.findViewById(R.id.agameSession);
            agameNumberOpen = itemView.findViewById(R.id.agameNumberOpen);
            MaterialTextView gameNumberClose = itemView.findViewById(R.id.agameNumberClose);
            awinPoints.setVisibility(View.GONE);
            gameNumberClose.setVisibility(View.GONE);
            session.setVisibility(View.GONE);
        }

        public void bindddddddddd(GaliWinModal.Data data) {
            String gameNameStr = data.getGameName();
            abidPoints.setText(data.getBidPoints()+" Points");
            agameDate.setText(data.getBiddedAt());
            if(!TextUtils.isEmpty(data.getWinPoints())){
                awinPoints.setText(data.getWinPoints()+" Points");
                awinPoints.setVisibility(View.VISIBLE);
                agameDate.setText(data.getWonAt());
            }
            switch (data.getGameType()){
                case "left_digit":
                    agameName.setText(gameNameStr+"( Left Digit )");
                    agameNumberOpen.setText("Game Number : "+data.getLeft_digit());
                    break;
                case "right_digit":
                    agameName.setText(gameNameStr+"( Right Panna )");
                    agameNumberOpen.setText("Game Number : "+data.getRight_digit());
                    break;
                case "jodi_digit":
                    agameName.setText(gameNameStr+"( Jodi Digit )");
                    agameNumberOpen.setText("Game Number : "+data.getLeft_digit()+data.getRight_digit());
                    break;
            }
        }
    }
}
