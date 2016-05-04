package com.xuewen.kidsbook.common.mysql.dao.service;

import com.xuewen.kidsbook.common.mysql.dao.bean.Activity;

import java.util.List;

/**
 * Created by root on 16-4-30.
 */
public interface ActivityService {
    List<Activity> list();
    void add(Activity activity);
}
