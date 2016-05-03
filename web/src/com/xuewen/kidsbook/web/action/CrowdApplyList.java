package com.xuewen.kidsbook.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.xuewen.kidsbook.common.book.Book;
import com.xuewen.kidsbook.common.mysql.dao.bean.CrowdApply;
import com.xuewen.kidsbook.common.mysql.dao.service.BookService;
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
public class CrowdApplyList extends ActionSupport {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private Map<String, Object> retJsonMap = new HashMap<>();

    public void setRetJsonMap(Map<String, Object> retJsonMap) {
        this.retJsonMap = retJsonMap;
    }

    public Map<String, Object> getRetJsonMap() {
        return retJsonMap;
    }

    private String getBookDesc(Long bookId) {

        BookService bookService = (BookService) applicationContext.getBean("bookService");

        Book book = bookService.getBook(bookId);

        return book.getDesc();
    }

    @Override
    public String execute() {
        CrowdService crowdService = (CrowdService) applicationContext.getBean("crowdService");
        List<CrowdApply> applies = crowdService.listApply();

        List<Map<String, Object>> listJson = new ArrayList<>();

        for (int i = 0; i < applies.size(); ++i) {
            CrowdApply apply = applies.get(i);

            Map<String, Object> applyMap = new HashMap<>();
            applyMap.put("id", apply.getId());
            applyMap.put("book_name", apply.getBookName());
            applyMap.put("book_id", apply.getBookId());
            applyMap.put("book_desc", getBookDesc(apply.getBookId()));
            applyMap.put("start_time", apply.getStartTime());
            applyMap.put("end_time", apply.getEndTime());
            applyMap.put("apply_num", apply.getApplyNum());
            applyMap.put("image_url", apply.getImageUrl());
            applyMap.put("publisher", apply.getPublisher());

            listJson.add(applyMap);
        }

        retJsonMap.put("errno", "0");
        retJsonMap.put("errmsg", "SUCCESS");
        retJsonMap.put("list", listJson);

        return SUCCESS;
    }
}
