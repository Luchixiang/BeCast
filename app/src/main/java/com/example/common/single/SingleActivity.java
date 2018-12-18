package com.example.common.single;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.R;

import java.util.ArrayList;
import java.util.List;

import library.common.img.GlideLoader;

public class SingleActivity extends AppCompatActivity implements SingleView {
    private String feedUrl;
    private static final String TAG = "luchixiangg";
    private List<Single> singleList = new ArrayList<>();
    private GlideLoader glideLoader;
    private TextView albumTitle;
    private TextView albumDescription;
    private ImageView share;
    private ImageView subscribe;
    private RecyclerView singleRecycler;
    private String  imgUrl;
    private ImageView albumImg;
    private SingleAdapter singleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        Intent intent = getIntent();
        feedUrl = intent.getStringExtra("feedUrl");
        imgUrl = intent.getStringExtra("imgUrl");
        initView();
        SingleModel singleModel = new SingleModel(this);
        singleModel.startXml(feedUrl);
    }

    public void initView() {
         glideLoader = new GlideLoader();
         singleRecycler = findViewById(R.id.single_recycler);
         albumTitle = findViewById(R.id.album_title);
         albumDescription = findViewById(R.id.album_description);
         albumImg = findViewById(R.id.album_img);
         share = findViewById(R.id.album_share);
         subscribe = findViewById(R.id.album_subsrcibe);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        singleRecycler.setLayoutManager(layoutManager);
        singleAdapter = new SingleAdapter(singleList,this);
        singleRecycler.setAdapter(singleAdapter);
        singleAdapter.notifyDataSetChanged();
        glideLoader.loadImage(this,imgUrl,albumImg);
    }

    @Override
    public void getList(List<Single> singleList) {
        if (singleList!=null&&singleList.size()!=0) {
            this.singleList = singleList;
            singleAdapter.listChange(singleList);
            singleAdapter.notifyDataSetChanged();
        }
        else {
        }
    }

    @Override
    public void setTitle(String title) {
        albumTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
    albumDescription.setText(description);
    }

    @Override
    public void setImage(String url) {
    }
}
