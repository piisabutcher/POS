package com.springboot.pos.mapper;
import com.springboot.pos.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> getAllUser();

    @Select("select password from user where userId = '")
    String login(String userId);

}
