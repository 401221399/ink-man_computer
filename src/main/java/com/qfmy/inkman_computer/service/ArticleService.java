package com.qfmy.inkman_computer.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfmy.inkman_computer.Util.CrowlUtil.BigSubString;
import com.qfmy.inkman_computer.dao.ArticleDao;
import com.qfmy.inkman_computer.dao.itemDao;
import com.qfmy.inkman_computer.dao.item_ArticleDao;
import com.qfmy.inkman_computer.entity.Article;
import com.qfmy.inkman_computer.entity.item;
import com.qfmy.inkman_computer.entity.item_Article;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class ArticleService extends ServiceImpl<ArticleDao, Article> {
    @Resource
    ArticleDao articleDao;
    @Resource
    item_ArticleDao item_articleDao;
    @Resource
    itemDao itemDao;
    @Transactional(rollbackFor = Exception.class)
    public void addArticle(Article a)
    {
        articleDao.add(a);
        QueryWrapper qw=new QueryWrapper<>();
        qw.eq("classname","notebook");
        List<item> list=itemDao.selectList(qw);
        for (item i:list)
        {
            if (BigSubString.isMaxSubString(a.getPc(),i.getName()))
            {
                item_Article ia=new item_Article();
                ia.setArticleId(a.getId());
                ia.setItemId(i.getId());
                item_articleDao.insert(ia);
            }
        }
    }

    public List<Article> getArticleByItemId(int itemid)
    {
        return articleDao.getArticleByItemId(itemid);
    }
}
