package com.xuewen.kidsbook.common.mysql.dao.service.impl;

import com.xuewen.kidsbook.common.mysql.dao.bean.Activity;
import com.xuewen.kidsbook.common.mysql.dao.service.ActivityService;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by root on 16-4-30.
 */
public class ActivityServiceBean implements ActivityService {
    private static String TABLE = "activity";

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Activity> list() {
        List<Map<String, Object>> activities = jdbcTemplate.queryForList(
                "select id, name, content, address, content, start_time, image_url from " + TABLE + " where id > ?",
                new Object[] {0},
                new int[] {Types.INTEGER});

        List<Activity> results = new ArrayList<>();

        System.out.println("result num: " + activities.size());

        for (int i = 0; i < activities.size(); ++i) {
            Map<String, Object> resultLine = activities.get(i);
            Activity activity = new Activity();
            activity.setId((Long) resultLine.get("id"));
            activity.setName((String) resultLine.get("name"));
            activity.setAddress((String) resultLine.get("address"));
            activity.setContent((String) resultLine.get("content"));
            activity.setStartTime((Timestamp) resultLine.get("start_time"));
            activity.setImageUrl((String) resultLine.get("image_url"));

            results.add(activity);
        }

        return results;
    }
}
