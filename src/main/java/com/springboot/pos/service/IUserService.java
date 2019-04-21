package com.springboot.pos.service;

import com.springboot.pos.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    User save(User user);

    Page<User> getAllUser(Pageable pageable);

    User update(User user);

    void deleteUser(User user);

    User getUserByUserId(String userId);
}
