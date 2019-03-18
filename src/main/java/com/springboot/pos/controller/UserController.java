package com.springboot.pos.controller;

import com.springboot.pos.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.springboot.pos.model.User;

import java.util.List;

/**
 * @author piisabutcher
 */
@Controller
@RequestMapping(value = "/user")
@ComponentScan(value = "com.spring.pos")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getAllUser")
    @ResponseBody
    private List<User> getAllUser(){
        List<User> users = userService.getAllUser();
        return users;

    }

    @RequestMapping("/login")
    @ResponseBody
    private User login(@RequestBody User user){
        System.out.println(user);
        if(user.getUserId().equals("111") && user.getPassword().equals("111")){
            System.out.println("验证成功！");
            return user;
        }else {
            return null;
        }
    }



}
