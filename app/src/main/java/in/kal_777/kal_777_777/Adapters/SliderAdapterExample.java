package in.kal_777.kal_777_777.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import in.kal_777.kal_777_777.R;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderViewHolder> {
    private String images[];
    Context context;
    public SliderAdapterExample(String[] images, Context context) {
        this.images = images;
        this.context = context;
    }
    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_layout,null);
        return new SliderViewHolder(view);
    }
    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        Glide
                .with(context)
                .load(images[position])
                .centerCrop()
                .error(R.drawable.ic_baseline_info_24)
                .into(viewHolder.imageView);
    }
    @Override
    public int getCount() {
        return images.length;
    }
    public class SliderViewHolder extends ViewHolder {
        private final ImageView imageView;
        public SliderViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
        }
    }
}
