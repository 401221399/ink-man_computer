package com.qfmy.inkman_computer.controller;

import com.qfmy.inkman_computer.common.R;
import com.qfmy.inkman_computer.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.qfmy.inkman_computer.entity.*;
import com.qfmy.inkman_computer.dao.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService UserService;


    @ResponseBody
    @RequestMapping(value = "/authentication",method = RequestMethod.GET)
    @CrossOrigin
    public Map authentication(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            Map<String,String> map=new HashMap<>();
            map.put("username",user.getUsername());
            map.put("name",user.getName());
            return map;
        }
        return null;
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R login(@RequestBody User user) {
        // 这里自己抛出自定义的异常信息
        try {
            Subject subject = SecurityUtils.getSubject();

            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

            subject.login(token);

        } catch (UnknownAccountException e) {
            return R.error("UnknownAccountException"); // 这个异常？？？弹到前台
        } catch (IncorrectCredentialsException e) {
            return R.error("账号或密码不正确");
        } catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return R.error("账户验证失败");
        }

        return R.ok();
    }

    /**
     * 退出 SecurityUtils.getSubject().logout();
     *  <a href="/logout">退出</a>
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public R logout() {
        SecurityUtils.getSubject().logout();  //当前用户退出
        return R.ok();
    }

    /**
     * 修改登录用户密码
     */
    @RequestMapping("/password")
    public R password(String password, String newPassword){

        //得到当前登录用户
        User user= (User) SecurityUtils.getSubject().getPrincipal();

        //更新密码
        boolean flag = UserService.updatePassword(user.getId(), password, newPassword);
        if(!flag){
            return R.error("原密码不正确");
        }

        return R.ok();
    }
//    /**
//     * 所有用户列表
//     */
//    @RequestMapping("/list")
//    public R list(@RequestParam Map<String, Object> params){
//        IPage<SysUser> page = sysUserService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }
//
//    /**
//     * 获取登录的用户信息
//     * getPrincipal
//     */
//    @RequestMapping("/info")
//    public R info(){
//        SysUser user= (SysUser) SecurityUtils.getSubject().getPrincipal();
//        return R.ok().put("user", user);
//    }
//    /**
//     * 用户信息
//     */
//    @RequestMapping("/info/{userId}")
//    public R info(@PathVariable("userId") Long userId){
//        SysUser user = sysUserService.getById(userId);
//        return R.ok().put("user", user);
//    }
    /**
     * 保存用户
     */
    @CrossOrigin
    @RequestMapping(value = "/save")
    @ResponseBody
    public R save(@RequestBody User user){
        UserService.saveUser(user);
        return R.ok();
    }

    @CrossOrigin
    @RequestMapping(value = "/test")
    @ResponseBody
    public R test(){
        return R.ok();
    }

    /**
     * 修改用户
     */
    @RequestMapping("/update")
    public R update(@RequestBody User user){
        UserService.update(user);
        return R.ok();
    }

    /**
     * 是否存在用户
     */
    @CrossOrigin
    @RequestMapping("/isHaveUser/{username}")
    @ResponseBody
    public R update(@PathVariable("username") String username){
        Map<String, Object> map=new HashMap<>();
        map.put("msg",UserService.isHaveUser(username));
        return R.ok(map);
    }
//
//    /**
//     * 删除用户
//     */
//    @RequestMapping("/delete")
//    public R delete(@RequestBody Long[] userIds){
////		if(ArrayUtils.contains(userIds, 1L)){
////			return R.error("系统管理员不能删除");
////		}
//        SysUser user= (SysUser) SecurityUtils.getSubject().getPrincipal();
//
//        if(ArrayUtils.contains(userIds, user.getUserId())){
//            return R.error("当前用户不能删除");
//        }
//
//        sysUserService.removeByIds(Arrays.asList(userIds));
//
//        return R.ok();
//    }
}
