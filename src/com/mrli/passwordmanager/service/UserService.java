package com.mrli.passwordmanager.service;

import com.mrli.passwordmanager.domain.User;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/30.
 */
public interface UserService {
    void save(User user);
    User login(User user);
    void delete(Serializable id);
    void editPassword(String password,String id);

    User findUserByUsername(String username);
}
