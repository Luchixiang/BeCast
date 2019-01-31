package com.example.common.interfaces;

import android.content.Context;
import android.widget.SeekBar;

import com.example.common.single.Single;
import com.google.android.exoplayer2.C;

public interface BecastPlayerCallBack {
    void changeTitle(Single single);
    SeekBar getSeekBar();
    Context getContext();
    void storeInformation(Single single);
}
