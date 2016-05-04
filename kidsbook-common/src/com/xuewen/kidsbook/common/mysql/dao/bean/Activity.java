package com.xuewen.kidsbook.common.mysql.dao.bean;

import java.sql.Timestamp;

/**
 * Created by root on 16-4-30.
 */
public class Activity {
    public static int TYPE_ONLINE = 0;
    public static int TYPE_OFFLINE = 1;

    private Long id;
    private String name;
    private String content;
    private Timestamp startTime;
    private String address;
    private String imageUrl;
    private String contact;
    private int type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(String start_timestamp) {
        this.startTime = Timestamp.valueOf(start_timestamp);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
