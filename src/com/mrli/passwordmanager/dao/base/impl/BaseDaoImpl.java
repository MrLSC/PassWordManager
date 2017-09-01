package com.mrli.passwordmanager.dao.base.impl;

import com.mrli.passwordmanager.dao.base.BaseDao;
import com.mrli.passwordmanager.utils.PageBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

    private final Class<T> entityClass;

    // 利用注解 注入sessionFactory
    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    // 构造方法中获取entityClass
    public BaseDaoImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        entityClass = (Class) actualTypeArguments[0];
    }

    @Override
    public void save(T entity) {
        this.getHibernateTemplate().save(entity);
    }

    @Override
    public void delete(T entity) {
        this.getHibernateTemplate().delete(entity);
    }

    @Override
    public void update(T entity) {
        this.getHibernateTemplate().update(entity);
    }

    @Override
    public T findById(Serializable id) {
        return this.getHibernateTemplate().get(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        String hql = "from " + entityClass.getSimpleName();
        return this.getHibernateTemplate().find(hql);
    }

    @Override
    public void executeUpdate(String queryName, Object... objects) {
        Session session = this.getSession();
        Query query = session.getNamedQuery(queryName);
        for (int i = 0; i < objects.length; i++) {
            query.setParameter(i, objects[i]);
        }
        query.executeUpdate();
    }

    @Override
    public void pageQuery(PageBean pageBean) {
        DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
        //总数据量----select count(*) from bc_staff
        //改变Hibernate框架发出的sql形式
        detachedCriteria.setProjection(Projections.rowCount());
        List<Long> list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
        pageBean.setTotal(list.get(0).intValue());

        //修改sql的形式为select * from ....
        detachedCriteria.setProjection(null);
        //重置表和类的映射关系
        detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
        //detachedCriteria.addOrder(Order.desc("id")); //根据id倒序
        //查询当前页显示的数据集合
        int currentPage = pageBean.getCurrentPage();
        int pageSize = pageBean.getPageSize(); //查询多少条
        int start = (currentPage - 1) * pageSize; //从哪条开始
        List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, start, pageSize);
        pageBean.setRows(rows);
    }

    @Override
    public void saveOrUpdate(T entity) {
        this.getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public List<T> findByCriteria(DetachedCriteria criteria) {
        return this.getHibernateTemplate().findByCriteria(criteria);
    }
}
