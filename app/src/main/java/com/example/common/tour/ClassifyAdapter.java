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

public class ClassifyAdapter extends RecyclerView.Adapter<ClassifyAdapter.ViewHolder> {
    private Context mContext;
    private RecyclerView recyclerView;
    private GlideLoader glideLoader;
    private List<Classify> mClassifyList =new ArrayList<>();
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tourTitle;
        TextView tourSubtitle;
        ImageView tourImg;

        ViewHolder(View view) {
            super(view);
            tourImg = view.findViewById(R.id.classify_img);
            tourTitle = view.findViewById(R.id.classify_title);
            tourSubtitle = view.findViewById(R.id.classify_subtitle);
        }
    }
    public ClassifyAdapter(Context mContext, List<Classify> mClassifyList, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.mClassifyList = mClassifyList;
        this.recyclerView = recyclerView;
        glideLoader =new GlideLoader();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_classifyitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!mClassifyList.isEmpty())
        {
            Classify classify =mClassifyList.get(position);
            holder.tourSubtitle.setText(classify.getArtistName());
            holder.tourTitle.setText(classify.getCollectionName());
            glideLoader.loadImage(mContext,classify.getArtworkUrl60(),holder.tourImg);
        }
    }

    @Override
    public int getItemCount() {
        return 15;
    }
}
