package com.example.common.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.common.R;

import static android.view.MotionEvent.ACTION_DOWN;

public class HonrizonViewPager extends ViewPager {
    public HonrizonViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        final int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case ACTION_DOWN: {
                intercepted = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
//                int count = getChildCount();
//                View child;
//                child = getChildAt(0).findViewById(R.id.luchixaing4);
//                int[] location = new int[2];
//                child.getLocationOnScreen(location);
                intercepted =false;
//1                if (y >= location[1] && y < location[1] + child.getHeight()) {
//                    intercepted = false;
//                } else intercepted = true;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_MOVE:
            {
            }
            case ACTION_DOWN:
            {
            }
        }
        return super.onTouchEvent(ev);
    }
}
