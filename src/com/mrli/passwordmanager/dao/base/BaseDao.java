package com.mrli.passwordmanager.dao.base;

import com.mrli.passwordmanager.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */
public interface BaseDao<T> {
    // 增删改查
    void save(T entity);

    void delete(T entity);

    void update(T entity);

    T findById(Serializable id);

    List<T> findAll();

    //通用查询方法
    void executeUpdate(String queryName, Object... objects);

    void pageQuery(PageBean pageBean);

    void saveOrUpdate(T entity);

    List<T> findByCriteria(DetachedCriteria criteria);
}
