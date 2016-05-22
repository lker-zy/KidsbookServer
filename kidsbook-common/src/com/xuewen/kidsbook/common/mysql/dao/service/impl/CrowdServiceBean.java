package com.xuewen.kidsbook.common.mysql.dao.service.impl;

import com.xuewen.kidsbook.common.mysql.dao.bean.CrowdApply;
import com.xuewen.kidsbook.common.mysql.dao.bean.CrowdReport;
import com.xuewen.kidsbook.common.mysql.dao.service.CrowdService;
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
public class CrowdServiceBean implements CrowdService {
    private static String REPORT_TABLE = "crowd_report";
    private static String APPLY_TABLE = "crowd_apply";

    private static String[] REPORT_TABLE_COLUMN_LIST = {"id", "apply_id", "status", "create_time", "author", "content", "title", "zan_num"};
    private static String[] APPLY_TABLE_COLUMN_LIST = {"id", "book_id", "status", "start_time", "end_time", "book_name", "publisher", "image_url", "apply_num"};

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<CrowdReport> listReports() {
        return listReports((long) 0);
    }

    @Override
    public List<CrowdReport> listReports(Long apply_id) {
        List<Map<String, Object>> reports;
        if (apply_id != 0) {
            reports = jdbcTemplate.queryForList(
                    "select id, title, content, author, zan_num from " + REPORT_TABLE + " where apply_id = ?",
                    new Object[]{apply_id},
                    new int[]{Types.BIGINT});
        } else {
            reports = jdbcTemplate.queryForList(
                    "select id, title, content, author, zan_num from " + REPORT_TABLE);
        }

        List<CrowdReport> results = new ArrayList<>();

        System.out.println("crowd reports result num: " + reports.size());

        for (int i = 0; i < reports.size(); ++i) {
            Map<String, Object> resultLine = reports.get(i);
            CrowdReport report = new CrowdReport();
            report.setReportId((Long) resultLine.get("id"));
            report.setTitle((String) resultLine.get("title"));
            report.setAuthor((String) resultLine.get("author"));
            report.setContent((String) resultLine.get("content"));
            report.setZanNum((Long) resultLine.get("zan_num"));

            results.add(report);
        }

        return results;
    }

    @Override
    public List<Map<String, Object>> reportOverview() {
        List<Map<String, Object>> apply_books = jdbcTemplate.queryForList("select apply.id, apply.book_name, apply.book_id, count(report.id) as count from "
                + APPLY_TABLE + " as apply , " + REPORT_TABLE + " as report where apply.id = report.apply_id group by apply.id");
        return apply_books;
    }

    @Override
    public List<CrowdApply> listApply() {
        List<Map<String, Object>> apply_books = jdbcTemplate.queryForList(
                "select id, book_name, book_id, image_url, status, publisher, apply_num, start_time, end_time from "
                        + APPLY_TABLE + " limit 8");

        List<CrowdApply> results = new ArrayList<>();

        System.out.println("result num: " + apply_books.size());

        for (int i = 0; i < apply_books.size(); ++i) {
            Map<String, Object> resultLine = apply_books.get(i);
            CrowdApply apply = new CrowdApply();
            apply.setId((Long) resultLine.get("id"));
            apply.setApplyNum((int) resultLine.get("apply_num"));
            apply.setBookName((String) resultLine.get("book_name"));
            apply.setBookId((Long) resultLine.get("book_id"));
            apply.setPublisher((String) resultLine.get("publisher"));
            apply.setImageUrl((String) resultLine.get("image_url"));
            apply.setStartTime((Timestamp) resultLine.get("start_time"));
            apply.setEndTime((Timestamp) resultLine.get("end_time"));

            results.add(apply);
        }

        return results;
    }

    @Override
    public void addReport(CrowdReport report) {
        jdbcTemplate.update("insert into " + REPORT_TABLE +
                        "(apply_id, status, author, content, title, image_url, zan_num) " + "" +
                        "values(?, ?, ?, ?, ?, ?, ?)",
                new Object[] {report.getApplyId(), 0, report.getAuthor(), report.getContent(), report.getTitle(), report.getImageUrl(), report.getZanNum()},
                new int[] {Types.BIGINT, Types.INTEGER, Types.VARCHAR, Types.LONGVARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER}
        );

    }
}
