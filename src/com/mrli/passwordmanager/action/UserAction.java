package com.mrli.passwordmanager.action;

import com.mrli.passwordmanager.action.base.BaseAction;
import com.mrli.passwordmanager.domain.User;
import com.mrli.passwordmanager.service.UserService;
import com.mrli.passwordmanager.utils.HttpUtils;
import com.mrli.passwordmanager.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017/6/30.
 */
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
    @Autowired
    private UserService userService;

    public String save() {
        User user = getModel();
        userService.save(user);
        return "index";
    }

    private String validateCode;

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String login() {
        String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
        if (StringUtils.isNotBlank(key) && validateCode.equals(key)) {
            User user = userService.login(this.getModel());
            if (user != null) {
                ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
                return "index";
            } else {
                this.addActionError("用户名密码错误!");
            }
        } else {
            this.addActionError("验证码错误!");
        }
        return "login";
    }

    public String getValidateCode() throws IOException {

        User user = userService.findUserByUsername(this.getModel().getUsername());
        if (user != null) {
            this.addActionError("用户已存在!");
            return "register";
        }

        String code = getSix();
        ServletActionContext.getRequest().getSession().setAttribute("smsKey", code);
        String smsUrl = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";
        String accountSid = "c2ef4fa654844701a938fc3d2966fb76";
        String authtoken = "4966bc40b42a468b82b4240edecf495c";
        String smsContent = "【云星科技】验证码：" + code + "，打死都不要告诉别人哦！";
        String to = this.getModel().getUsername();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String sig = MD5Utils.md5(accountSid + authtoken + timestamp);

        Map<String, Object> params = new HashMap<>();
        params.put("accountSid", accountSid);
        params.put("smsContent", smsContent);
        params.put("to", to);
        params.put("timestamp", timestamp);
        params.put("sig", sig);

//        String result = "{\"respCode\":\"00000\",\"respDesc\":\"请求成功。\",\"failCount\":\"0\",\"failList\":[],\"smsId\":\"7b7c55aeff394a31beadb680c0447c71\"}";
        String result = HttpUtils.doPostSSL(smsUrl, params);
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(result);

        return NONE;
    }

    public String register() {

        String smsKey = (String) ServletActionContext.getRequest().getSession().getAttribute("smsKey");
        if (StringUtils.isNotBlank(smsKey) && validateCode.equals(smsKey)) {
            User user = getModel();
            userService.save(user);
        } else {
            this.addActionError("验证码错误!");
            return "register";
        }

        return "login";
    }

    public String forgetPassword(){
        String smsKey = (String) ServletActionContext.getRequest().getSession().getAttribute("smsKey");
        if (StringUtils.isNotBlank(smsKey) && validateCode.equals(smsKey)) {
            User user = getModel();
            userService.editPassword(user.getPassword(),user.getId());
        } else {
            this.addActionError("验证码错误!");
            return "register";
        }

        return "login";
    }


    /**
     * 产生随机的六位数
     *
     * @return
     */
    public String getSix() {
        Random rad = new Random();

        String result = rad.nextInt(1000000) + "";

        if (result.length() != 6) {
            return getSix();
        }
        return result;
    }
}
