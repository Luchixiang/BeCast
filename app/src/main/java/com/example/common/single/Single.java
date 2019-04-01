package com.example.common.single;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.lzx.starrysky.model.SongInfo;

import java.io.File;
import java.io.Serializable;

@Entity(tableName = "singleHistory")
public class Single implements Serializable {
    @PrimaryKey
    @NonNull
    private String title = "";
    private String updataTime;
    private String vioiceUrl;
    private String imgUrL;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private boolean hasListened = false;
    private boolean isPlay = false;

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public boolean isHasListened() {
        return hasListened;
    }

    public void setHasListened(boolean hasListened) {
        this.hasListened = hasListened;
    }

    @Ignore

    private File file = null;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgUrL() {
        return imgUrL;
    }

    public void setImgUrL(String imgUrL) {
        this.imgUrL = imgUrL;
    }

    public Single(@NonNull String title, String updataTime, String vioiceUrl, String imgUrL) {
        this.title = title;
        this.updataTime = updataTime;
        this.vioiceUrl = vioiceUrl;
        this.imgUrL = imgUrL;
    }

    @Ignore
    public Single() {
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getUpdataTime() {
        return updataTime;
    }

    public void setUpdataTime(String updataTime) {
        this.updataTime = updataTime;
    }

    public String getVioiceUrl() {
        return vioiceUrl;
    }

    public void setVioiceUrl(String vioiceUrl) {
        this.vioiceUrl = vioiceUrl;
    }

    @Ignore
    public void changeFromSongInfo(SongInfo songInfo) {
        this.setUpdataTime(songInfo.getPublishTime());
        this.setTime(String.valueOf(songInfo.getDuration()));
        this.setTitle(songInfo.getSongName());
        this.setImgUrL(songInfo.getSongCover());
        this.setVioiceUrl(songInfo.getSongUrl());
        this.setId(Integer.parseInt(songInfo.getSongId()));
    }

    @Ignore
    public void changToSongInfo(SongInfo songInfo) {
        songInfo.setSongCover(this.getImgUrL());
        songInfo.setSongName(this.getTitle());
        songInfo.setDuration(Long.parseLong(this.getTime()));
        songInfo.setPublishTime(this.getUpdataTime());
        songInfo.setSongUrl(this.getVioiceUrl());
        songInfo.setSongId(String.valueOf(this.getId()));
    }
}
