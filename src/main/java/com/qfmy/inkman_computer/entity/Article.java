package com.qfmy.inkman_computer.entity;

public class Article {
    private int id;
    private String title;
    private String PC;
    private String url;
    private String profile;
    private String data;
    private String imgurl;
    private String form;

    public void setForm(String form) {
        this.form = form;
    }

    public String getForm() {
        return form;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPC(String PC) {
        this.PC = PC;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPC() {
        return PC;
    }

    public String getUrl() {
        return url;
    }

    public String getProfile() {
        return profile;
    }

    public String getData() {
        return data;
    }

    public String getImgurl() {
        return imgurl;
    }
}
