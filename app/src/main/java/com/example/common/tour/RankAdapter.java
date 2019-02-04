package com.example.common.tour;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
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

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private List<Bean.Results> rankList;
    private final Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView rankTitle;
        private final TextView rankName;
        private final ImageView rankImg;
        private final TextView rankNumber;

        ViewHolder(View itemView) {
            super(itemView);
            rankName = itemView.findViewById(R.id.rank_name);
            rankTitle = itemView.findViewById(R.id.rank_title);
            rankImg = itemView.findViewById(R.id.rank_img);
            rankNumber = itemView.findViewById(R.id.rank_number);
        }
    }

    RankAdapter(List<Bean.Results> rankList, Context context) {
        this.rankList = rankList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rank, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            Bean.Results results = rankList.get(position);
            Intent intent = new Intent(context, SingleActivity.class);
            intent.putExtra("feedUrl", results.getFeedUrl());
            intent.putExtra("imgUrl", results.getArtworkUrl100());
            intent.putExtra("title", results.getCollectionName());
            context.startActivity(intent);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (rankList != null && rankList.size() != 0) {
            Bean.Results results = rankList.get(position);
            holder.rankTitle.setText(results.getCollectionName());
            holder.rankName.setText(results.getPrimaryGenreName());
        }
        switch (position) {
            case 0:
                holder.rankImg.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.vector_drawable_t_1, null));
                holder.rankNumber.setVisibility(View.GONE);
                break;
            case 1:
                holder.rankImg.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.vector_drawable_t_2, null));
                holder.rankNumber.setVisibility(View.GONE);
                break;
            case 2:
                holder.rankImg.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.vector_drawable_t_3, null));
                holder.rankNumber.setVisibility(View.GONE);
                break;
            default:
                //holder.rankImg.setVisibility(View.GONE);
                holder.rankNumber.setText(String.valueOf(position + 1));
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void listChanged(List<Bean.Results> resultsList) {
        this.rankList = resultsList;
        this.notifyDataSetChanged();
    }
}
