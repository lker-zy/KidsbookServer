package com.xuewen.kidsbook.common.mysql.dao.bean;

/**
 * Created by root on 16-4-30.
 */
public class CrowdReport {
    private Long applyId;
    private Long reportId;
    private String title;
    private String author;
    private String content;
    private String imageUrl;
    private Long zanNum;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getZanNum() {
        return zanNum;
    }

    public void setZanNum(Long zanNum) {
        this.zanNum = zanNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

