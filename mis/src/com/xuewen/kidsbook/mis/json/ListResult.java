package com.xuewen.kidsbook.mis.json;

import com.xuewen.kidsbook.common.book.Book;

import java.util.List;

/**
 * Created by root on 16-2-17.
 */
public class ListResult {
    private String status;
    private String errmsg;
    List<Book> books;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
