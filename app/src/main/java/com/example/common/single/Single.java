package com.example.common.single;

public class Single {
    private String title;
    private String updataTime;
    private String vioiceUrl;
    private String imgUrL;

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
