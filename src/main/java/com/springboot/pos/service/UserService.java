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
     * @return user
     */
    @Transactional
    public User update(User user){
        User user1 = this.getUserByUserId(user.getUserId());
        if(user1 == null)
            return null;
        else{
            if(user.getPassword() != null) user1.setPassword(user.getPassword());
            if(user.getGender() == 0 || user.getGender() == 1) user1.setGender(user.getGender());
            if(user.getUserName() != null) user1.setUserName(user.getUserName());
            if(user.getTel() != null) user1.setTel(user.getTel());
            return user1;
        }
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

    /**
     * 通过用户名称模糊查询用户信息
     * @param user_name
     * @return
     */
    public Iterable<User> getUserByCName(String user_name){
        Iterable<User> user = userReposity.findByCName(user_name);
        return (user != null)?user:null;
    }

    /**
     * 删除用户
     * @param userId
     */
    public void deleteUser(String userId){userReposity.deleteById(userId);}

}
