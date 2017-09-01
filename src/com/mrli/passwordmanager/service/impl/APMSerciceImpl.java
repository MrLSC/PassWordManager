package com.mrli.passwordmanager.service.impl;

import com.mrli.passwordmanager.dao.APMDao;
import com.mrli.passwordmanager.domain.PasswordManager;
import com.mrli.passwordmanager.service.APMService;
import com.mrli.passwordmanager.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */
@Service
@Transactional
public class APMSerciceImpl implements APMService {

    @Autowired
    private APMDao apmDao;

    @Override
    public void add(PasswordManager passwordManager) {
        apmDao.saveOrUpdate(passwordManager);
    }

    @Override
    public void pageQuery(PageBean pageBean, String uid) {
        apmDao.pageQuery(pageBean, uid);
    }

    @Override
    public void saveBatch(List<PasswordManager> pwms) {
        for (PasswordManager p : pwms) {
            apmDao.save(p);
        }
    }
}
