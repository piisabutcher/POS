package com.springboot.pos.controller;

import com.springboot.pos.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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



}
