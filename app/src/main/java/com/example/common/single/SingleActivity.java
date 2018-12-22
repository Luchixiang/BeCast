package com.example.common.single;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private String imgUrl;
    private ImageView albumImg;
    private SingleAdapter singleAdapter;
    SingleModel singleModel;
    int start = 0;
    int end = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        Intent intent = getIntent();
        feedUrl = intent.getStringExtra("feedUrl");
        imgUrl = intent.getStringExtra("imgUrl");
        initView();
        singleModel = new SingleModel(this);
        singleModel.startXml(feedUrl, end, start);
    }

    public void initView() {
        glideLoader = new GlideLoader();
        singleRecycler = findViewById(R.id.single_recycler);
        albumTitle = findViewById(R.id.album_title);
        albumDescription = findViewById(R.id.album_description);
        albumImg = findViewById(R.id.album_img);
        share = findViewById(R.id.album_share);
        subscribe = findViewById(R.id.album_subsrcibe);
        ImageView back = findViewById(R.id.more_back);
        back.setOnClickListener(v-> finish());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        singleRecycler.setLayoutManager(layoutManager);
        singleAdapter = new SingleAdapter(singleList, this);
        singleRecycler.addOnScrollListener(monScrollListener);
        singleRecycler.setAdapter(singleAdapter);
        singleAdapter.notifyDataSetChanged();
        glideLoader.loadImage(this, imgUrl, albumImg);
    }

    private int mLastVisibleItemPosition;
    private RecyclerView.OnScrollListener monScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                mLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (singleAdapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItemPosition + 1 == singleAdapter.getItemCount()) {
                    //发送网络请求获取更多数据
                    sendMoreRequest();
                }
            }
        }
    };

    public void sendMoreRequest() {
        start = start + 20;
        end = end + 20;
        singleModel.startXml(feedUrl, end, start);
    }

    @Override
    public void getList(List<Single> singleList) {
        if (singleList != null && singleList.size() != 0) {
            this.singleList.addAll(singleList);
            singleAdapter.listChange(this.singleList);
            singleAdapter.notifyDataSetChanged();
        } else {
            singleAdapter.setIsLoadMore();
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

    @Override
    protected void onStop() {
        super.onStop();
        singleModel.stop();
    }
}
