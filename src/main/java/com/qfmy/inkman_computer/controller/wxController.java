package com.qfmy.inkman_computer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfmy.inkman_computer.common.Entity2Map;
import com.qfmy.inkman_computer.common.R;
import com.qfmy.inkman_computer.entity.Shopcar_Collection;
import com.qfmy.inkman_computer.entity.User;
import com.qfmy.inkman_computer.entity.UserInfo;
import com.qfmy.inkman_computer.entity.item;
import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.qfmy.inkman_computer.service.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wx")
public class wxController {
    @Resource
    UserService UserService;
    @Resource
    UserInfoService UserInfoService;
    @Resource
    Shopcar_CollectionService Shopcar_CollectionService;
    @Resource
    itemService itemService;
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/login/{code}", method = RequestMethod.GET)
    public R login(@PathVariable("code") String code) {
        // 这里自己抛出自定义的异常信息
        try {
            String appid="wx93d635d061a7c076";
            String secret="72da2ee4029b1a6766e35224c048a112";
            String js_code=code;
            String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+js_code+"&grant_type=authorization_code";
            RawResponse resp = Requests.get(url).send();
            //System.out.println(resp.readToText());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode resNode = objectMapper.readTree(resp.readToText());
            String session = resNode.get("session_key").asText();
            String openid = resNode.get("openid").asText();

            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(openid.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            String md5openid = new BigInteger(1, md.digest()).toString(16);

            if(UserService.getUserByOpenid(md5openid)==null)
            {
                Map<String,Object> map=new HashMap<>();
                map.put("openid",md5openid);
                map.put("code",1);
                map.put("msg","未绑定");
                return R.ok(map);
            }
            else{
                Map<String,Object> map=new HashMap<>();
                map.put("openid",md5openid);
                map.put("code",0);
                map.put("msg","登陆成功");
                return R.ok(map);
            }
        } catch (Exception e) {
            return R.error(e.getMessage()); // 这个异常？？？弹到前台
        }
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/linkUser", method = RequestMethod.POST)
    public R linkUser(@RequestBody Map param)
    {
        try{
            String username= String.valueOf(param.get("username"));
            String password= String.valueOf(param.get("password"));
            String openid= String.valueOf(param.get("openid"));
            if(UserService.addopenid(username,password,openid)>0)
                return R.ok();
            else
                return R.error("绑定失败");
        }catch (Exception e){
            return R.error(e.getMessage());
        }
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getUserInfo/{openid}")
    public R getUserInfo(@PathVariable("openid") String openid)
    {
       try{
           QueryWrapper qw=new QueryWrapper<>();
           qw.eq("openid",openid);
           User u=UserService.getOne(qw);
           UserInfo info=UserInfoService.getUserInfoByID(u.getFirstinfo());
           Map<String,Object> map=new HashMap<>();
           map.put("user",u);
           map.put("info",info);
           return R.ok(map);
       }catch (Exception e)
       {
           return R.error(e.getMessage());
       }
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getShopcar_CollectionList", method = RequestMethod.POST)
    public R getShopcar_CollectionList(@RequestBody Map param)
    {
        int page = (int) param.get("page");// 当前页
        int size = (int) param.get("limit");// 条数
        String type = (String) param.get("type");// 条数
        String openid = (String) param.get("openid");// 条数
        QueryWrapper qw=new QueryWrapper<>();
        qw.eq("openid",openid);
        User u=UserService.getOne(qw);
        if(u!=null)
        {
            int count=0;
            Map<String,Object> resultmap=new HashMap<>();
            List<Map> list=new ArrayList<>();
            qw=new QueryWrapper<>();
            qw.eq("userid",u.getId());
            qw.eq("type",type);
            int current=(page-1)*size;
            Page<Shopcar_Collection> Ipage = new Page<>(current, size);
            List<Shopcar_Collection> sclist = Shopcar_CollectionService.getBaseMapper().selectPage(Ipage,qw).getRecords();
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
        else return R.error("找不到用户");

    }


}
