package com.mrli.passwordmanager.service.impl;

import com.mrli.passwordmanager.dao.UserDao;
import com.mrli.passwordmanager.domain.User;
import com.mrli.passwordmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/30.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User login(User user) {
        return userDao.findUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public void delete(Serializable id) {
        User user = userDao.findById(id);
        userDao.delete(user);
    }

    @Override
    public void editPassword(String password, String id) {
        userDao.executeUpdate("editPassword",password,id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }
}
