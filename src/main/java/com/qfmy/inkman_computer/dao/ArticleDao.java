package com.qfmy.inkman_computer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfmy.inkman_computer.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleDao extends BaseMapper<Article> {
    int add(Article a);
    List<Article> getArticleByItemId(int itemid);
    List<Article> getArticleList(@Param("classname") String classname, @Param("form")String form,@Param("page")int page,@Param("sort")String sort);
    int getArticleListCount(@Param("classname") String classname, @Param("form")String form);
}
