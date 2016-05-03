package com.xuewen.kidsbook.common.mysql.dao.service.impl;

import com.xuewen.kidsbook.common.book.BookReview;
import com.xuewen.kidsbook.common.mysql.dao.service.ReviewService;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by root on 16-3-4.
 */
public class BookReviewServiceBean implements ReviewService {
    private static String TABLE = "book_review";

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(BookReview bookReview) {
        jdbcTemplate.update("insert into " + TABLE +
                        "(book_id, title, author, content) " +
                        "values(?, ?, ?, ?)",
                new Object[] {bookReview.getBookId(), bookReview.getTitle(), bookReview.getAuthor(), bookReview.getContent()},
                new int[] {Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.LONGVARCHAR}
        );
    }

    @Override
    public void del(BookReview review) {
        logger.info("update " + TABLE + " set deleted = 1 where id = " + review.getId());
        jdbcTemplate.update("update " + TABLE + " set deleted = 1 where id = " +review.getId());
    }

    @Override
    public BookReview get(Long id) {
        BookReview bookReview = (BookReview) jdbcTemplate.queryForObject(
                "select * from " + TABLE + " where id = ?",
                new Object[] {id},
                new int[] {Types.INTEGER},
                new ReviewRowMapper()
        );

        return bookReview;
    }

    @Override
    public List<BookReview> list() {
        List<Map<String, Object>> reviews = jdbcTemplate.queryForList(
                "select id, author, book_id, title from " + TABLE + " where id > ?",
                new Object[] {0},
                new int[] {Types.INTEGER});

        List<BookReview> results = new ArrayList<>();

        System.out.println("result num: " + reviews.size());

        for (int i = 0; i < reviews.size(); ++i) {
            Map<String, Object> resultLine = reviews.get(i);
            BookReview bookReview = new BookReview();
            bookReview.setId((Long) resultLine.get("id"));
            bookReview.setAuthor((String) resultLine.get("author"));
            bookReview.setBookId((Long) resultLine.get("book_id"));
            bookReview.setTitle((String) resultLine.get("title"));

            results.add(bookReview);
        }

        return results;
    }
}
