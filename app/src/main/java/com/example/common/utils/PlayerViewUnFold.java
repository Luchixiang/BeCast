package com.example.common.utils;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.common.R;

import library.common.img.GlideLoader;

public class PlayerViewUnFold extends RelativeLayout {
    private Context context;
    private ImageView playerImg;
    private TextView playerTitle;
    private SeekBar playerSeekbar;
    private TextView playerTime;
    private GlideLoader glideLoader;
    private ImageView lastButton;
    private ImageView nextButton;
    private ImageView pauseButton;
    private int latestX = 0;
    private int latestY = 0;
    private int distanceY = 0;
    private int distanceX = 0;
    private Intent intent = new Intent();

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

    public void initView(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.player_view_unflod, this, true);
        glideLoader = new GlideLoader();
        playerImg = findViewById(R.id.player_view_img_unfold);
        playerTitle = findViewById(R.id.player_view_title_unflod);
        playerSeekbar = findViewById(R.id.player_view_seekbar_unflod);
        playerTime = findViewById(R.id.player_view_time_unfold);
        lastButton = findViewById(R.id.last_button);
        nextButton = findViewById(R.id.next_button);
        pauseButton = findViewById(R.id.player_view_pause);
        pauseButton.setOnClickListener(v ->
                {
                    intent.setAction("com.example.change");
                    context.sendBroadcast(intent);
                }
        );
        nextButton.setOnClickListener(v -> {
            intent.setAction("com.example.next");
            context.sendBroadcast(intent);
        });
    }

    public SeekBar getSeekBar() {
        return this.playerSeekbar;
    }

    public void setPlayerTime(String s) {
        if (s != null) {
            playerTime.setText(s);
        }
    }

    public void setPlayerTitle(String s) {
        if (s != null) {
            playerTitle.setText(s);
        }
    }

    public void setImgView(String url) {
        glideLoader.loadImage(context, url, playerImg);
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
                    intent.setAction("com.example.detail");
                    context.sendBroadcast(intent);
                } else if (distanceY >= 90) {
                    scrollBy(distanceX, 0);
                    intent.setAction("com.example.changeReciever");
                    context.sendBroadcast(intent);
                } else if (distanceX <= -5) {
                    scrollBy(distanceX, 0);
                    intent.setAction("com.example.changeTabToLeft");
                    context.sendBroadcast(intent);
                }
                //向右滑Tab
                else if (distanceX >= 5) {
                    scrollBy(distanceX, 0);
                    intent.setAction("com.example.changeTabToRight");
                    context.sendBroadcast(intent);
                }
                distanceY = 0;
                distanceX = 0;
                break;
        }
        latestX = x;
        latestY = y;
        return true;
    }
}
