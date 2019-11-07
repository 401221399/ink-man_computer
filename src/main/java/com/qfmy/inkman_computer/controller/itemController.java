package com.qfmy.inkman_computer.controller;

import com.qfmy.inkman_computer.common.R;
import com.qfmy.inkman_computer.entity.User;
import com.qfmy.inkman_computer.entity.item;
import com.qfmy.inkman_computer.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.qfmy.inkman_computer.service.itemService;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/item")
public class itemController {
    @Resource
    itemService itemServer;

    @CrossOrigin
    @RequestMapping(value = "/getItemByid/{id}")
    @ResponseBody
    public R getItemByid(@PathVariable("id") int id){
        Map<String,Object> map=new HashMap<>();
        item i=itemServer.getItemBy(id);
        map.put("item",i);
        System.out.println(i.getName());
        return R.ok(map);
    }
}
