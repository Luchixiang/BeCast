package com.example.common.tab;

import android.animation.Animator;
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

import com.example.common.R;
import com.example.common.activities.PlayerDetailsActivity;
import com.example.common.utils.PlayerViewFold;
import com.example.common.utils.PlayerViewUnFold;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.DefaultMediaSourceEventListener;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import library.common.base.BaseActivity;

public class MainActivity extends BaseActivity {
    @SuppressLint("StaticFieldLeak")
    SimpleExoPlayer mPlayer;
    ConcatenatingMediaSource concatenatingMediaSource;
    DataSource.Factory dataSourceFactory;
    static boolean isPlay = false;
    static List<String> allUrl = new ArrayList<>();
    static int current = 0;
    PlayerViewFold playerViewFold;
    SeekBar foldseekBar;
    ChangeReceiver changeReceiver = new ChangeReceiver();
    PlayerHandler playerHandler;
    PlayerViewUnFold playerViewUnFold;
    double transtionY;
    double transtionY2;
    static boolean isFold = true;
    private static final String TAG = "luchixiang";
    private final String testUrl = "http://ws.stream.qqmusic.qq.com/M500001VfvsJ21xFqb.mp3?guid=ffffffff82def4af4b12b3cd9337d5e7&uin=346897220&vkey=6292F51E1E384E06DCBDC9AB7C49FD713D632D313AC4858BACB8DDD29067D3C601481D36E62053BF8DFEAF74C0A5CCFADD6471160CAF3E6A&fromtag=46";
    Uri playerUri = Uri.parse(testUrl);
    @BindView(R.id.viewpager)
    HonrizonViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        playerViewFold = findViewById(R.id.player_views);
        playerViewUnFold = findViewById(R.id.player_views_unfold);
        foldseekBar = playerViewFold.getSeekbar();
        transtionY = playerViewFold.getTranslationY();
        transtionY2 = playerViewUnFold.getTranslationY();
        initTab();
        initPlay();
    }

    @Override
    protected void onResume() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.changeReciever");
        intentFilter.addAction("com.example.change");
        intentFilter.addAction("com.example.detail");
        intentFilter.addAction("com.example.changeTabToLeft");
        intentFilter.addAction("com.example.changeTabToRight");
        intentFilter.addAction("com.example.next");
        intentFilter.addAction("com.example.last");
        intentFilter.addAction("com.example.add");
        registerReceiver(changeReceiver, intentFilter);
        super.onResume();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(changeReceiver);
        super.onStop();
    }

    //初始化tab
    public void initTab() {
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), 3));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab().setCustomView(FragmentGenerator.getTabView(this, i)));
        }
        viewPager.setCurrentItem(1);
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
        playerHandler = new PlayerHandler(foldseekBar, mPlayer,concatenatingMediaSource);
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
    public void PlayOrPause() {
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
    public void nextMusic() {
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

    public void lastMusic() {
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
                current = current - 1;
            }

            mPlayer.prepare(concatenatingMediaSource);
        }
    }

    public static class PlayerHandler extends Handler {
        WeakReference oneseekBars;
        WeakReference players;
        WeakReference sources;
        PlayerHandler(SeekBar oneseekBar, ExoPlayer exoPlayer,ConcatenatingMediaSource concatenatingMediaSource) {
            this.oneseekBars = new WeakReference<>(oneseekBar);
            this.players = new WeakReference<>(exoPlayer);
            this.sources = new WeakReference<>(concatenatingMediaSource);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    SeekBar seekBar = (SeekBar) oneseekBars.get();
                    ExoPlayer exoPlayer = (ExoPlayer) players.get();
                    ConcatenatingMediaSource source = (ConcatenatingMediaSource) sources.get();
                    if (source.getSize()!=0) {
                        seekBar.setMax((int) exoPlayer.getDuration());
                        seekBar.setProgress((int) exoPlayer.getCurrentPosition());
                    }
                    sendEmptyMessageDelayed(0, 300);
                }
            }
        }

        void changeSeekBar(SeekBar seekBar) {
            this.oneseekBars = new WeakReference<>(seekBar);
        }
    }

    //改变播放器的广播
    public class ChangeReceiver extends BroadcastReceiver {
        boolean hasChange;

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())) {
                //上下滑动改变view的高度
                case "com.example.changeReciever":
                    if (isFold) {
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(playerViewFold, "translationY", -30),
                                ObjectAnimator.ofFloat(playerViewFold, "alpha", 1, 0),
                                ObjectAnimator.ofFloat(playerViewUnFold, "translationY", -playerViewUnFold.getHeight() / 2),
                                ObjectAnimator.ofFloat(playerViewUnFold, "alpha", 0, 1)
                        );
                        set.setDuration(500).addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                playerViewUnFold.setTranslationY((float) transtionY2);
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
                                ObjectAnimator.ofFloat(playerViewUnFold, "translationY", playerViewUnFold.getHeight() / 2),
                                ObjectAnimator.ofFloat(playerViewUnFold, "alpha", 1, 0)
                        );
                        set.setDuration(500).addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
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
                //暂停
                case "com.example.change":
                    PlayOrPause();
                    break;
                //进入具体页面
                case "com.example.detail":
                    Intent intent1 = new Intent(MainActivity.this, PlayerDetailsActivity.class);
                    context.startActivity(intent1);
                    break;
                //下一首
                case "com.example.next":
                    nextMusic();
                    break;
                //上一首
                case "com.example.last":
                    lastMusic();
                    break;
                //添加音乐
                case "com.example.add":
                    Intent intent2 = getIntent();
                    String url = intent2.getStringExtra("URL");
                    addMusic(url);
                    break;
                //向右滑动tab
                case "com.example.changeTabToRight":
                    if (viewPager.getCurrentItem() < 3) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                    break;
                //向左滑动tab
                case "com.example.changeTabToLeft":
                    if (viewPager.getCurrentItem() > 0) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                    }
                    break;
            }
        }
    }
}

