package com.springboot.pos.model;

/**
 * User实体类
 * @author piisabutcher
 * @Date
 */
public class User {
    private String userId;
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
