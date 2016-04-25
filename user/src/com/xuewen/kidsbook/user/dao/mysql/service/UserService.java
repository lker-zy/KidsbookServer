package com.xuewen.kidsbook.user.dao.mysql.service;

import com.xuewen.kidsbook.user.dao.beans.User;

/**
 * Created by root on 16-3-30.
 */
public interface UserService {
    public abstract void createUser(User user);

    public abstract void getUser(User user);

    public abstract void deleteUser(User user);
    public abstract void deleteUser(Long id);
}
