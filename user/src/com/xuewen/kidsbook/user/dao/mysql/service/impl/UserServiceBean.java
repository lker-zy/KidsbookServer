package com.xuewen.kidsbook.user.dao.mysql.service.impl;

import com.xuewen.kidsbook.user.dao.beans.User;
import com.xuewen.kidsbook.user.dao.mysql.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.logging.Logger;

/**
 * Created by root on 16-3-30.
 */
public class UserServiceBean implements UserService {
    private static String TABLE = "user";

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private Long lastInsertId() {
        return jdbcTemplate.queryForLong("SELECT LAST_INSERT_ID()");
    }

    @Override
    public void createUser(User user) {
        jdbcTemplate.update("insert into " + TABLE +
                        "(nickname, email, mobilephone, password) " + "" +
                        "values(?, ?, ?, ?)",
                new Object[] {user.getNickname(), user.getEmail(), user.getMobilenum(), user.getPassword()},
                new int[] {Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR}
        );

        Long id = lastInsertId();
        user.setId(id);
    }

    @Override
    public void getUser(User user) {
        /*
        user = (User) jdbcTemplate.queryForObject(
                "select * from " + TABLE + " where id = ?",
                new Object[] {user.getId()}, new int[] {Types.INTEGER},
                new UserRowMapper()
        );
        */
        User utmp = (User) jdbcTemplate.queryForObject(
                "select * from " + TABLE + " where " + user.queryString(),
                new UserRowMapper()
        );

        user.setId(utmp.getId());
        user.setNickname(utmp.getNickname());
        user.setPassword(utmp.getPassword());
        user.setEmail(utmp.getEmail());
        user.setMobilenum(utmp.getMobilenum());
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void deleteUser(Long id) {

    }
}
