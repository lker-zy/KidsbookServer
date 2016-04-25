package com.xuewen.kidsbook.user.action;

import com.opensymphony.xwork2.ActionSupport;
import com.xuewen.kidsbook.user.dao.beans.User;
import com.xuewen.kidsbook.user.dao.mysql.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by root on 16-3-30.
 */
public class RegisterAction extends ActionSupport {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private Long   userID;

    private String nickname;
    private String email;
    private String mobileNum;
    private String password;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private User serializeToUser() {
        User user = new User();
        user.setEmail(getEmail());
        user.setPassword(getPassword());
        user.setMobilenum(getMobileNum());
        user.setNickname(getNickname());

        return user;
    }

    @Override
    public String execute() {
        UserService userService = (UserService) applicationContext.getBean("UserService");
        User user = serializeToUser();

        userService.createUser(user);
        setUserID(user.getId());

        return SUCCESS;
    }
}
