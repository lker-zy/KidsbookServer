package com.xuewen.kidsbook.mis.action;

import com.xuewen.kidsbook.common.book.Book;
import com.xuewen.kidsbook.common.mysql.dao.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by root on 16-5-4.
 */
public class BooksOverview extends CommonJsonAction {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private String num;
    private String curPage;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }

    public String execute() {
        BookService bookService = (BookService) applicationContext.getBean("bookService");
        List<Book> books =  bookService.getBooks();


        return SUCCESS;
    }
}
