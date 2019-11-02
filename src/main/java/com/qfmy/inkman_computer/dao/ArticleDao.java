package com.qfmy.inkman_computer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfmy.inkman_computer.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleDao extends BaseMapper<Article> {
    int add(Article a);
}
