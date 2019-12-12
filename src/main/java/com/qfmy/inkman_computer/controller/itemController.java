package com.qfmy.inkman_computer.controller;

import com.qfmy.inkman_computer.common.R;
import com.qfmy.inkman_computer.entity.User;
import com.qfmy.inkman_computer.entity.item;
import com.qfmy.inkman_computer.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.qfmy.inkman_computer.service.itemService;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @CrossOrigin
    @RequestMapping(value = "/getMakeList/{classname}")
    @ResponseBody
    public R getMakeList(@PathVariable("classname") String classname){
        Map<String,Object> map=new HashMap<>();
        List<String> list=itemServer.getMakeList(classname);
        map.put("makelist",list);
        return R.ok(map);
    }

    @CrossOrigin
    @RequestMapping(value = "/getClassnameList")
    @ResponseBody
    public List<Map<String,String>> getClassnameList()
    {
        List<Map<String,String>> list=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        map.put("笔记本","notebook");
        list.add(map);

        map=new HashMap<>();
        map.put("cpu","cpu");
        list.add(map);

        map=new HashMap<>();
        map.put("显卡","vga");
        list.add(map);

        map=new HashMap<>();
        map.put("内存","memory");
        list.add(map);

        map=new HashMap<>();
        map.put("主板","mb");
        list.add(map);

        map=new HashMap<>();
        map.put("硬盘","harddisk");
        list.add(map);

        map=new HashMap<>();
        map.put("ssd","dianziyingpan");
        list.add(map);

        map=new HashMap<>();
        map.put("电源","power");
        list.add(map);

        map=new HashMap<>();
        map.put("散热","sanre");
        list.add(map);

        map=new HashMap<>();
        map.put("机箱","case");
        list.add(map);

        return list;
    }

    @CrossOrigin
    @RequestMapping(value = "/getItemList/{classname}/{make}/{min}/{max}/{page}/{sort}")
    @ResponseBody
    public R getItemList(@PathVariable("classname") String classname,@PathVariable("make") String make,
                         @PathVariable("min") String min,@PathVariable("max") String max,@PathVariable("page") String page,
                         @PathVariable("sort") String sort){
        Map<String,Object> map=new HashMap<>();
        List<item> list=itemServer.getItemList(classname,make,min,max,page,sort);
        int count=itemServer.getItemListCount(classname,make,min,max);
        int pagecount=(count-1)/25+1;
        map.put("itemlist",list);
        map.put("pagecount",pagecount);
        return R.ok(map);
    }

}
