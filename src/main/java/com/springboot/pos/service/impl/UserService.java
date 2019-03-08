package com.springboot.pos.service.impl;

import com.springboot.pos.mapper.UserMapper;
import com.springboot.pos.model.User;
import com.springboot.pos.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "userService")
public class UserService implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser(){
        return userMapper.getAllUser();
    }


}
