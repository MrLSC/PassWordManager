package com.mrli.passwordmanager.service;

import com.mrli.passwordmanager.domain.PasswordManager;
import com.mrli.passwordmanager.utils.PageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */
public interface APMService {
    void add(PasswordManager passwordManager);

    void pageQuery(PageBean pageBean,String uid);

    void saveBatch(List<PasswordManager> pwms);
}
