package com.example.common.single;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.common.R;

public class FootView extends RelativeLayout {
    ProgressBar footViewProgressbar;

    public FootView(Context context) {
        this(context, null);
    }

    public FootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.foot_view, this);
        footViewProgressbar = findViewById(R.id.foot_view_progressbar);
    }

    public void setData() {
        footViewProgressbar.setVisibility(GONE);
    }
}
