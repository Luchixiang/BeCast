package com.example.common.history;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.common.R;
import com.example.common.base.BaseActivity;
import com.example.common.model.Model;
import com.example.common.single.Single;
import com.example.common.single.SingleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends BaseActivity {
    private final List<Single> songInfoList = new ArrayList<>();
    private SingleAdapter singleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        getHistory();
    }

    private void initView() {
        RecyclerView historyRecycler = findViewById(R.id.history_recycler);
        singleAdapter = new SingleAdapter(songInfoList, this);
        findViewById(R.id.history_back).setOnClickListener(v -> finish());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(linearLayoutManager);
        historyRecycler.setAdapter(singleAdapter);
        singleAdapter.notifyDataSetChanged();
        singleAdapter.setIsLoadMore();
    }

    private void getHistory() {
        Model.getInstance(getApplication()).getSingleList(singleList -> {
            Log.d("history", "getHistory: " + singleList.size());
            Collections.reverse(singleList);
            singleAdapter.listChange(singleList);
            singleAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Model.getInstance(getApplication()).stopModel();
    }
}
