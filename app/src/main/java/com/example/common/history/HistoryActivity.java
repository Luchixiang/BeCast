package com.example.common.history;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.common.R;
import com.example.common.model.Model;
import com.example.common.single.Single;
import com.example.common.single.SingleAdapter;

import java.util.ArrayList;
import java.util.List;

import library.common.base.BaseActivity;

public class HistoryActivity extends BaseActivity {
    private RecyclerView historyRecycler;
    private List<Single> singleList = new ArrayList<>();
    private SingleAdapter singleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        getHistory();
    }
    public void initView()
    {
        historyRecycler = findViewById(R.id.history_recycler);
        singleAdapter = new SingleAdapter(singleList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(linearLayoutManager);
        historyRecycler.setAdapter(singleAdapter);
        singleAdapter.notifyDataSetChanged();
        singleAdapter.setIsLoadMore();
    }

    public void getHistory()
    {
       Model.getInstance(getApplication()).getSingleList(singleList -> {
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
