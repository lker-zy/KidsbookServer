package com.xuewen.kidsbook.common.mysql.dao.service.impl;

import com.xuewen.kidsbook.common.book.Book;
import com.xuewen.kidsbook.common.mysql.dao.service.BookService;
import org.springframework.asm.Type;
import org.springframework.jdbc.core.InterruptibleBatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Created by root on 16-2-21.
 */
public class BookServiceBean implements BookService {
    private static String TABLE = "books";

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long save(Book book) {
        jdbcTemplate.update("insert into " + TABLE +
                            "(name, ddid, isbn, author, descript, price, pub_org, img_url, words_num, pages_num) " + "" +
                            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                            new Object[] {book.getName(), book.getDDId(), book.getIsbn(), book.getAuthor(), book.getDesc(), book.getPrice(), book.getPublishOrg(), book.getImgUrl(), book.getWordsNum(), book.getPages()},
                            new int[] {Types.VARCHAR, Types.CHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.CHAR, Types.VARCHAR, Types.BIGINT, Types.INTEGER}
                );

        Long id = lastInsertId();
        book.setId(id);

        return id;
    }

    private Long lastInsertId() {
        return jdbcTemplate.queryForLong("SELECT LAST_INSERT_ID()");
    }

    @Override
    public void update(Book book) {
        jdbcTemplate.update("update " + TABLE + " set category=?, search_tag=? where id = ?",
                new Object[] {book.getAuthor(), book.getIsbn(), book.getId()},
                new int[] {Types.ARRAY, Types.ARRAY, Types.BIGINT});
    }

    @Override
    public void delete(Long id) {
        logger.info("update " + TABLE + " set deleted = 1 where id = " + id);
        jdbcTemplate.update("update " + TABLE + " set deleted = 1 where id = " + id);
    }

    @Override
    public Book getBook(Long id) {
        Book book = (Book) jdbcTemplate.queryForObject(
                "select * from " + TABLE + " where id = ?",
                new Object[] {id}, new int[] {Types.INTEGER},
                new BookRowMapper()
        );
        return book;
    }

    @Override
    public List<Book> getBooks() {
        logger.info("sql : select * from " + TABLE);
        List<Book> books = jdbcTemplate.query("select * from " + TABLE + " where deleted = 0", new BookRowMapper());

        System.out.println("Books is : " + books);
        return books;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForInt("select count(*) from " + TABLE + " where deleted = 0");
    }
}
