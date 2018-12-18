package com.example.common.tour;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.R;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    List<Rank> rankList;
    RecyclerView recyclerView;
    Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rankTitle;
        private TextView rankName;
        public ViewHolder(View itemView) {
            super(itemView);
            rankName = itemView.findViewById(R.id.rank_name);
            rankTitle = itemView.findViewById(R.id.rank_title);
        }
    }

    public RankAdapter(List<Rank> rankList, RecyclerView recyclerView, Context context) {
        this.rankList = rankList;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rank, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (rankList!=null&&rankList.size()!=0)
        {
            Rank rank = rankList.get(position);
            holder.rankTitle.setText(rank.getCollectionName());
            holder.rankName.setText(rank.getPrimaryGenreName());
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
