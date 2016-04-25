package com.xuewen.kidsbook.common.mysql.dao.service;

import com.xuewen.kidsbook.common.book.Book;
import com.xuewen.kidsbook.common.book.BookReview;

/**
 * Created by root on 16-3-4.
 */
public interface ReviewService {
    public abstract void add(BookReview bookReview);

    public abstract void del(BookReview review);

    public abstract BookReview get(Long id);
}
