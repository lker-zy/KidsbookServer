package com.xuewen.kidsbook.mis.action;

import com.xuewen.kidsbook.common.mysql.dao.bean.CrowdReport;
import com.xuewen.kidsbook.common.mysql.dao.service.CrowdService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by root on 16-5-5.
 */
public class CrowdReportAdd extends CommonJsonAction {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private Long applyId;
    private String bookId;
    private String title;
    private String author;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyIdStr) {
        this.applyId = Long.parseLong(applyIdStr);
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String execute() {
        CrowdService crowdService = (CrowdService) applicationContext.getBean("crowdService");
        CrowdReport report = new CrowdReport();
        report.setAuthor(getAuthor());
        report.setTitle(getTitle());
        report.setContent(getContent());
        report.setZanNum((long) 0);
        report.setApplyId(getApplyId());

        crowdService.addReport(report);

        getRetJsonMap().put("errno", 0);
        getRetJsonMap().put("errmsg", SUCCESS);
        getRetJsonMap().put("reportId", report.getReportId());

        return SUCCESS;
    }
}
