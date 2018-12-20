package com.example.common.single;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
@Entity(tableName = "singleHistory")
public class Single implements Serializable {
    @PrimaryKey
    @NonNull
    private String title="";
    private String updataTime;
    private String vioiceUrl;
    private String imgUrL;
    private String time;

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

    public Single(String title, String updataTime, String vioiceUrl,String  imgUrL) {
        this.title = title;
        this.updataTime = updataTime;
        this.vioiceUrl = vioiceUrl;
        this.imgUrL = imgUrL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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
}
