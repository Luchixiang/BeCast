package com.example.common.local;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.common.R;
import com.example.common.model.Model;
import com.example.common.single.Single;
import com.example.common.single.SingleAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import library.common.base.BaseActivity;
import library.common.base.BaseApplication;

public class LocalActivity extends BaseActivity {
    private static final String TAG = "hujiwen";
    private RecyclerView localRecycler;
    private SingleAdapter localSingleAdapter;
    private List<String> fileName = new ArrayList<>();
    private List<Single> realList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        initView();
        File file = BaseApplication.getApplication().getFilesDir();
        File[] files = file.listFiles();
        Log.d(TAG, "onCreate: " + files.length);
        for (int i = 0; i < files.length; i++) {
            fileName.add(files[i].getName());
            Log.d(TAG, "fileName: " + files[i].getName());
        }
        Model.getInstance(getApplication()).getSingleList(singleList -> {
            for (Single single : singleList) {
                Log.d(TAG, "asd: " + single.getTitle());
                if (fileName.contains(single.getTitle())) {
                     single.setFile(files[fileName.indexOf(single.getTitle())]);
                    realList.add(single);
                }
            }
            localSingleAdapter.listChange(realList);
            localSingleAdapter.notifyDataSetChanged();
        });
    }

    void initView() {
        localRecycler = findViewById(R.id.local_recycler);
        localSingleAdapter = new SingleAdapter(realList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        localRecycler.setLayoutManager(linearLayoutManager);
        localRecycler.setAdapter(localSingleAdapter);
        localSingleAdapter.notifyDataSetChanged();
        localSingleAdapter.setIsLoadMore();
    }
}
