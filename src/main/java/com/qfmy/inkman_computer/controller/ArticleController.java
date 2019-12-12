package com.qfmy.inkman_computer.controller;

import com.qfmy.inkman_computer.common.R;
import com.qfmy.inkman_computer.entity.Article;
import com.qfmy.inkman_computer.entity.item;
import com.qfmy.inkman_computer.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Resource
    ArticleService articleService;

    @CrossOrigin
    @RequestMapping(value = "/getArticleList/{classname}/{form}/{page}/{sort}")
    @ResponseBody
    public R getArticleList(@PathVariable("classname") String classname, @PathVariable("form") String form,
                         @PathVariable("page") String page,
                         @PathVariable("sort") String sort){
        Map<String,Object> map=new HashMap<>();
        List<Article> list=articleService.getArticleList(classname,form,page,sort);
        int count=articleService.getArticleListCount(classname,form);
        int pagecount=(count-1)/10+1;
        map.put("articlelist",list);
        map.put("pagecount",pagecount);
        return R.ok(map);
    }
}
