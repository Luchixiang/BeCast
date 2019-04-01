package com.example.common.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.common.R;
import com.example.common.interfaces.PlayerListener;
import com.example.common.single.Single;
import com.lzx.starrysky.model.SongInfo;

import java.util.ArrayList;
import java.util.List;

import library.common.img.GlideLoader;

public class PlayerViewFold extends RelativeLayout {
    private ImageView playerImg;
    private TextView playerTitle;
    private SeekBar playerSeekbar;
    private TextView playerTime;
    private GlideLoader glideLoader;
    private Context context;
    private int latestX = 0;
    private int latestY = 0;
    private int distanceX = 0;
    private int distanceY = 0;
    private List<PlayerListener> listeners = new ArrayList<>();

    public PlayerViewFold(Context context) {
        super(context);
        initView(context);
    }

    public PlayerViewFold(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public PlayerViewFold(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.plyayer_view, this, true);
        glideLoader = new GlideLoader();
        playerImg = findViewById(R.id.player_view_img);
        playerTitle = findViewById(R.id.player_view_title);
        playerSeekbar = findViewById(R.id.player_view_seekbar);
        playerTime = findViewById(R.id.player_view_time);
    }

    public SeekBar getSeekbar() {
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
                if (y - latestY < 0) {
                    int delay = y - latestY;
                    distanceY = distanceY + delay;
                } else if (x != latestX) {
                    int delax = x - latestX;
                    scrollBy(-delax, 0);
                    distanceX = distanceX + delax;
                }
                break;
            case MotionEvent.ACTION_UP:
                //单纯点击则触发暂停
                if (distanceX == 0 && distanceY == 0) {
                    for (PlayerListener playerListener : listeners) {
                        playerListener.pause();
                    }
                }
                //向上划 改变
                else if (distanceY <= -50) {
                    scrollBy(distanceX, 0);
                    for (PlayerListener playerListener : listeners) {
                        playerListener.Changer();
                    }
                    //向左滑Tab
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
        if (!listeners.contains(playerListener))
            listeners.add(playerListener);
    }

    public void unRegisterListener(PlayerListener playerListener) {
        if (!listeners.contains(playerListener))
            listeners.remove(playerListener);
    }
}
