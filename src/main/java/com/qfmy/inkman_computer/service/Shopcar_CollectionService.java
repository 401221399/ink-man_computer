package com.qfmy.inkman_computer.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfmy.inkman_computer.common.R;
import com.qfmy.inkman_computer.dao.Shopcar_CollectionDao;
import com.qfmy.inkman_computer.dao.UserInfoDao;
import com.qfmy.inkman_computer.entity.Shopcar_Collection;
import com.qfmy.inkman_computer.entity.User;
import com.qfmy.inkman_computer.entity.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Shopcar_CollectionService extends ServiceImpl<Shopcar_CollectionDao, Shopcar_Collection> {
    @Transactional(rollbackFor = Exception.class)
    public void add(Shopcar_Collection s_c)
    {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            s_c.setUserid(user.getId());
        }
        QueryWrapper qw=new QueryWrapper<>();
        qw.eq("itemid",s_c.getItemid());
        qw.eq("userid",s_c.getUserid());
        if (this.baseMapper.selectOne(qw)==null)
            this.baseMapper.insert(s_c);
        else
        {
            Shopcar_Collection oldsc=this.baseMapper.selectOne(qw);
            oldsc.setCount(s_c.getCount()+oldsc.getCount());
            this.baseMapper.updateById(oldsc);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Shopcar_Collection s_c)
    {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            s_c.setUserid(user.getId());
        }
        this.baseMapper.updateById(s_c);
    }

    @Transactional(rollbackFor = Exception.class)
    public void del(List<Integer> ids)
    {
        if(ids!=null&&ids.size()>0)
        {
            for(Integer i:ids){
                this.baseMapper.deleteById(i);;
            }
        }
    }

    public List<Shopcar_Collection> getList(String type,int pagenum,int size)
    {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            int userid=user.getId();
            QueryWrapper qw=new QueryWrapper<>();
            qw.eq("userid",userid);
            qw.eq("type",type);
            int current=(pagenum-1)*size;
            Page<Shopcar_Collection> page = new Page<>(current, size);
            return this.baseMapper.selectPage(page,qw).getRecords();
        }
        return null;
    }

    public boolean ishave(int itemid,String type)
    {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            int userid=user.getId();
            QueryWrapper qw=new QueryWrapper<>();
            qw.eq("userid",userid);
            qw.eq("itemid",itemid);
            qw.eq("type",type);
            return this.baseMapper.selectCount(qw)>0;
        }
        return false;
    }

    public int count(String type)
    {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            int userid=user.getId();
            QueryWrapper qw=new QueryWrapper<>();
            qw.eq("userid",userid);
            qw.eq("type",type);
            return this.baseMapper.selectCount(qw);
        }
        return 0;
    }
}
