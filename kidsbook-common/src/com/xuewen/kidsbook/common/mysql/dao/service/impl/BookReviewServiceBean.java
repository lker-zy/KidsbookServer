package com.xuewen.kidsbook.common.mysql.dao.service.impl;

import com.xuewen.kidsbook.common.book.Book;
import com.xuewen.kidsbook.common.book.BookReview;
import com.xuewen.kidsbook.common.mysql.dao.service.ReviewService;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Types;
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
}
