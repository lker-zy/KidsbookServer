package com.xuewen.kidsbook.mis.action;

import com.xuewen.kidsbook.common.book.Book;
import com.xuewen.kidsbook.common.book.BookList;
import com.xuewen.kidsbook.common.mysql.dao.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-2-17.
 */
public class BookListAction extends CommonJsonAction {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private String limit;
    private String offset;
    private String search;

    private boolean needFilter;
    private int limit_int;
    private int offset_int;

    public int getLimit() {
        return limit_int;
    }

    public void setLimit(String limit) {
        this.limit = limit;
        this.limit_int = Integer.parseInt(this.limit);
    }

    public int getOffset() {
        return offset_int;
    }

    public void setOffset(String offset) {
        this.offset = offset;
        this.offset_int = Integer.parseInt(this.offset);
    }

    public void setSearch(String search) {
        this.search = search;
        if (search.length() > 0) {
            this.needFilter = true;
        }
    }

    public String getSearch() {
        return this.search;
    }

    public boolean isNeedFilter() {
        return needFilter;
    }

    public String listJson() {
        int start = getOffset();
        int end = start + getLimit();

        logger.info("start = " + start + "; end = " + end);

        BookService bs = (BookService) applicationContext.getBean("bookService");

        BookList bookList = new BookList();
        List<Book> books = bookList.get(start, end);

        List<Map<String, Object >> jsonList = new ArrayList<>();

        for (int i = 0; i < books.size(); ++i) {
            Book book  = books.get(i);
            jsonList.add(book.toJsonMap());
        }

        retJsonMap.put("status", "0");
        retJsonMap.put("errmsg", "SUCCESS");
        retJsonMap.put("rows", jsonList);
        retJsonMap.put("total", bs.count());
        return SUCCESS;
    }

}
