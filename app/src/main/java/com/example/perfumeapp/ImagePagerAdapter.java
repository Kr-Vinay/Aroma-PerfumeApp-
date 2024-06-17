package com.example.perfumeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder> {
    private Context context;
    private List<ImageModel> imageList;

    public ImagePagerAdapter(Context context, List<ImageModel> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_viewpager, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageModel imageModel = imageList.get(position);
        Glide.with(context).load(imageModel.getImageUrl()).into(holder.imageView);
        holder.descriptionView.setText(imageModel.getDescription());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView descriptionView;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
        }
    }
}
