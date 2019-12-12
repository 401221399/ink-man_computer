package com.qfmy.inkman_computer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfmy.inkman_computer.entity.Article;
import com.qfmy.inkman_computer.entity.item;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface itemDao extends BaseMapper<item> {
    int add(item i);
    List<String> getMakeList(String classname);
    List<item> getItemList(@Param("classname") String classname, @Param("make")String make,
                           @Param("min")String min, @Param("max")String max,
                           @Param("page")int page,@Param("sort")String sort);
    int getItemListCount(@Param("classname") String classname, @Param("make")String make,
                         @Param("min")String min, @Param("max")String max);
}
