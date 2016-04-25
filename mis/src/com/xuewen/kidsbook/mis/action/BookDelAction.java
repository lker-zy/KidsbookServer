package com.xuewen.kidsbook.mis.action;

import com.xuewen.kidsbook.common.mysql.dao.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by root on 16-3-3.
 */
public class BookDelAction extends CommonJsonAction {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String execute() {
        BookService bookService = (BookService) applicationContext.getBean("bookService");

        Long id = Long.parseLong(this.id);

        bookService.delete(id);

        retJsonMap.put("errno", 0);
        return SUCCESS;
    }
}
