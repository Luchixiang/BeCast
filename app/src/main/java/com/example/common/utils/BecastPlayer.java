package com.example.common.utils;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.SeekBar;

import com.example.common.interfaces.BecastPlayerCallBack;
import com.example.common.single.Single;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.DefaultMediaSourceEventListener;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class BecastPlayer {
    private ConcatenatingMediaSource concatenatingMediaSource;
    private DefaultDataSourceFactory dataSourceFactory;
    private boolean isPlay = false;
    private ExoPlayer mPlayer;
    private int current = 0;
    private static final String TAG = "luchixiang";
    private PlayerHandler playerHandler;
    private BecastPlayerCallBack becastPlayerCallBack;
    private static BecastPlayer INSTANCE;
    private List<Single> singleList = new ArrayList<>();

    public BecastPlayer(BecastPlayerCallBack becastPlayerCallBack) {
        this.becastPlayerCallBack = becastPlayerCallBack;
        initPlay();
        initSeekBar(becastPlayerCallBack.getSeekBar());
        INSTANCE = this;
    }

    private BecastPlayer() {
    }

    private void initPlay() {
        concatenatingMediaSource = new ConcatenatingMediaSource();
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // 生成加载媒体数据的DataSource实例。
        dataSourceFactory = new DefaultDataSourceFactory(becastPlayerCallBack.getContext(),
                Util.getUserAgent(becastPlayerCallBack.getContext(), "test"), bandwidthMeter);
        mPlayer = ExoPlayerFactory.newSimpleInstance(becastPlayerCallBack.getContext(), new DefaultTrackSelector());
        playerHandler = new PlayerHandler(Looper.getMainLooper(), becastPlayerCallBack.getSeekBar(), mPlayer, concatenatingMediaSource);
        initSeekBar(becastPlayerCallBack.getSeekBar());
        mPlayer.setPlayWhenReady(false);
        playerHandler.sendEmptyMessage(0);
        mPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_ENDED:
                        Log.d(TAG, "Playback ended!");
                        //Stop playback and return to start position
                        mPlayer.setPlayWhenReady(false);
                        isPlay = false;
                        mPlayer.seekTo(0);
                        break;
                    case Player.STATE_READY:
                        Log.d(TAG, "ready ");
                        break;
                    case Player.STATE_BUFFERING:
                        Log.d(TAG, "Playback buffering!");
                        break;
                    case Player.STATE_IDLE:
                        Log.d(TAG, "ExoPlayer idle!");
                        break;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }

        });

        concatenatingMediaSource.addEventListener(playerHandler, new DefaultMediaSourceEventListener() {
            @Override
            public void onLoadStarted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
                super.onLoadStarted(windowIndex, mediaPeriodId, loadEventInfo, mediaLoadData);
                if (mPlayer.getContentPosition() == 0) {
                    current++;
                    Log.d(TAG, "current++: " + current);
                }
                becastPlayerCallBack.changeTitle(singleList.get(current - 1));
                mPlayer.setPlayWhenReady(true);
                isPlay = true;
            }
        });
    }

    public void initSeekBar(SeekBar seekBar) {
        playerHandler.changeSeekBar(seekBar);
        //seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlayer.seekTo((long) seekBar.getProgress());
            }
        });
    }

    private static class PlayerHandler extends Handler {
        WeakReference oneseekBars;
        final WeakReference players;
        final WeakReference sources;

        PlayerHandler(Looper looper, SeekBar oneseekBar, ExoPlayer exoPlayer, ConcatenatingMediaSource concatenatingMediaSource) {
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
                    if (source.getSize() != 0) {
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

    public void lastMusic() {
        Uri uri;
        MediaSource mediaSource;
        if (current > 1) {
            current = current - 2;
            String url = singleList.get(current).getVioiceUrl();
            uri = Uri.parse(url);
            mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            concatenatingMediaSource.addMediaSource(0, mediaSource);
            mPlayer.seekTo(0);
        }
        mPlayer.prepare(concatenatingMediaSource);
    }

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

    public void addMusic(Single single) {
        Uri uri;
        if (single.getFile() != null) {
            uri = Uri.fromFile(single.getFile());
        } else {
            uri = Uri.parse(single.getVioiceUrl());
        }
        mPlayer.setPlayWhenReady(false);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        concatenatingMediaSource.addMediaSource(0, mediaSource);
        singleList.add(current, single);
        mPlayer.prepare(concatenatingMediaSource);
    }

    public void PlayOrPause() {
        if (isPlay) {
            mPlayer.setPlayWhenReady(false);
            isPlay = false;
        } else {
            mPlayer.setPlayWhenReady(true);
            isPlay = true;
        }
    }

    public void release() {
        mPlayer.release();
        playerHandler.removeMessages(0);
    }

    public static BecastPlayer getINSTANCE() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        return new BecastPlayer();
    }
}
