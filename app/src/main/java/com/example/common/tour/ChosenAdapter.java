package com.example.common.tour;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.R;

import java.util.ArrayList;
import java.util.List;

import library.common.img.GlideLoader;

public class ChosenAdapter extends RecyclerView.Adapter<ChosenAdapter.ViewHolder> {
    private Context mContext;
    private RecyclerView recyclerView;
    private GlideLoader glideLoader;
    private List<Chosen> mChosenList =new ArrayList<>();
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chosenTitle;
        ImageView chosenImg;

        ViewHolder(View view) {
            super(view);
            chosenTitle = view.findViewById(R.id.chosen_title);
            chosenImg = view.findViewById(R.id.chosen_img);
        }
    }
    ChosenAdapter(Context mContext, List<Chosen> mChosenList, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.mChosenList =mChosenList;
        this.recyclerView = recyclerView;
        glideLoader =new GlideLoader();
    }

    @NonNull
    @Override
    public ChosenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chosen, parent, false);
        return new ChosenAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChosenAdapter.ViewHolder holder, int position) {
        if (!mChosenList.isEmpty())
        {
           Chosen chosen =mChosenList.get(position);
           holder.chosenTitle.setText(chosen.getTitle());
           glideLoader.loadImage(mContext,chosen.getImgUrl(),holder.chosenImg);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
