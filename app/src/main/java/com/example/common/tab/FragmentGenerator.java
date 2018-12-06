package com.example.common.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.common.R;
import com.example.common.first.FirstFragment;
import com.example.common.me.MeFragment;
import com.example.common.tour.TourFragment;

public class FragmentGenerator {

    public static final String[] mTabTitles = {"首页", "游览", "我的"};

    public static Fragment[] getFragments() {
        Fragment[] fragments = new Fragment[3];
        fragments[0] = FirstFragment.newInstance();
        fragments[1] = TourFragment.newInstance();
        fragments[2] = MeFragment.newInstance();
        return fragments;
    }

    public static View getTabView(Context context, int positon) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        TextView text = (TextView) view.findViewById(R.id.tab_text);
        text.setText(mTabTitles[positon]);
        return view;
    }
}
