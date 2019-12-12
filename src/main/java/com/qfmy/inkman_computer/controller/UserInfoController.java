package com.qfmy.inkman_computer.controller;

import com.qfmy.inkman_computer.common.R;
import com.qfmy.inkman_computer.entity.User;
import com.qfmy.inkman_computer.entity.UserInfo;
import com.qfmy.inkman_computer.service.ArticleService;
import com.qfmy.inkman_computer.service.UserInfoService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserInfoController {
    @Resource
    UserInfoService UserInfoService;
    @CrossOrigin
    @RequestMapping(value = "/getUserInfoCount")
    @ResponseBody
    public R getUserInfoCount()
    {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            int count = UserInfoService.getUserInfoCount(user.getId());
            Map<String,Object> map=new HashMap<>();
            map.put("count",count);
            return R.ok(map);
        }
        return R.error();
    }

    @CrossOrigin
    @RequestMapping(value = "/getFirstUserInfo")
    @ResponseBody
    public R getFirstUserInfo()
    {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            UserInfo userinfo = UserInfoService.getUserInfoByID(user.getFirstinfo());
            Map<String,Object> map=new HashMap<>();
            map.put("userinfo",userinfo);
            return R.ok(map);
        }
        return R.error();
    }
}
