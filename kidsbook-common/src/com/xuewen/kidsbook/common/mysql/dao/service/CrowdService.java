package com.xuewen.kidsbook.common.mysql.dao.service;

import com.xuewen.kidsbook.common.mysql.dao.bean.CrowdApply;
import com.xuewen.kidsbook.common.mysql.dao.bean.CrowdReport;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-4-30.
 */
public interface CrowdService {
    List<CrowdReport> listReports(Long apply_id);
    List<Map<String, Object>> reportOverview();

    List<CrowdApply> listApply();
}
