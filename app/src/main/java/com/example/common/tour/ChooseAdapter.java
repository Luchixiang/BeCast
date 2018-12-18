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

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder> {
    private List<Chosen> chosenList;
    private Context context;
    private RecyclerView recyclerView;
    private GlideLoader glideLoader;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView chooseImg;
        TextView chooseName;

        ViewHolder(View itemView) {
            super(itemView);
            chooseImg = itemView.findViewById(R.id.choose_img);
            chooseName = itemView.findViewById(R.id.choose_text);
        }
    }

    public ChooseAdapter(List<Chosen> chosenList, Context context, RecyclerView recyclerView) {
        this.chosenList = chosenList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose, parent, false);
        glideLoader = new GlideLoader();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (chosenList != null&&chosenList.size()!=0) {
            Chosen chosen = chosenList.get(position);
            holder.chooseName.setText(chosen.getTitle());
           glideLoader.loadImage(context,chosen.getImgUrl(),holder.chooseImg);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
