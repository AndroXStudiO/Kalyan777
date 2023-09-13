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

import in.kal_777.kal_777_777.Modals.StarlineGameModel;
import in.kal_777.kal_777_777.R;

public class SLListAdapter extends RecyclerView.Adapter<SLListAdapter.ViewHolder>{

    public interface OnItemClickListener{
        void onItemClick(StarlineGameModel.Data.StarlineGame starlineGame, View itemView);
    }
    Context context;
    private final List<StarlineGameModel.Data.StarlineGame> starlineGameList;

    private final OnItemClickListener listener;

    public SLListAdapter(Context context, List<StarlineGameModel.Data.StarlineGame> starlineGameList, OnItemClickListener listener) {
        this.context = context;
        this.starlineGameList = starlineGameList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_sl_game_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(starlineGameList.get(position), listener, context, position);
    }

    @Override
    public int getItemCount() {
        return starlineGameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView gameName;
        private final MaterialTextView gameResult;
        private final ShapeableImageView gamePlay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.agameName);
            gameResult = itemView.findViewById(R.id.agameResult);
            gamePlay = itemView.findViewById(R.id.agamePlay);

        }

        public void bind(StarlineGameModel.Data.StarlineGame starlineGame, OnItemClickListener listener, Context context, int position) {

            gameName.setText(starlineGame.getName());
            gameResult.setText(starlineGame.getResult());
            if(starlineGame.isPlay()) gamePlay.setImageResource(R.drawable.ic_play_icon_clip_art);
            else gamePlay.setImageResource(R.drawable.ic_cancel);
            Animation  animation = AnimationUtils.loadAnimation(context, R.anim.ic_shake);
            gameResult.setAnimation(animation);
            itemView.setOnClickListener(v ->{
                listener.onItemClick(starlineGame, v);
            });
        }
    }
}
