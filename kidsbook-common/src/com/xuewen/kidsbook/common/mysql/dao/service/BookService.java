package com.xuewen.kidsbook.common.mysql.dao.service;

import com.xuewen.kidsbook.common.book.Book;

import java.util.List;

/**
 * Created by root on 16-2-21.
 */
public interface BookService {
    // TODO: 没有必要返回 id(Long)， 生成的id直接写入book即可
    public abstract Long save(Book book);

    public abstract void update(Book book);

    public abstract Book getBook(Long id);

    public abstract List<Book> getBooks();

    public abstract int count();

    public abstract void delete(Long id);
}
