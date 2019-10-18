package com.qfmy.inkman_computer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfmy.inkman_computer.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao  extends BaseMapper<User> {
    User getByid(int id);
}
