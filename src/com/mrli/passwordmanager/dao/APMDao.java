package com.mrli.passwordmanager.dao;

import com.mrli.passwordmanager.dao.base.BaseDao;
import com.mrli.passwordmanager.domain.PasswordManager;
import com.mrli.passwordmanager.utils.PageBean;

/**
 * Created by Administrator on 2017/7/4.
 */
public interface APMDao extends BaseDao<PasswordManager> {
    void pageQuery(PageBean pageBean, String uid);
}
