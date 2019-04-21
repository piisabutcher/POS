package com.springboot.pos.service.impl;

import com.springboot.pos.dao.IUserDAO;
import com.springboot.pos.entity.User;
import com.springboot.pos.service.IUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;


@Service("userService")
public class UserService implements IUserService{
    @Resource
    private IUserDAO IuserDao;

    /**
     * 注册、保存对象
     * @param user
     * @return user对象
     */
    @Transactional
    public User save(User user){
        return IuserDao.save(user);
    }

    /**
     * 查询用户列表
     * @return 对象列表
     */
    @Transactional
    public Page<User> getAllUser(Pageable pageable){
        Page<User> pages = IuserDao.findAll(pageable);
        System.out.println(pages);
        return pages;
    }

    /**
     * 修改用户数据，持久化对象修改自动更新到数据库
     * @param user
     */
    @Transactional
    public User update(User user){
        User u = getUserByUserId(user.getUserId());
        if(u.getPassword()!=null) u.setPassword(user.getPassword());
        if(u.getTel()!=null) u.setTel(user.getTel());
        if(u.getUserName()!=null) u.setUserName(user.getUserName());
        return u;
    }

    /**
     * 删除用户
     * @param user
     */
    @Transactional
    public void  deleteUser(User user){
        IuserDao.delete(user);
    }

    /**
     * 根据id查询数据
     * @return 返回id对应的user对象
     */
    public User getUserByUserId(String userId){
        Optional<User> user = IuserDao.findById(userId);
        //异常处理
        return user.isPresent()?user.get():null;
    }

}
