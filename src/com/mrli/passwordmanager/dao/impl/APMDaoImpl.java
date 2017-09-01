package com.mrli.passwordmanager.dao.impl;

import com.mrli.passwordmanager.dao.APMDao;
import com.mrli.passwordmanager.dao.base.impl.BaseDaoImpl;
import com.mrli.passwordmanager.domain.PasswordManager;
import com.mrli.passwordmanager.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */
@Repository
public class APMDaoImpl extends BaseDaoImpl<PasswordManager> implements APMDao {

    public void pageQuery(PageBean pageBean,String uid) {
        DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
        //总数据量----select count(*) from bc_staff
        //改变Hibernate框架发出的sql形式
        detachedCriteria.add(Restrictions.eq("user.id",uid));
        detachedCriteria.setProjection(Projections.rowCount());
        List<Long> list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
        pageBean.setTotal(list.get(0).intValue());

        //修改sql的形式为select * from ....
        detachedCriteria.setProjection(null);
        //重置表和类的映射关系
        detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
        detachedCriteria.add(Restrictions.eq("user.id",uid));
        detachedCriteria.addOrder(Order.desc("id")); //根据id倒序
        //查询当前页显示的数据集合
        int currentPage = pageBean.getCurrentPage();
        int pageSize = pageBean.getPageSize(); //查询多少条
        int start = (currentPage - 1) * pageSize; //从哪条开始
        List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, start, pageSize);
        pageBean.setRows(rows);
    }
}
