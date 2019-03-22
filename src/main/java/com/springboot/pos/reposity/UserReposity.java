package com.springboot.pos.reposity;

import com.springboot.pos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserReposity extends JpaRepository<User, String> {


}
