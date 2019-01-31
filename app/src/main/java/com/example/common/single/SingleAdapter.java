package com.example.common.single;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.R;
import com.example.common.interfaces.PlayerListener;
import com.example.common.utils.BecastPlayer;

import java.util.ArrayList;
import java.util.List;

import library.common.img.GlideLoader;

public class SingleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<? extends Single> singleList;
    private final Context mContext;
    private final GlideLoader glideLoader;
    private static final int view_Foot = 1;
    //主要布局
    private static final int view_Normal = 2;
    //是否隐藏
    private boolean isLoadMore = false;

    public SingleAdapter(List<Single> singleList, Context mContext) {
        this.singleList = singleList;
        this.mContext = mContext;
        glideLoader = new GlideLoader();
    }

    static class NormalViewHolder extends RecyclerView.ViewHolder {
        final TextView singleTitle;
        final TextView singleTime;
        final ImageView singleImg;

        NormalViewHolder(View view) {
            super(view);
            singleImg = view.findViewById(R.id.single_img);
            singleTitle = view.findViewById(R.id.single_title);
            singleTime = view.findViewById(R.id.single_time);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == view_Normal) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single, parent, false);
            NormalViewHolder normalViewHolder = new NormalViewHolder(view);
            view.setOnClickListener(v -> {
                Single single = singleList.get(normalViewHolder.getAdapterPosition());
                if (BecastPlayer.getINSTANCE() != null) {
                    BecastPlayer.getINSTANCE().addMusic(single);
                }
            });
            return normalViewHolder;
        } else {
            FootView view = new FootView(mContext);
            return new FootViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            FootView itemView = (FootView) holder.itemView;
            if (isLoadMore) {
                itemView.setData();
            } else {
                itemView.setVisibility(View.VISIBLE);
            }
        } else {
            if (singleList.size() != 0) {
                Single single = singleList.get(position);
                ((NormalViewHolder) holder).singleTitle.setText(single.getTitle());
                ((NormalViewHolder) holder).singleTime.setText(single.getUpdataTime());
                glideLoader.loadImage(mContext, single.getImgUrL(), ((NormalViewHolder) holder).singleImg);
            }
        }
    }

    public void listChange(List<Single> singleList) {
        this.singleList = singleList;
    }

    @Override
    public int getItemCount() {
        return singleList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return view_Foot;
        } else {
            return view_Normal;
        }
    }

    public void setIsLoadMore() {
        this.isLoadMore = true;
        notifyDataSetChanged();
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        FootViewHolder(View itemView) {
            super(itemView);
        }
    }
}
