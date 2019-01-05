package com.example.common.tour;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.R;
import com.example.common.first.Bean;
import com.example.common.single.SingleActivity;

import java.util.List;

import library.common.img.GlideLoader;

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder> {
    private List<Bean.Results> chosenList;
    private final Context context;
    private GlideLoader glideLoader;

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView chooseImg;
        final TextView chooseName;

        ViewHolder(View itemView) {
            super(itemView);
            chooseImg = itemView.findViewById(R.id.choose_img);
            chooseName = itemView.findViewById(R.id.choose_title);
        }
    }

    ChooseAdapter(List<Bean.Results> chosenList, Context context, RecyclerView recyclerView) {
        this.chosenList = chosenList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose, parent, false);
        glideLoader = new GlideLoader();
        ViewHolder viewHolder= new ViewHolder(view);
        view.setOnClickListener(v->{
            int position = viewHolder.getAdapterPosition();
            Bean.Results results = chosenList.get(position);
            Intent intent = new Intent(context, SingleActivity.class);
            intent.putExtra("feedUrl",results.getFeedUrl());
            intent.putExtra("imgUrl",results.getArtworkUrl100());
            context.startActivity(intent);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (chosenList != null&&chosenList.size()!=0) {
            Bean.Results results = chosenList.get(position);
            holder.chooseName.setText(results.getCollectionName());
           glideLoader.loadImage(context,results.getArtworkUrl60(),holder.chooseImg);
        }
    }

    @Override
    public int getItemCount() {
        return chosenList.size();
    }
    public void listChanged(List<Bean.Results> resultsList)
    {
        this.chosenList = resultsList;
        this.notifyDataSetChanged();
    }
}
