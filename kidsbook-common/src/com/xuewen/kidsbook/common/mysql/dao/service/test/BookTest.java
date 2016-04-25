package com.xuewen.kidsbook.common.mysql.dao.service.test;

import com.xuewen.kidsbook.common.book.Book;
import com.xuewen.kidsbook.common.mysql.dao.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by root on 16-2-21.
 */
public class BookTest {
    public static void main(String [] args) {
        ApplicationContext actx = new ClassPathXmlApplicationContext("bean.xml");

        BookService bookService = (BookService) actx.getBean("bookService");

        Book book = new Book();
        book.setId((long) 10);
        book.setName("lker");
        book.setDesc("lker desc");
        book.setPrice("100.00");
        book.setAuthor("lizhiyong");

        bookService.save(book);
    }
}
