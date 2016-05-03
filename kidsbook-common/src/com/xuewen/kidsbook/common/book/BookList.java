package com.xuewen.kidsbook.common.book;

import com.xuewen.kidsbook.common.mysql.dao.service.BookService;
import com.xuewen.kidsbook.common.redis.RedisConf;
import com.xuewen.kidsbook.common.redis.RedisHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by root on 16-2-17.
 */
public class BookList {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private static String REDIS_BOOK_PREFIX = "book_";
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("mysql_bean.xml");

    private String dataSource = "MYSQL";


    private List<Book>      books = null;
    private RedisHandler    redis = null;

    private void initRedis() {
        if (redis == null) {
            RedisConf conf = new RedisConf("127.0.0.1", 6379);
            redis = new RedisHandler(conf);
        }

        redis.init();
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    private List<String> getIDList() {
        List<String> idList = new ArrayList<>();

        Set<String> books_set = this.redis.getJedis().keys(REDIS_BOOK_PREFIX + "*");
        System.out.println(books_set);

        for (Iterator<String> iter = books_set.iterator(); iter.hasNext();) {
            String id = iter.next();
            idList.add(id.split("_")[1]);
        }

        return idList;

    }

    private void getBaseInfo(Book book) {

        List<String> result = this.redis.getJedis().hmget(REDIS_BOOK_PREFIX + book.getDDId(),
                                                        "name", "author", "ddprice", "img_url");
        System.out.println("Img url : " + result.get(3));
        book.setName(result.get(0));
        book.setAuthor(result.get(1));
        book.setPrice(result.get(2));
        book.setImgUrl(result.get(3));
    }

    private List<Book> getFromRedis() {
        logger.info("get from redis");
        List<Book> list = new ArrayList<>();

        initRedis();

        List<String> idList = this.getIDList();
        logger.info("idList : " + idList);

        for (int i = 0; i < idList.size(); ++i) {
            String id = idList.get(i);

            Book book = new Book();
            book.setDDId(id);
            getBaseInfo(book);
            list.add(book);
        }

        return list;
    }

    private List<Book> getFromDB() {
        BookService bookService = (BookService) applicationContext.getBean("bookService");

        List<Book> list = bookService.getBooks();

        return list;
    }

    public List<Book> get(int start, int end) {
        logger.info("BookList get execute. start - end: " + start + " - " + end);

        List<Book> list = get();

        //logger.info("books list:" + this.books);
        if (end > list.size()) {
            end = list.size();
        }

        return list.subList(start, end);
    }

    public List<Book> get() {
        if (this.books == null) {
            if (this.dataSource.equals("MYSQL")) {
                this.books = getFromDB();
            } else {
                this.books = getFromRedis();
            }
        }

        return this.books;
    }
}
