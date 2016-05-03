package com.xuewen.kidsbook.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.xuewen.kidsbook.common.mysql.dao.bean.Activity;
import com.xuewen.kidsbook.common.mysql.dao.service.ActivityService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-4-30.
 */
public class ActivityList extends ActionSupport {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private Map<String, Object> retJsonMap = new HashMap<>();
    private static String imgPrefix = "http://180.76.176.227/web/img/list/";

    public void setRetJsonMap(Map<String, Object> retJsonMap) {
        this.retJsonMap = retJsonMap;
    }

    public Map<String, Object> getRetJsonMap() {
        return retJsonMap;
    }

    @Override
    public String execute() {
        ActivityService activityService = (ActivityService) applicationContext.getBean("activityService");
        List<Activity> activities = activityService.list();

        List<Map<String, Object>> listJson = new ArrayList<>();

        for (int i = 0; i < activities.size(); ++i) {
            Activity activity = activities.get(i);

            Map<String, Object> activityMap = new HashMap<>();
            activityMap.put("id", activity.getId());
            activityMap.put("address", activity.getAddress());
            activityMap.put("content", activity.getContent());
            activityMap.put("name", activity.getName());
            activityMap.put("start_time", activity.getStartTime());
            activityMap.put("image_url", activity.getImageUrl());

            listJson.add(activityMap);
        }

        retJsonMap.put("errno", "0");
        retJsonMap.put("errmsg", "SUCCESS");
        retJsonMap.put("list", listJson);

        return SUCCESS;
    }
}
