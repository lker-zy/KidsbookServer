package com.xuewen.kidsbook.mis.action;

import com.xuewen.kidsbook.mis.gsearch.douban.DoubanBook;
import com.xuewen.kidsbook.mis.gsearch.douban.DoubanSearch;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by root on 16-3-4.
 */
public class GlobalSearchAction extends CommonJsonAction {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private String search_tag;

    public void setSearch_tag(String search_tag) {
        this.search_tag = search_tag;
    }

    private boolean validateISBN() {
        return true;
    }

    @Override
    public String execute() throws IOException {
        DoubanSearch doubanSearch = new DoubanSearch();
        DoubanBook doubanBook = doubanSearch.go(search_tag);

        Map<String, Object> book = new HashMap<>();
        book.put("name", doubanBook.getTitle());
        book.put("author", doubanBook.getAuthor());
        book.put("price", doubanBook.getPrice());
        book.put("publisher", doubanBook.getPublisher());
        book.put("publish_time", doubanBook.getPubdate());

        retJsonMap.put("errno", 0);
        retJsonMap.put("book", book);
        return SUCCESS;
    }
}
