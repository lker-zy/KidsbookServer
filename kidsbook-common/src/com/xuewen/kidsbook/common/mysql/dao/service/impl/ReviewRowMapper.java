package com.xuewen.kidsbook.common.mysql.dao.service.impl;

import com.xuewen.kidsbook.common.book.BookReview;
import com.xuewen.kidsbook.common.book.RecommendInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 16-2-24.
 */
public class ReviewRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        BookReview bookReview = new BookReview();
        bookReview.setAuthor(resultSet.getString("author"));
        bookReview.setTitle(resultSet.getString("title"));
        bookReview.setContent(resultSet.getString("content"));
        return bookReview;
    }
}
