package com.qfmy.inkman_computer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfmy.inkman_computer.entity.Article;
import com.qfmy.inkman_computer.entity.item;

public interface itemDao extends BaseMapper<item> {
    int add(item i);
}
