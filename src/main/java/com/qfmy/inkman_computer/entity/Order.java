package com.qfmy.inkman_computer.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "order")
public class Order {
    @TableId(value = "id")
    private  int  id;
    private  int  itemid;
    private  int  userid;
    private  int  count;
    private  String  remark;
    private  String name;
    private  String phone;
    private  String address;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getRemark() {
        return remark;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
