package com.mrli.passwordmanager.dao;

import com.mrli.passwordmanager.dao.base.BaseDao;
import com.mrli.passwordmanager.domain.User;

/**
 * Created by Administrator on 2017/6/30.
 */
public interface UserDao extends BaseDao<User> {
    User findUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);
}
