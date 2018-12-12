package com.example.common.first;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.common.R;

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
    private Scroller scroller;
    private Intent intent = new Intent();
    private static final String TAG = "luchixiang";

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

    public void initView(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.plyayer_view, this, true);
        glideLoader = new GlideLoader();
        playerImg = findViewById(R.id.player_view_img);
        playerTitle = findViewById(R.id.player_view_title);
        playerSeekbar = findViewById(R.id.player_view_seekbar);
        playerTime = findViewById(R.id.player_view_time);
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

    public SeekBar getSeekbar() {
        return this.playerSeekbar;
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
                    intent.setAction("com.example.change");
                    context.sendBroadcast(intent);
                }
                //向上划 改变
                if (distanceY <= -50) {
                    scrollBy(distanceX, 0);
                    intent.setAction("com.example.changeReciever");
                    context.sendBroadcast(intent);
                    //向左滑Tab
                } else if (distanceX <= -90) {
                    scrollBy(distanceX, 0);
                    intent.setAction("com.example.changeTabToLeft");
                    context.sendBroadcast(intent);
                }
                //向右滑Tab
                else if (distanceX >= 90) {
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

    public static class ViewWrapper {
        private View target;

        public ViewWrapper(View view) {
            target = view;
        }

        public int getHeight() {
            Log.d("luchixiang", "getHeight: " + target.getLayoutParams().height);
            return target.getLayoutParams().height;
        }

        public void setHeight(int height) {
            target.getLayoutParams().height = height;
            target.requestLayout();
        }
    }
}
