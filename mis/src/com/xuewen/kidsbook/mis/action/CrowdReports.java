package com.xuewen.kidsbook.mis.action;

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
public class CrowdReports extends CommonJsonAction {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private Long applyId = Long.valueOf(0);

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
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
            reportsMap.put("bookId", 0);
            reportsMap.put("title", report.getTitle());
            reportsMap.put("content", report.getContent());
            reportsMap.put("author", report.getAuthor());

            listJson.add(reportsMap);
        }

        retJsonMap.put("status", "0");
        retJsonMap.put("total", listJson.size());
        retJsonMap.put("errmsg", "SUCCESS");
        retJsonMap.put("rows", listJson);

        return SUCCESS;
    }
}
