package com.xuewen.kidsbook.user.action;

import com.opensymphony.xwork2.ActionSupport;
import com.xuewen.kidsbook.user.dao.beans.User;
import com.xuewen.kidsbook.user.dao.mysql.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-3-30.
 */
public class LoginAction extends ActionSupport {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private String mobilephone;
    private String email;
    private String password;

    private Map<String, Object> retJsonMap;

    public Map<String, Object> getRetJsonMap() {
        return retJsonMap;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String execute() {
        UserService userService = (UserService) applicationContext.getBean("UserService");
        retJsonMap = new HashMap<>();

        User user = new User();
        user.setMobilenum(getMobilephone());

        userService.getUser(user);
        if (user.getPassword().compareTo(getPassword()) != 0) {
            retJsonMap.put("errno", -1);
            retJsonMap.put("errmsg", "password invalid");
        } else {
            retJsonMap.put("errno", 0);
        }

        return SUCCESS;
    }
}
