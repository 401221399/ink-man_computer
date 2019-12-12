package com.qfmy.inkman_computer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;
@TableName(value = "item")
public class item {
    @TableId(value = "id")
    private int id;
    private String name;//商品名
    private String price;//价格
    private String imglist;//图片集合json
    private String profile;//简介
    private String parameter;//属性集合 json
    private String divbox;//展示div（性能指标/基本参数）
    private String classname;//商品类型
    private String make;//品牌
    @TableField(exist = false)
    private List<Article> ArticelList;//评测集合

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getDivbox() {
        return divbox;
    }

    public void setDivbox(String divbox) {
        this.divbox = divbox;
    }

    public String getImglist() {
        return imglist;
    }

    public void setImglist(String imglist) {
        this.imglist = imglist;
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


    public String getProfile() {
        return profile;
    }

    public String getParameter() {
        return parameter;
    }
}
