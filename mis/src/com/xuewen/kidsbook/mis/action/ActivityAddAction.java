package com.xuewen.kidsbook.mis.action;

import com.xuewen.kidsbook.common.mysql.dao.bean.Activity;
import com.xuewen.kidsbook.common.mysql.dao.service.ActivityService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by root on 16-5-4.
 */
public class ActivityAddAction extends CommonJsonAction {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private String actName;
    private String actDate;
    private String actAddress;
    private String actContact;
    private String actDetail;
    private String actImageId;

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActDate() {
        return actDate;
    }

    public void setActDate(String actDate) {
        this.actDate = actDate;
    }

    public String getActAddress() {
        return actAddress;
    }

    public void setActAddress(String actAddress) {
        this.actAddress = actAddress;
    }

    public String getActContact() {
        return actContact;
    }

    public void setActContact(String actContact) {
        this.actContact = actContact;
    }

    public String getActDetail() {
        return actDetail;
    }

    public void setActDetail(String actDetail) {
        this.actDetail = actDetail;
    }

    public String getActImageId() {
        return actImageId;
    }

    public void setActImageId(String actImageId) {
        this.actImageId = actImageId;
    }

    @Override
    public String execute() {
        ActivityService activityService = (ActivityService) applicationContext.getBean("activityService");

        String imageUrl = "http://180.76.176.227/web/img/upload/" + getActImageId() + ".png";

        Activity activity = new Activity();
        activity.setAddress(getActAddress());
        activity.setStartTime(getActDate());
        activity.setContent(getActDetail());
        activity.setContact(getActContact());
        activity.setImageUrl(imageUrl);

        activityService.add(activity);

        retJsonMap.put("errno", 0);
        return SUCCESS;
    }
}
