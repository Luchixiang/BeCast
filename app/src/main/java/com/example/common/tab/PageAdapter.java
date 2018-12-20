package com.example.common.tab;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;

public class PageAdapter extends FragmentPagerAdapter {
    private int num;
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, android.support.v4.app.Fragment> mfragments = new HashMap<>();
    private android.support.v4.app.Fragment[] fragments;

   public PageAdapter(FragmentManager fm, int num) {
       //luchixiang
        super(fm);
        this.num = num;
    }

    @Override
    public int getCount() {
        return num;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return createFragment(position);
    }

    private android.support.v4.app.Fragment createFragment(int pos) {
        android.support.v4.app.Fragment fragment = mfragments.get(pos);
        fragments = FragmentGenerator.getFragments();
        if (fragment == null) {
            switch (pos) {
                case 0:
                    fragment = fragments[0];
                    break;
                case 1:
                    fragment = fragments[1];
                    break;
                case 2:
                    fragment = fragments[2];
                    break;
            }
            mfragments.put(pos, fragment);
        }
        return fragment;
    }
}
