package com.mrli.passwordmanager.dao.impl;

import com.mrli.passwordmanager.dao.UserDao;
import com.mrli.passwordmanager.dao.base.impl.BaseDaoImpl;
import com.mrli.passwordmanager.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Override
    public User findUsernameAndPassword(String username, String password) {
        String hql = "from User u where u.username = ? and u.password = ?";
        List<User> list = this.getHibernateTemplate().find(hql, username, password);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        String hql = "from User where  username = ?";
        List<User> list = this.getHibernateTemplate().find(hql, username);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}
