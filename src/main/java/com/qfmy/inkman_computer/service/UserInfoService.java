package com.qfmy.inkman_computer.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfmy.inkman_computer.dao.*;
import com.qfmy.inkman_computer.entity.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserInfoService extends ServiceImpl<UserInfoDao, UserInfo> {
    public UserInfo getUserInfoByID(int id)
    {
        return this.baseMapper.selectById(id);
    }

    public List<UserInfo> getUserInfoByUserID(int userid)
    {
        QueryWrapper qw=new QueryWrapper<>();
        qw.eq("userid",userid);
        return this.baseMapper.selectList(qw);
    }

    public int getUserInfoCount(int userid) {
        QueryWrapper qw=new QueryWrapper<>();
        qw.eq("userid",userid);
        return this.baseMapper.selectCount(qw);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addtUserInfo(UserInfo u){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            u.setUserid(user.getId());
        }
        this.baseMapper.insert(u);
    }

    public void delUserInfo(int id)
    {
        this.baseMapper.deleteById(id);
    }


}
