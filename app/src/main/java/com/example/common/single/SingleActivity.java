package com.example.common.single;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.R;

import java.util.ArrayList;
import java.util.List;

import library.common.base.BaseActivity;
import library.common.img.GlideLoader;

public class SingleActivity extends BaseActivity implements SingleView {
    private String feedUrl;
    private static final String TAG = "luchixiangg";
    private final List<Single> singleList = new ArrayList<>();
    private TextView albumTitle;
    private TextView albumDescription;
    private RecyclerView singleRecycler;
    private String imgUrl;
    private ImageView errorImg;
    private TextView errorText;
    private SingleAdapter singleAdapter;
    private SingleModel singleModel;
    private int start = 0;
    private int end = 20;

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
        GlideLoader glideLoader = new GlideLoader();
        singleRecycler = findViewById(R.id.single_recycler);
        albumTitle = findViewById(R.id.album_title);
        albumDescription = findViewById(R.id.album_description);
        ImageView albumImg = findViewById(R.id.album_img);
        ImageView share = findViewById(R.id.album_share);
        ImageView subscribe = findViewById(R.id.album_subsrcibe);
        ImageView back = findViewById(R.id.more_back);
        back.setOnClickListener(v-> finish());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        singleRecycler.setLayoutManager(layoutManager);
        singleAdapter = new SingleAdapter(singleList, this);
        singleRecycler.addOnScrollListener(monScrollListener);
        singleRecycler.setAdapter(singleAdapter);
        singleAdapter.notifyDataSetChanged();
        glideLoader.loadImage(this, imgUrl, albumImg);
        errorImg = findViewById(R.id.single_error);
        errorText = findViewById(R.id.single_text);
    }

    private int mLastVisibleItemPosition;
    private final RecyclerView.OnScrollListener monScrollListener = new RecyclerView.OnScrollListener() {

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

    private void sendMoreRequest() {
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
    protected void onStop() {
        super.onStop();
        singleModel.stop();
    }

    @Override
    public void Error() {
        singleRecycler.setVisibility(View.GONE);
        errorImg.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.VISIBLE);
    }
}
