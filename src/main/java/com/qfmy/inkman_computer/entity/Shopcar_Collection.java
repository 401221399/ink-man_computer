package com.qfmy.inkman_computer.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "shopcar_collection")
public class Shopcar_Collection {
    @TableId(value = "id")
    private int id;
    private int itemid;
    private int userid;
    private int count;
    private String type;

    public void setId(int id) {
        this.id = id;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getItemid() {
        return itemid;
    }

    public int getUserid() {
        return userid;
    }

    public int getCount() {
        return count;
    }

    public String getType() {
        return type;
    }
}
