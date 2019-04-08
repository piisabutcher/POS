package com.springboot.pos.reposity;

import com.springboot.pos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserReposity extends JpaRepository<User, String> {

    //按照用户名称模糊查询
    @Query(value = "select * from user where user_name like %?1%", nativeQuery = true)
    Iterable<User> findByCName(String user_name);
}
