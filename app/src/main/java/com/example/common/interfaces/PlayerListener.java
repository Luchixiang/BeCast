package com.example.common.interfaces;

import com.lzx.starrysky.model.SongInfo;

public interface PlayerListener {
    void Changer();

    void addSong(SongInfo songInfo);

    void nextSong();

    void lastSong();

    void pause();

    void detail();

    void toRight();

    void toLeft();
}
