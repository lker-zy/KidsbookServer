package com.xuewen.kidsbook.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.xuewen.kidsbook.common.book.Book;
import com.xuewen.kidsbook.common.book.BookReview;
import com.xuewen.kidsbook.common.mysql.dao.service.BookService;
import com.xuewen.kidsbook.common.mysql.dao.service.ReviewService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-4-27.
 */
public class EssenceDetail extends ActionSupport {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private static String imgPrefix = "http://180.76.176.227/web/img/detail/";

    private String bookId;
    private String essenceId;

    private Map<String, Object> retJsonMap = new HashMap<>();

    public String getEssenceId() {
        return essenceId;
    }

    public void setEssenceId(String essenceId) {
        this.essenceId = essenceId;
    }

    public void setRetJsonMap(Map<String, Object> retJsonMap) {
        this.retJsonMap = retJsonMap;
    }

    public Map<String, Object> getRetJsonMap() {
        return retJsonMap;
    }

    @Override
    public String execute() {
        ReviewService reviewService = (ReviewService) applicationContext.getBean("reviewService");
        BookReview bookReview = reviewService.get(Long.valueOf(essenceId));

        Long bookId = bookReview.getBookId();
        System.out.println("book id: " + bookId);
        BookService bookService = (BookService) applicationContext.getBean("bookService");
        Book book = bookService.getBook(bookId);

        retJsonMap.put("errno", "0");
        retJsonMap.put("errmsg", "SUCCESS");
        retJsonMap.put("formatText", bookReview.getContent());
        retJsonMap.put("imgUrl", imgPrefix + essenceId + ".png");
        retJsonMap.put("book", book.toJsonMap());

        return SUCCESS;
    }
}
