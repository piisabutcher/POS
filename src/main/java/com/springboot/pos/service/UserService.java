package com.springboot.pos.service;

import com.springboot.pos.entity.User;
import com.springboot.pos.reposity.UserReposity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;


@Service("userService")
public class UserService {
    @Resource
    private UserReposity userReposity;

    /**
     * 注册、保存对象
     * @param user
     * @return user对象
     */
    @Transactional
    public User save(User user){
        System.out.println("注册成功！");
        return userReposity.save(user);
    }

    /**
     * 查询用户列表
     * @return 对象列表
     */
    @Transactional
    public Page<User> getAllUser(Pageable pageable){
        return userReposity.findAll(pageable);
    }
    /**
     * 修改用户数据，持久化对象修改自动更新到数据库
     * @param user
     */
    @Transactional
    public void update(User user){

    }

    /**
     * 根据id查询数据
     * @return 返回id对应的user对象
     */
    public User getUserByUserId(String userId){
        System.out.println(userId);
        Optional<User> user = userReposity.findById(userId);
        //异常处理
        return user.isPresent()?user.get():null;
    }




}
