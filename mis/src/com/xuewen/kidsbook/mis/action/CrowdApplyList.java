package com.xuewen.kidsbook.mis.action;

import com.xuewen.kidsbook.common.mysql.dao.bean.CrowdApply;
import com.xuewen.kidsbook.common.mysql.dao.service.CrowdService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-5-1.
 */
public class CrowdApplyList extends CommonJsonAction {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    @Override
    public String execute() {
        CrowdService crowdService = (CrowdService) applicationContext.getBean("crowdService");
        List<CrowdApply> applies = crowdService.listApply();

        List<Map<String, Object>> listJson = new ArrayList<>();

        for (int i = 0; i < applies.size(); ++i) {
            CrowdApply apply = applies.get(i);

            Map<String, Object> applyMap = new HashMap<>();
            applyMap.put("id", apply.getId());
            applyMap.put("bookId", apply.getBookId());
            applyMap.put("bookName", apply.getBookName());
            applyMap.put("applyTime", apply.getStartTime());
            applyMap.put("startTime", apply.getStartTime());
            applyMap.put("endTime", apply.getEndTime());
            applyMap.put("applyNum", apply.getApplyNum());
            applyMap.put("shareNum", apply.getApplyNum());
            applyMap.put("shareValidNum", apply.getApplyNum());

            listJson.add(applyMap);
        }

        retJsonMap.put("errmsg", "SUCCESS");
        retJsonMap.put("total", listJson.size());
        retJsonMap.put("status", "0");
        retJsonMap.put("rows", listJson);

        return SUCCESS;
    }
}
