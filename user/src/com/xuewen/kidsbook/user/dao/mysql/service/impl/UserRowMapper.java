package com.xuewen.kidsbook.user.dao.mysql.service.impl;

import com.xuewen.kidsbook.user.dao.beans.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 16-3-30.
 */
public class UserRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();

        user.setId(resultSet.getLong("id"));
        user.setNickname(resultSet.getString("nickname"));
        user.setEmail(resultSet.getString("email"));
        user.setMobilenum(resultSet.getString("mobilephone"));
        user.setPassword(resultSet.getString("password"));

        System.out.println("user is :" + user.getId() + ", " + user.getNickname() + ", " + user.getPassword() + ", " + user.getEmail() + ", " + user.getMobilenum());

        return user;
    }
}
