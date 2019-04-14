package com.example.common.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.R;
import com.example.common.interfaces.PlayerListener;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.model.SongInfo;

import java.util.ArrayList;
import java.util.List;

import library.common.http.downLoad.DownLoadObserver;
import library.common.http.downLoad.DownloadManager;
import library.common.img.GlideLoader;

public class PlayerViewUnFold extends RelativeLayout {
    private Context context;
    private ImageView playerImg;
    private TextView playerTitle;
    private SeekBar playerSeekbar;
    private TextView playerTime;
    private GlideLoader glideLoader;
    private int latestX = 0;
    private int latestY = 0;
    private int distanceY = 0;
    private int distanceX = 0;
    private List<PlayerListener> listeners = new ArrayList<>();

    public PlayerViewUnFold(Context context) {
        super(context);
        initView(context);
    }

    public PlayerViewUnFold(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PlayerViewUnFold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.player_view_unflod, this, true);
        glideLoader = new GlideLoader();
        playerImg = findViewById(R.id.player_view_img_unfold);
        playerTitle = findViewById(R.id.player_view_title_unflod);
        playerSeekbar = findViewById(R.id.player_view_seekbar_unflod);
        playerTime = findViewById(R.id.player_view_time_unfold);
        ImageView lastButton = findViewById(R.id.last_button);
        ImageView nextButton = findViewById(R.id.next_button);
        ImageView pauseButton = findViewById(R.id.player_view_pause);
        ImageView downloadButton = findViewById(R.id.download_button);
        downloadButton.setOnClickListener(v -> {
            Toast.makeText(context, "开始下载", Toast.LENGTH_LONG).show();
            SongInfo songInfo = MusicManager.getInstance().getNowPlayingSongInfo();
            Log.d("hujiewen", "initView: " + songInfo.getSongUrl());
            DownloadManager.getInstance().download(songInfo, new DownLoadObserver() {
                @Override
                public void onError(Throwable e) {
                    Toast.makeText(context, "下载失败", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onComplete() {
                    Toast.makeText(context, "下载完成", Toast.LENGTH_LONG).show();
                }
            });

        });
        pauseButton.setOnClickListener(v ->
                {
                    for (PlayerListener playerListener : listeners) {
                        playerListener.pause();
                    }
                }
        );
        nextButton.setOnClickListener(v -> {
            for (PlayerListener playerListener : listeners) {
                playerListener.nextSong();
            }
        });
        lastButton.setOnClickListener(v -> {
            for (PlayerListener playerListener : listeners) {
                playerListener.lastSong();
            }
        });
    }

    public SeekBar getSeekBar() {
        return this.playerSeekbar;
    }

    public void setView(SongInfo songInfo) {
        playerTime.setText(ChangeTime.calculateTime(String.valueOf(songInfo.getDuration())));
        playerTitle.setText(songInfo.getSongName());
        glideLoader.loadImage(context, songInfo.getSongCover(), playerImg);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                intercept = true;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        latestX = x;
        latestY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (y - latestY > 0) {
                    int delay = y - latestY;
                    distanceY = distanceY + delay;
                } else if (x != latestX) {
                    int delax = x - latestX;
                    distanceX = distanceX + delax;
                    scrollBy(-delax, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (distanceY == 0 && distanceX == 0) {
                    for (PlayerListener playerListener : listeners) {
                        playerListener.detail();
                    }
                } else if (distanceY >= 90) {
                    scrollBy(distanceX, 0);
                    for (PlayerListener playerListener : listeners) {
                        playerListener.Changer();
                    }
                } else if (distanceX <= -5) {
                    scrollBy(distanceX, 0);
                    for (PlayerListener playerListener : listeners) {
                        playerListener.toLeft();
                    }
                }
                //向右滑Tab
                else if (distanceX >= 5) {
                    scrollBy(distanceX, 0);
                    for (PlayerListener playerListener : listeners) {
                        playerListener.toRight();
                    }
                }
                distanceY = 0;
                distanceX = 0;
                break;
        }
        latestX = x;
        latestY = y;
        performClick();
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void registerListener(PlayerListener playerListener) {
        listeners.add(playerListener);
    }

    public void unRegisterListener(PlayerListener playerListener) {
        listeners.remove(playerListener);
    }
}
