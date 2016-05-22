package com.xuewen.kidsbook.common.mysql.dao.service.impl;

import com.xuewen.kidsbook.common.book.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 16-2-21.
 */
public class BookRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setName(rs.getString("name"));
        book.setAuthor(rs.getString("author"));
        book.setDesc(rs.getString("descript"));
        book.setPrice(rs.getString("price"));
        book.setIsbn(rs.getString("isbn"));
        book.setPublishOrg(rs.getString("pub_org"));
        book.setDDId(rs.getString("ddid"));
        book.setImgUrl(rs.getString("img_url"));
        book.setWordsNum(rs.getLong("words_num"));
        book.setPages(rs.getInt("pages_num"));

        return book;
    }
}
