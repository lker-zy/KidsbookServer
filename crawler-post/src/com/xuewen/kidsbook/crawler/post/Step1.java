package com.xuewen.kidsbook.crawler.post;

import com.xuewen.kidsbook.common.book.Book;
import com.xuewen.kidsbook.common.book.BookDetail;
import com.xuewen.kidsbook.common.book.BookList;

import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 16-2-23.
 */
public class Step1 {
    private List<Book> getAllBooksFromRedis() {
        BookList books = new BookList();
        books.setDataSource("REDIS");
        return books.get();
    }

    private void parseDetail(Book book) {
        BookDetail detail = new BookDetail(book.getDDId());
        detail.parse(book);
        detail.storeToDB(book);
    }

    public void execute() {
        List<Book> books = getAllBooksFromRedis();

        for (Iterator<Book> bookIter = books.iterator(); bookIter.hasNext(); ) {
            Book book = bookIter.next();
            System.out.println("BOOK Name: " + book.getName());
            this.parseDetail(book);

            break;
        }
    }

    public static void main(String [] args) {
        Step1 step1 = new Step1();
        step1.execute();
    }
}
