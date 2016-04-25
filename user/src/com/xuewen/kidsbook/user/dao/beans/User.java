package com.xuewen.kidsbook.user.dao.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-3-30.
 */
public class User {
    private long id;
    private String nickname;
    private String mobilenum;
    private String email;
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String queryString() {
        String query = null;
        List<String> queryList = new ArrayList<>();

        if (getId() > 0) {
            queryList.add("id = " + getId());
        }

        if (getNickname() != null) {
            queryList.add("nickname = '" + getNickname() + "'");
        }

        if (getEmail() != null) {
            queryList.add("email = '" + getEmail() + "'");
        }

        if (getMobilenum() != null) {
            queryList.add("mobilephone = '" + getMobilenum() + "'");
        }

        query = org.apache.commons.lang.StringUtils.join(queryList, " AND ");

        System.out.println("query is: " + query);

        return query;
    }
}
