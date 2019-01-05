package com.example.common.interfaces;

import com.example.common.single.Single;

public interface PlayerListener {
    void Changer();
    void addSong(Single single);
    void nextSong();
    void lastSong();
    void pause();
    void detail();
    void toRight();
    void toLeft();
}
