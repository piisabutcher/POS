package com.springboot.pos.dao;

        import com.springboot.pos.entity.User;
        import com.springboot.pos.reposity.UserReposity;
        import org.springframework.data.jpa.repository.Query;

public interface IUserDAO extends UserReposity {
    //按照用户名称模糊查询
    @Query(value = "select * from user where user_name like %?1%", nativeQuery = true)
    Iterable<User> findByCName(String user_name);
}
