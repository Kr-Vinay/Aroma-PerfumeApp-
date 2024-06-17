package com.example.perfumeapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ItemviewHolder> {
    private List<res> resList;
    private Context context;

    public adapter(List<res> resList, Context context) {
        this.resList = resList;
        this.context = context;
    }






    @NonNull
    @Override
    public ItemviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_layout,parent, false);
        return new ItemviewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ItemviewHolder holder, int position) {
        res currentItem=resList.get(position);
      Glide.with(context).load(currentItem.getImageID()).into(holder.imageView);




    }


    @Override
    public int getItemCount() {
        return resList.size();
    }

    public class ItemviewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;



        public ItemviewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);

        }
    }
}

