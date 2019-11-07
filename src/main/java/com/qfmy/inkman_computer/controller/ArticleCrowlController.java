package com.qfmy.inkman_computer.controller;

import com.qfmy.inkman_computer.common.R;
import com.qfmy.inkman_computer.entity.item;
import com.qfmy.inkman_computer.service.ArticleService;
import com.qfmy.inkman_computer.service.itemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Article")
public class ArticleCrowlController {
    @Resource
    ArticleService articleService;
    @CrossOrigin
    @RequestMapping(value = "/getArticleByItemId/{id}")
    @ResponseBody
    public R getArticleByItemId(@PathVariable("id") int id){
        Map<String,Object> map =new HashMap<>();
        map.put("articleList",articleService.getArticleByItemId(id));
        return R.ok(map);
    }
}
