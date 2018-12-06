package com.example.common.tab;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.danikula.videocache.HttpProxyCacheServer;
import com.example.common.R;
import com.example.common.activities.PlayerDetailsActivity;
import com.example.common.first.PlayerViewFold;
import com.example.common.first.PlayerViewUnFold;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.DefaultMediaSourceEventListener;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import library.common.base.BaseActivity;
import butterknife.BindView;

public class MainActivity extends BaseActivity {
    HttpProxyCacheServer proxy;
    String proxyUrl;
    @SuppressLint("StaticFieldLeak")
     SimpleExoPlayer mPlayer;
     ConcatenatingMediaSource concatenatingMediaSource;
     DataSource.Factory dataSourceFactory;
    static boolean isPlay = false;
    static List<String> allUrl = new ArrayList<>();
    static int current = 0;
    PlayerViewFold playerViewFold;
    SeekBar foldseekBar;
    PlayerHandler playerHandler;
    PlayerViewUnFold playerViewUnFold;
    static boolean isFold = true;
    private final String testUrl = "http://ws.stream.qqmusic.qq.com/M500001VfvsJ21xFqb.mp3?guid=ffffffff82def4af4b12b3cd9337d5e7&uin=346897220&vkey=6292F51E1E384E06DCBDC9AB7C49FD713D632D313AC4858BACB8DDD29067D3C601481D36E62053BF8DFEAF74C0A5CCFADD6471160CAF3E6A&fromtag=46";
    Uri playerUri = Uri.parse(testUrl);
    @BindView(R.id.viewpager)
    HonrizonViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("首页");
        playerViewFold = findViewById(R.id.player_views);
        playerViewUnFold = findViewById(R.id.player_views_unfold);
        foldseekBar = playerViewFold.getSeekbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.changeReciever");
        intentFilter.addAction("com.example.change");
        intentFilter.addAction("com.example.detail");
        ChangeReceiver changeReceiver = new ChangeReceiver();
        registerReceiver(changeReceiver, intentFilter);
        initTab();
        initPlay();
    }

    //初始化tab
    public void initTab() {
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), 3));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                toolbar.setTitle(FragmentGenerator.mTabTitles[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab().setCustomView(FragmentGenerator.getTabView(this, i)));
        }
    }

    //初始化mediaPlayer
    public void initPlay() {
        concatenatingMediaSource = new ConcatenatingMediaSource();
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // 生成加载媒体数据的DataSource实例。
        dataSourceFactory
                = new DefaultDataSourceFactory(MainActivity.this,
                Util.getUserAgent(MainActivity.this, "test"), bandwidthMeter);
        mPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        playerHandler = new PlayerHandler(foldseekBar, mPlayer);
        initSeekBar(foldseekBar);
        mPlayer.setPlayWhenReady(false);
        addMusic(testUrl);
        mPlayer.prepare(concatenatingMediaSource);
    }

    public void initSeekBar(SeekBar seekBar) {
        playerHandler.changeSeekBar(seekBar);
        concatenatingMediaSource.addEventListener(playerHandler, new DefaultMediaSourceEventListener() {
            @Override
            public void onLoadStarted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
                super.onLoadStarted(windowIndex, mediaPeriodId, loadEventInfo, mediaLoadData);
                seekBar.setMax((int) mPlayer.getDuration());
                seekBar.setMax((int) mPlayer.getDuration());
                playerHandler.sendEmptyMessage(0);
                current++;
            }

            @Override
            public void onLoadCompleted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
                super.onLoadCompleted(windowIndex, mediaPeriodId, loadEventInfo, mediaLoadData);
            }
        });
        //seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mPlayer.seekTo((long) seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    //暂停或者播放
    public  void PlayOrPause() {
        if (isPlay) {
            mPlayer.setPlayWhenReady(false);
            isPlay = false;
        } else {
            mPlayer.setPlayWhenReady(true);
            isPlay = true;
        }
    }

    //添加队列
    public void addMusic(String url) {
        Uri uri = Uri.parse(url);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        concatenatingMediaSource.addMediaSource(mediaSource);
        allUrl.add(url);
        if (!mPlayer.getPlayWhenReady()) {
            mPlayer.setPlayWhenReady(true);
            isPlay = true;
        }
    }

    //下一首
    public  void nextMusic() {
        mPlayer.seekTo(mPlayer.getDuration());
        if (concatenatingMediaSource.getSize() != 0) {
            concatenatingMediaSource.removeMediaSource(0);
        }
        if (concatenatingMediaSource.getSize() == 0) {
            mPlayer.setPlayWhenReady(false);
            isPlay = false;
        }
        mPlayer.prepare(concatenatingMediaSource);
    }

    public  void lastMusic() {
        Uri uri;
        MediaSource mediaSource;
        mPlayer.seekTo(mPlayer.getDuration());
        if (allUrl.size() > 1) {
            int n = allUrl.size() - 1;
            String string = allUrl.get(n - 1);
            for (int i = 0; i < concatenatingMediaSource.getSize(); i++) {
                concatenatingMediaSource.removeMediaSource(i);
            }
            if (current >= 1) {
                for (int i = current - 1; i < allUrl.size(); i++) {
                     uri = Uri.parse(allUrl.get(current));
                     mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
                    concatenatingMediaSource.addMediaSource(mediaSource);
                }
                current = current-1;
            }

            mPlayer.prepare(concatenatingMediaSource);
        }
    }

    public static class PlayerHandler extends Handler {
        WeakReference oneseekBars;
        WeakReference players;

        PlayerHandler(SeekBar oneseekBar, ExoPlayer exoPlayer) {
            this.oneseekBars = new WeakReference<SeekBar>(oneseekBar);
            this.players = new WeakReference<ExoPlayer>(exoPlayer);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    SeekBar seekBar = (SeekBar) oneseekBars.get();
                    ExoPlayer exoPlayer = (ExoPlayer) players.get();
                    seekBar.setMax((int) exoPlayer.getDuration());
                    seekBar.setProgress((int) exoPlayer.getCurrentPosition());
                    sendEmptyMessageDelayed(0, 300);
                }
            }
        }

        public void changeSeekBar(SeekBar seekBar) {
            this.oneseekBars = new WeakReference<>(seekBar);
        }
    }

    //改变播放器的广播
    public class ChangeReceiver extends BroadcastReceiver {
        boolean hasChange;

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())) {
                case "com.example.changeReciever":
                    if (isFold) {
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(playerViewFold, "translationY", -30),
                                ObjectAnimator.ofFloat(playerViewFold, "alpha", 1, 0),
                                ObjectAnimator.ofFloat(playerViewUnFold, "translationY", playerViewFold.getHeight() / 2),
                                ObjectAnimator.ofFloat(playerViewUnFold, "alpha", 0, 1)
                        );
                        set.setDuration(500).addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                int x = playerViewFold.getScrollX();
                                playerViewUnFold.setScrollX(x);
                                playerViewUnFold.setVisibility(View.VISIBLE);
                                playerViewFold.setVisibility(View.GONE);
                                foldseekBar = playerViewUnFold.getSeekBar();
                                initSeekBar(foldseekBar);
                                isFold = false;
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        set.start();
                    } else {
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(playerViewFold, "translationY", 30),
                                ObjectAnimator.ofFloat(playerViewFold, "alpha", 0, 1),
                                ObjectAnimator.ofFloat(playerViewUnFold, "translationY", playerViewUnFold.getHeight()),
                                ObjectAnimator.ofFloat(playerViewUnFold, "alpha", 1, 0)
                        );
                        set.setDuration(500).addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                int x = playerViewUnFold.getScrollX();
                                playerViewFold.setScrollX(x);
                                playerViewFold.setVisibility(View.VISIBLE);
                                playerViewUnFold.setVisibility(View.GONE);
                                foldseekBar = playerViewFold.getSeekbar();
                                initSeekBar(foldseekBar);
                                isFold = true;
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        set.start();
                    }
                    break;
                case "com.example.change":
                    PlayOrPause();
                    break;
                case "com.example.detail":
                    Intent intent1 = new Intent(MainActivity.this, PlayerDetailsActivity.class);
                    context.startActivity(intent1);
                    break;
                case "com.example.next":
                    nextMusic();
                    break;
                case "com.example.last":
                    lastMusic();
                    break;
                case "com.example.add":
                    Intent intent2  = getIntent();
                    String url  =intent2.getStringExtra("URL");
                    addMusic(url);
                    break;
            }
        }
    }
}

