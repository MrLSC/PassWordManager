package com.mrli.passwordmanager.intercept;

import com.mrli.passwordmanager.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/4/8.
 */
public class BOSLoginIntercept extends MethodFilterInterceptor {
    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {

        //判断用户是否已经登录,没有登录返回登录页面
        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null){
            return "login";
        }

        return actionInvocation.invoke();
    }
}
