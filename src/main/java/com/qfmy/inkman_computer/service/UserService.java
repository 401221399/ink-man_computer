package com.qfmy.inkman_computer.service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfmy.inkman_computer.common.ShiroUtils;
import com.qfmy.inkman_computer.entity.*;
import com.qfmy.inkman_computer.dao.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService extends ServiceImpl<UserDao, User> {
    //添加用户
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        user.setCreatedate(new Date());
        //sha256加密，产生随机盐
        String salt = RandomStringUtils.randomAlphanumeric(20);

        user.setSalt(salt);

        //ShiroUtils.sha256加密       //明码+盐+迭代次数=密文
        user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));

        this.save(user);
    }

    //更新用户
    @Transactional(rollbackFor = Exception.class)
    public void update(User user) {

        User userEntity = this.getById(user.getId());

        String newpwd=ShiroUtils.sha256(user.getPassword(), userEntity.getSalt());
        user.setPassword(newpwd);

        this.updateById(user);

        //保存用户与角色关系
        //UserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }
    //更新密码
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(int id, String password, String newPassword) {

        User user = this.getById(id);
        //原密码
        password = ShiroUtils.sha256(password, user.getSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, user.getSalt());

        return this.update(
                new UpdateWrapper<User>().setSql("password="+newPassword).eq("user_id", id).eq("password", password));
    }

    public boolean isHaveUser(String username) {
        User user = this.baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if(user == null) {
           return false;
        }
        else
        {
            return true;
        }
    }

    public User getUserByOpenid(String openid){
        QueryWrapper qw=new QueryWrapper<>();
        qw.eq("openid",openid);
        return this.baseMapper.selectOne(qw);
    }
    public int addopenid(String username,String password,String openid){
        QueryWrapper qw=new QueryWrapper<>();
        qw.eq("username",username);
        User u =  this.baseMapper.selectOne(qw);
        password = ShiroUtils.sha256(password, u.getSalt());
        if(password.equals(u.getPassword()))
        {
            u.setOpenid(openid);
            return this.baseMapper.updateById(u);
        }
        else
            return 0;
    }
}
