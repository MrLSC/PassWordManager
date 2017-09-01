package com.mrli.passwordmanager.action.base;

import com.mrli.passwordmanager.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    public T model;

    @Override
    public T getModel() {
        return model;
    }

    // 分页相关
    protected PageBean pageBean = new PageBean();

    public void setRows(int rows) {
        pageBean.setPageSize(rows);
    }

    public void setPage(int page) {
        pageBean.setCurrentPage(page);
    }

    /**
     * 在构造方法中动态获得实现类型，通过反射创建模型对象
     */
    public BaseAction() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        //获得实体类型
        Class<T> entityClass = (Class<T>) actualTypeArguments[0];
        //给pageBean封装查询条件
        pageBean.setDetachedCriteria(DetachedCriteria.forClass(entityClass));
        try {
            //通过反射创建对象
            model = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将分页数据转换成Json 并将结果返回
     */
    public void writePageBean2Json(PageBean pageBean, String[] excludes) throws IOException {
        writeObject2Json(pageBean, excludes);
    }

    public void writeObject2Json(Object object, String[] excludes) throws IOException {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);
        String json = jsonObject.toString();
        ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
        String date = ServletActionContext.getResponse().getHeader("Date");
        ServletActionContext.getResponse().addHeader("Last-Modified",date);
        ServletActionContext.getResponse().getWriter().print(json);
    }

    /**
     * 将list转换为json
     *
     * @param list
     * @param excludes
     * @throws IOException
     */
    public void writeList2Json(List list, String[] excludes) throws IOException {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        JSONArray jsonObject = JSONArray.fromObject(list, jsonConfig);
        String json = jsonObject.toString();
        ServletActionContext.getResponse().setContentType(
                "text/json;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(json);
    }

}
