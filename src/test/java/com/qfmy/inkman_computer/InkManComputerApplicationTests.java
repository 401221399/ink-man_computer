package com.qfmy.inkman_computer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qfmy.inkman_computer.Util.CrowlUtil.*;
import com.qfmy.inkman_computer.dao.ArticleDao;
import com.qfmy.inkman_computer.entity.Article;
import com.qfmy.inkman_computer.entity.item;
import com.qfmy.inkman_computer.service.ArticleService;
import com.qfmy.inkman_computer.service.itemService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InkManComputerApplicationTests{
    @Resource
    ArticleService articleService;
    @Resource
    itemService itemService;
    @Resource
    ArticleDao ArticleDao;

    @Test
    public void Test() throws Exception {
//        Computer_Crowl c=new Computer_Crowl();
//        c.setArticleDao(ArticleDao);
//        c.setItemService(itemService);
//        c.crowl();
//        Article_Crowl ac=new Article_Crowl();
//        ac.setArticleService(this.articleService);
//        ac.ArticlCrowl("笔吧评测室");


//        String url="http://mp.weixin.qq.com/s?__biz=MzIxMTAyNjk0OA==&mid=2654594731&idx=1&sn=d3f178497cb410bcc56792089ce4f64a&chksm=8c96d3c0bbe15ad627a5b799b6433cb6ed237fe2a2518ca6ae87e2fb9e16ba9d068e8e98cd5b#rd";
//        new DownHtml().Test(url);
    }

}