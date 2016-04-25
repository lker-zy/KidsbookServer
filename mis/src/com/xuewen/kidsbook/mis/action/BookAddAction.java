package com.xuewen.kidsbook.mis.action;

import com.xuewen.kidsbook.common.book.Book;
import com.xuewen.kidsbook.common.book.BookReview;
import com.xuewen.kidsbook.common.mysql.dao.service.BookService;
import com.xuewen.kidsbook.common.mysql.dao.service.ReviewService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-3-2.
 */
public class BookAddAction extends CommonJsonAction {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private String bookId;

    private String name;
    private String series;
    private String publish_org;
    private String publish_time;
    private String author;
    private String price;
    private String category;
    private String genre;
    private String age;
    private String isbn;

    private String reviewAuthor;
    private String reviewContent;
    private String reviewTitle;

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setPublish_org(String publish_org) {
        this.publish_org = publish_org;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String base() {
        Book book = new Book();
        book.setPublishOrg(publish_org);
        book.setPublishTime(publish_time);
        book.setAuthor(author);
        book.setName(name);
        book.setPrice(price);
        book.setIsbn(isbn);

        BookService bookService = (BookService) applicationContext.getBean("bookService");
        logger.info("add new book. ");
        Long id = bookService.save(book);
        logger.info("add new book. book id is: " + id);

        Map<String, Object> new_book = new HashMap<>();
        new_book.put("id", id);

        retJsonMap.put("errno", 0);
        retJsonMap.put("book", new_book);
        return SUCCESS;
    }

    public String review() {
        BookReview bookReview = new BookReview();
        bookReview.setAuthor(reviewAuthor);
        bookReview.setContent(reviewContent);
        bookReview.setTitle(reviewTitle);
        bookReview.setBookId(Long.parseLong(bookId));

        logger.info("author: " + reviewAuthor + "; title : " + reviewTitle + "; content: " + reviewContent + "; book id: " + bookId);
        ReviewService reviewService = (ReviewService) applicationContext.getBean("reviewService");
        reviewService.add(bookReview);

        retJsonMap.put("errno", 0);
        return SUCCESS;
    }
}
