package com.qfmy.inkman_computer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@TableName(value = "user")
public class User {
    @TableId(value = "id")
    private int id;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("name")
    private String name;
    @TableField("salt")
    private String salt;
    @TableField("status")//0启用，1停用
    private int status;
    @TableField("openid")//微信绑定id
    private String openid;
    // 时间类型 @JsonFormat
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("createdate")
    private Date createdate;
    @TableField("firstinfo")
    private int firstinfo;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstinfo(int firstinfo) {
        this.firstinfo = firstinfo;
    }

    public int getFirstinfo() {
        return firstinfo;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCreatedate(Date createDate) {
        this.createdate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
