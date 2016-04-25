package com.xuewen.kidsbook.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.xuewen.kidsbook.common.book.Book;
import com.xuewen.kidsbook.common.book.BookList;
import com.xuewen.kidsbook.common.mysql.dao.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-3-22.
 */
public class DailyBooks extends ActionSupport {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private Map<String, Object> retJsonMap = new HashMap<>();

    public Map<String, Object> getRetJsonMap() {
        return retJsonMap;
    }

    public String list() {
        BookService bs = (BookService) applicationContext.getBean("bookService");

        BookList bookList = new BookList();
        List<Book> books = bookList.get(0, 20);

        List<Map<String, Object >> jsonList = new ArrayList<>();

        for (int i = 0; i < books.size(); ++i) {
            Book book  = books.get(i);
            jsonList.add(book.toJsonMap());
        }

        retJsonMap.put("errno", "0");
        retJsonMap.put("errmsg", "SUCCESS");
        retJsonMap.put("books", jsonList);
        retJsonMap.put("total", bs.count());

        return SUCCESS;
    }
}
