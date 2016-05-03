package com.xuewen.kidsbook.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.xuewen.kidsbook.common.mysql.dao.bean.CrowdReport;
import com.xuewen.kidsbook.common.mysql.dao.service.CrowdService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-4-30.
 */
public class CrowdReports extends ActionSupport {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private Map<String, Object> retJsonMap = new HashMap<>();

    private Long applyId;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public void setRetJsonMap(Map<String, Object> retJsonMap) {
        this.retJsonMap = retJsonMap;
    }

    public Map<String, Object> getRetJsonMap() {
        return retJsonMap;
    }

    @Override
    public String execute() {
        CrowdService crowdService = (CrowdService) applicationContext.getBean("crowdService");
        List<CrowdReport> reports = crowdService.listReports(getApplyId());

        List<Map<String, Object>> listJson = new ArrayList<>();

        for (int i = 0; i < reports.size(); ++i) {
            CrowdReport report = reports.get(i);

            Map<String, Object> reportsMap = new HashMap<>();
            reportsMap.put("id", report.getReportId());
            reportsMap.put("name", report.getTitle());
            reportsMap.put("content", report.getContent());
            reportsMap.put("author", report.getAuthor());
            reportsMap.put("zan_num", report.getZanNum());

            listJson.add(reportsMap);
        }

        retJsonMap.put("errno", "0");
        retJsonMap.put("errmsg", "SUCCESS");
        retJsonMap.put("list", listJson);

        return SUCCESS;
    }
}
