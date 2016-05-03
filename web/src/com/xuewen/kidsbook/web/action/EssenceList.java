package com.xuewen.kidsbook.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.xuewen.kidsbook.common.book.BookReview;
import com.xuewen.kidsbook.common.mysql.dao.service.ReviewService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-4-27.
 */
public class EssenceList extends ActionSupport {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private Map<String, Object> retJsonMap = new HashMap<>();
    private static String imgPrefix = "http://180.76.176.227/web/img/list/";

    public void setRetJsonMap(Map<String, Object> retJsonMap) {
        this.retJsonMap = retJsonMap;
    }

    public Map<String, Object> getRetJsonMap() {
        return retJsonMap;
    }

    @Override
    public String execute() {
        ReviewService reviewService = (ReviewService) applicationContext.getBean("reviewService");
        List<BookReview> essenceList = reviewService.list();

        List<Map<String, Object>> listJson = new ArrayList<>();

        for (int i = 0; i < essenceList.size(); ++i) {
            BookReview review = essenceList.get(i);

            Map<String, Object> reviewMap = new HashMap<>();
            reviewMap.put("id", review.getId());
            reviewMap.put("author", review.getAuthor());
            reviewMap.put("title", review.getTitle());
            reviewMap.put("img", imgPrefix + review.getId() + ".png");

            listJson.add(reviewMap);
        }

        retJsonMap.put("errno", "0");
        retJsonMap.put("errmsg", "SUCCESS");
        retJsonMap.put("list", listJson);

        return SUCCESS;
    }
}
