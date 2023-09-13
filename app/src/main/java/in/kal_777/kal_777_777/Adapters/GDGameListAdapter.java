package in.kal_777.kal_777_777.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import in.kal_777.kal_777_777.Modals.GaliGameList;
import in.kal_777.kal_777_777.R;

public class GDGameListAdapter extends RecyclerView.Adapter<GDGameListAdapter.ViewHolder>{

    public interface OnItemClickListenerInter {
        void onItemClickFun(GaliGameList.Data.GaliDesawarGame starlineGame, View itemView);
    }
    Context context;
    private final List<GaliGameList.Data.GaliDesawarGame> galiDesawarGameList;

    private final OnItemClickListenerInter listener;

    public GDGameListAdapter(Context context, List<GaliGameList.Data.GaliDesawarGame> galiDesawarGameList, OnItemClickListenerInter listener) {
        this.context = context;
        this.galiDesawarGameList = galiDesawarGameList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_gd_game_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binddddddd(galiDesawarGameList.get(position), listener, context, position);
    }

    @Override
    public int getItemCount() {
        return galiDesawarGameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView agameName;
        private final MaterialTextView agameResult;
        private final MaterialTextView acloseTime;
        private final ShapeableImageView agamePlay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            agameName = itemView.findViewById(R.id.agameName);
            agameResult = itemView.findViewById(R.id.agameResult);
            agamePlay = itemView.findViewById(R.id.agamePlay);
            acloseTime = itemView.findViewById(R.id.acloseTime);

        }

        public void binddddddd(GaliGameList.Data.GaliDesawarGame galiDesawarGame, OnItemClickListenerInter listener, Context context, int position) {

            acloseTime.setText("Close : " +galiDesawarGame.getTime());
            agameName.setText(galiDesawarGame.getName());
            agameResult.setText(galiDesawarGame.getResult());
            if(galiDesawarGame.isPlay()) agamePlay.setImageResource(R.drawable.ic_play_icon_clip_art);
            else agamePlay.setImageResource(R.drawable.ic_cancel);
            Animation  animation = AnimationUtils.loadAnimation(context, R.anim.ic_shake);
            agameResult.setAnimation(animation);
            itemView.setOnClickListener(v ->{
                listener.onItemClickFun(galiDesawarGame, v);
            });
        }
    }
}
