package com.qfmy.inkman_computer.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfmy.inkman_computer.dao.*;
import com.qfmy.inkman_computer.dao.itemDao;
import com.qfmy.inkman_computer.entity.Article;
import com.qfmy.inkman_computer.entity.item;
import com.qfmy.inkman_computer.entity.item_Article;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class itemService extends ServiceImpl<itemDao, item> {
    @Resource
    item_ArticleDao item_articleDao;
    @Resource
    itemDao itemDao;
    @Transactional(rollbackFor = Exception.class)
    public void addItem(item i)
    {
        itemDao.add(i);
        for (Article a : i.getArticelList())
        {
            item_Article ia=new item_Article();
            ia.setArticleId(a.getId());
            ia.setItemId(i.getId());
            item_articleDao.insert(ia);
        }
    }

    public item getItemBy(int id)
    {
        return  itemDao.selectById(id);
    }

    public List<String> getMakeList(String classname)
    {
       return itemDao.getMakeList(classname);
    }
    public List<item> getItemList(String classname,String make,String min,String max,String page,String sort)
    {

        return itemDao.getItemList(classname,make,min,max,(Integer.parseInt(page)-1)*25,sort);
    }
    public int getItemListCount(String classname,String make,String min,String max)
    {

        return itemDao.getItemListCount(classname,make,min,max);
    }

}
