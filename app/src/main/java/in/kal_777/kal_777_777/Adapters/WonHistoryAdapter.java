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

import in.kal_777.kal_777_777.Modals.WinHistoryModal;
import in.kal_777.kal_777_777.R;

public class WonHistoryAdapter extends RecyclerView.Adapter<WonHistoryAdapter.ViewHolder> {

    Context context;
     private final List<WinHistoryModal.Data> dataList;

    public WonHistoryAdapter(Context context, List<WinHistoryModal.Data> dataList) {
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
        WinHistoryModal.Data data = dataList.get(position);
        holder.attach(data);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView gameName;
        private final MaterialTextView gameSession;
        private final MaterialTextView gameNumberOpen;
        private final MaterialTextView gameDate;
        private final MaterialTextView bidPoints;
        private final MaterialTextView winPoints;
        private final MaterialTextView gameNumberClose;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gameName = itemView.findViewById(R.id.agameName);
            gameSession = itemView.findViewById(R.id.agameSession);
            gameNumberOpen = itemView.findViewById(R.id.agameNumberOpen);
            gameDate = itemView.findViewById(R.id.agameDate);
            bidPoints = itemView.findViewById(R.id.abidPoints);
            winPoints = itemView.findViewById(R.id.awinPoints);
            gameNumberClose = itemView.findViewById(R.id.agameNumberClose);
            winPoints.setVisibility(View.GONE);
            gameNumberClose.setVisibility(View.GONE);
        }

        public void attach(WinHistoryModal.Data data) {
            String gameNameStr = data.getGameName();
            bidPoints.setText(data.getBidPoints()+" Points");
            gameDate.setText(data.getBiddedAt());
           // ll_bid_history.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.teal_200)));
            if(!TextUtils.isEmpty(data.getWinPoints())){
              //  ll_bid_history.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.green)));
                winPoints.setText(data.getWinPoints()+" Points");
                winPoints.setVisibility(View.VISIBLE);
                gameDate.setText(data.getWonAt());
            }
            switch (data.getGameType()){
                case "single_digit":
                    gameName.setText(gameNameStr+"( Single Digit )");
                    gameNumberClose.setVisibility(View.GONE);
                    if(data.getSession().equalsIgnoreCase("open")){
                        gameSession.setText("Session : OPEN");
                        gameNumberOpen.setText("Open Digit : "+data.getOpenDigit());
                    }
                    else{
                        gameSession.setText("Session : CLOSE");
                        gameNumberOpen.setText("Close Digit : "+data.getCloseDigit());
                    }
                    break;
                case "jodi_digit":
                    gameNumberClose.setVisibility(View.GONE);
                    gameName.setText(gameNameStr+"( Jodi Digit )");
                    gameSession.setText("Session : OPEN");
                    String jodi = "Jodi Digit : "+data.getOpenDigit()+""+data.getCloseDigit();
                    gameNumberOpen.setText(jodi);
                    break;
                case "single_panna":
                    gameNumberClose.setVisibility(View.GONE);
                    gameName.setText(gameNameStr+"( Single Panna )");
                    if(data.getSession().equalsIgnoreCase("open")){
                        gameSession.setText("Session : OPEN");
                        gameNumberOpen.setText("Open Panna : "+data.getOpenPanna());
                    }
                    else{
                        gameSession.setText("Session : CLOSE");
                        gameNumberOpen.setText("Close Panna : "+data.getClosePanna());
                    }
                    break;
                case "double_panna":
                    gameName.setText(gameNameStr+"( Double Panna )");
                    gameNumberClose.setVisibility(View.GONE);
                    if(data.getSession().equalsIgnoreCase("open")){
                        gameSession.setText("Session : OPEN");
                        gameNumberOpen.setText("Open Panna : "+data.getOpenPanna());
                    }
                    else{
                        gameSession.setText("Session : CLOSE");
                        gameNumberOpen.setText("Close Panna : "+data.getClosePanna());
                    }
                    break;
                case "triple_panna":
                    gameName.setText(gameNameStr+"( Triple Panna )");
                    gameNumberClose.setVisibility(View.GONE);
                    if(data.getSession().equalsIgnoreCase("open")){
                        gameSession.setText("Session : OPEN");
                        gameNumberOpen.setText("Open Panna : "+data.getOpenPanna());
                    }
                    else{
                        gameSession.setText("Session : CLOSE");
                        gameNumberOpen.setText("Close Panna : "+data.getClosePanna());
                    }
                    break;
                case "half_sangam":
                    gameName.setText(gameNameStr+"( Half Sangam )");
                    if(data.getSession().equalsIgnoreCase("open")){
                        gameSession.setText("Session : OPEN");
                        gameNumberOpen.setText("Open Digit : "+data.getOpenDigit());
                        gameNumberClose.setText("Close Panna : "+data.getClosePanna());
                        gameNumberClose.setVisibility(View.VISIBLE);
                    }
                    else{
                        gameSession.setText("Session : Close");
                        gameNumberOpen.setText("Open Panna : "+data.getOpenPanna());
                        gameNumberClose.setText("Close Digit : "+data.getCloseDigit());
                        gameNumberClose.setVisibility(View.VISIBLE);
                    }
                    break;
                case "full_sangam":
                    gameName.setText(gameNameStr+"( Full Sangam )");
                    gameSession.setText("Session : OPEN");
                    gameNumberOpen.setText("Open Panna : "+data.getOpenPanna());
                    gameNumberClose.setText("Close Panna : "+data.getClosePanna());
                    gameNumberClose.setVisibility(View.VISIBLE);
                    break;

            }
        }
    }
}
