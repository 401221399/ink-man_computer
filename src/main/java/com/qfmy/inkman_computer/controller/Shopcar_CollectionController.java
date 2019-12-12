package com.qfmy.inkman_computer.controller;


import com.qfmy.inkman_computer.common.Entity2Map;
import com.qfmy.inkman_computer.common.R;
import com.qfmy.inkman_computer.entity.Shopcar_Collection;
import com.qfmy.inkman_computer.entity.item;
import com.qfmy.inkman_computer.service.ArticleService;
import com.qfmy.inkman_computer.service.Shopcar_CollectionService;
import com.qfmy.inkman_computer.service.UserInfoService;
import com.qfmy.inkman_computer.service.itemService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/item_options")
public class Shopcar_CollectionController {
    @Resource
    Shopcar_CollectionService Shopcar_CollectionService;
    @Resource
    itemService itemService;

    @CrossOrigin
    @RequestMapping(value = "/getShopcar_CollectionList/{type}")
    @ResponseBody
    public R getShopcar_CollectionList(HttpServletRequest request,@PathVariable("type") String type, HttpServletResponse response)
    {
        int page = Integer.parseInt(request.getParameter("page"));// 当前页
        int size = Integer.parseInt(request.getParameter("limit"));// 条数
        System.out.println(page+size);
        int count=0;
        Map<String,Object> resultmap=new HashMap<>();
        List<Map> list=new ArrayList<>();
        List<Shopcar_Collection> sclist = Shopcar_CollectionService.getList(type,page,size);
        count=sclist.size();
        Map<String,Object> map;
        for (Shopcar_Collection sc:sclist)
        {
            map=new HashMap<>();
            item i=itemService.getItemBy(sc.getItemid());
            map=Entity2Map.object2Map(map,i);
            map=Entity2Map.object2Map(map,sc);
            list.add(map);
            map=null;
        }
        resultmap.put("data",list);
        resultmap.put("count", count);
        resultmap.put("code", 0);//返回码
        resultmap.put("msg", "");
        return R.ok(resultmap);
    }

    @CrossOrigin
    @RequestMapping(value = "/getShopcar_CollectionCount/{type}")
    @ResponseBody
    public R getShopcar_CollectionCount(@PathVariable("type") String type)
    {
        int count = Shopcar_CollectionService.count(type);
        Map<String,Object> resultmap=new HashMap<>();
        resultmap.put("count",count);
        return R.ok(resultmap);
    }

    @CrossOrigin
    @RequestMapping(value = "/Shopcar_Collection_ishave/{itemid}/{type}")
    @ResponseBody
    public R getShopcar_CollectionList(@PathVariable("itemid")int itemid,@PathVariable("type") String type)
    {
        Map<String,Object> map=new HashMap<>();
        boolean ishave = Shopcar_CollectionService.ishave(itemid,type);
        map.put("ishave",ishave);
        return R.ok(map);
    }

    @CrossOrigin
    @RequestMapping(value = "/save_Shopcar_Collection/")
    @ResponseBody
    public R save_Shopcar_Collection(@RequestBody Shopcar_Collection s_c)
    {
        if(SecurityUtils.getSubject().isAuthenticated())
        {
            Shopcar_CollectionService.add(s_c);
            return R.ok();
        }
        return R.error();
    }

    @CrossOrigin
    @RequestMapping(value = "/del_Shopcar_Collection/{delList}")
    @ResponseBody
    public R del_Shopcar_Collection(@PathVariable("delList") String delList)
    {
        String[] s=delList.split(",");
        List<Integer> alist=new ArrayList<>();
        for (String item:s) {
            alist.add(Integer.parseInt(item));
        }
        Shopcar_CollectionService.del(alist);
        return R.ok();
    }
}
