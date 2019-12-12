package com.qfmy.inkman_computer.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfmy.inkman_computer.dao.OrderDao;
import com.qfmy.inkman_computer.entity.*;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends ServiceImpl<OrderDao, Order> {
}
