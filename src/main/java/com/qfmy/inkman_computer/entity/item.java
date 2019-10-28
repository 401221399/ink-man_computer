package com.qfmy.inkman_computer.entity;

import java.util.List;

public class item {
    private int id;
    private String name;//商品名
    private String price;//价格
    private String imgList;//图片集合json
    private String profile;//简介
    private String parameter;//属性集合 json
    private List<Article> ArticelList;//评测集合
    private String div;//展示div（性能指标/基本参数）

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public void setArticelList(List<Article> articelList) {
        ArticelList = articelList;
    }

    public List<Article> getArticelList() {
        return ArticelList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImgList(String imgList) {
        this.imgList = imgList;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImgList() {
        return imgList;
    }

    public String getProfile() {
        return profile;
    }

    public String getParameter() {
        return parameter;
    }
}
