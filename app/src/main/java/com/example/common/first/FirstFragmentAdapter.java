package com.example.common.first;

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
import com.example.common.single.SingleActivity;

import java.util.List;

import library.common.img.GlideLoader;

public class FirstFragmentAdapter extends RecyclerView.Adapter<FirstFragmentAdapter.ViewHolder> {
    private final Context mContext;
    private final GlideLoader glideLoader;
    private List<Bean.Results> resultsList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView chosenTitle;
        final ImageView chosenImg;
        final TextView chosenTime;
        final TextView chosenName;

        ViewHolder(View view) {
            super(view);
            chosenTitle = view.findViewById(R.id.firstFragment_title);
            chosenImg = view.findViewById(R.id.firstFragment_img);
            chosenName = view.findViewById(R.id.firstFragment_name);
            chosenTime = view.findViewById(R.id.firstFragment_time);
        }
    }
    FirstFragmentAdapter(Context mContext, List<Bean.Results> resultsList, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.resultsList = resultsList;
        glideLoader =new GlideLoader();
    }

    @NonNull
    @Override
    public FirstFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_firstfragment, parent, false);
       ViewHolder viewHolder= new ViewHolder(view);
        view.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            Bean.Results results = resultsList.get(position);
            Intent intent = new Intent(mContext, SingleActivity.class);
            intent.putExtra("feedUrl",results.getFeedUrl());
            intent.putExtra("imgUrl",results.getArtworkUrl100());
            mContext.startActivity(intent);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FirstFragmentAdapter.ViewHolder holder, int position) {
        if (!resultsList.isEmpty())
        {
           Bean.Results results = resultsList.get(position);
           holder.chosenTitle.setText(results.getCollectionName());
           holder.chosenName.setText(results.getArtistName());
           glideLoader.loadImage(mContext,results.getArtworkUrl30(),holder.chosenImg);
        }
    }
    public void listChanger(List<Bean.Results> resultsList)
    {
        this.resultsList = resultsList;
    }
    @Override
    public int getItemCount() {
        return resultsList.size();
    }
}
