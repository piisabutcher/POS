package com.springboot.pos.controller;

import com.springboot.pos.service.UserService;
import com.springboot.pos.util.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.springboot.pos.entity.User;
import javax.annotation.Resource;

/**
 * @author piisabutcher
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    @ResponseBody
    private Result<User> login(@RequestBody User user) throws Exception{
        try {
            User u = userService.getUserByUserId(user.getUserId());
            if(u == null){
                return Result.error(204);
            }
            else{
                System.out.println("获取成功！"+u.getUserName());
                if(!user.getPassword().equals(u.getPassword())){
                    System.out.println("密码错误！");
                    return Result.error(401);
                }
            }
            return Result.success(u);
        }catch (Exception e){
            return Result.error(500);
        }
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public Result<User> register(@RequestBody User user) throws Exception{
        try{
            System.out.println(user.getUserName());
            user.setUserId(String.valueOf((int)(Math.random()*9+1)*100000));//自动生成id,待修改
            userService.save(user);
            return Result.success(user);
        }catch (Exception e){
            System.out.println("出错啦！");
            e.printStackTrace();
            return Result.error(500);
        }

    }

    /**
     * 分页获取用户列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/getUserList")
    @ResponseBody
    private Result<User> getUserList(
            @RequestParam("page")int page,
            @RequestParam("size")int size){
        page = page<0?0:page;
        Sort sort = new Sort(Sort.DEFAULT_DIRECTION,"userId");
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<User> it = userService.getAllUser(pageable);
        return Result.success(it);
    }


}
