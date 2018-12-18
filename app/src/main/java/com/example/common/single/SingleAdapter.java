package com.example.common.single;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.R;

import java.util.List;

import library.common.img.GlideLoader;

public class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.ViewHolder> {
    private List<Single> singleList;
    private Context mContext;
    private GlideLoader glideLoader;
    public SingleAdapter(List<Single> singleList, Context mContext) {
        this.singleList = singleList;
        this.mContext = mContext;
        glideLoader = new GlideLoader();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView singleTitle;
        TextView singleTime;
        ImageView singleImg;
        ViewHolder(View view)
        {
            super(view);
            singleImg = view.findViewById(R.id.single_img);
            singleTitle = view.findViewById(R.id.single_title);
            singleTime = view.findViewById(R.id.single_time);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (singleList.size()!=0)
        {
            Single single = singleList.get(position);
            holder.singleTitle.setText(single.getTitle());
            holder.singleTime.setText(single.getUpdataTime());
//            glideLoader.loadImage(mContext,single.getImgUrL(),holder.singleImg);
        }
    }
    public void listChange(List<Single> singleList)
    {
        this.singleList = singleList;
    }
    @Override
    public int getItemCount() {
        return singleList.size();
    }
}
