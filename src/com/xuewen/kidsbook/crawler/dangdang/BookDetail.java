package com.xuewen.kidsbook.crawler.dangdang;

import com.alibaba.fastjson.JSON;
import com.xuewen.kidsbook.common.book.Book;
import com.xuewen.kidsbook.common.mysql.dao.service.BookService;
import com.xuewen.kidsbook.lib.HttpVisit;
import com.xuewen.kidsbook.lib.SystemCurl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by root on 16-2-16.
 */

public class BookDetail {
    private static String REDIS_KEY_PREFIX = "detail_";
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private String book_id  = null;
    private String content  = null;
    private Book   baseinfo = null;

    private static String base_url = "http://mapi.dangdang.com/index.php";
    Map<String, String> detail_query_param = new HashMap<>();

    private void initQueryParam() {
        this.detail_query_param.put("time_code", "29f8fe56feb60dd9cc5e91d8ac93284e");
        this.detail_query_param.put("is_abtest", "0");
        this.detail_query_param.put("img_size", "b");
        this.detail_query_param.put("pid", this.book_id);
        this.detail_query_param.put("client_version", "6.0.9");
        this.detail_query_param.put("action", "get_product");
        this.detail_query_param.put("union_id", "537-100114");
        this.detail_query_param.put("timestamp", "1455547650");
        this.detail_query_param.put("udid", "87e5b86170039e1338e8a78c6cd4c066");
        this.detail_query_param.put("expand", "1,2,3,4,5,6");
        this.detail_query_param.put("user_client", "android");
        this.detail_query_param.put("permanent_id", "20160215210106878724230811076647536");
    }

    public BookDetail(Book book) {
        this.baseinfo = book;
    }

    public BookDetail(String id) {
        this.book_id = id;
        initQueryParam();
    }

    public void fetch() {
        /*
        HttpVisit hv = new HttpVisit();
        hv.vist(this.base_url, this.detail_query_param);
        content = hv.getContent();
        */

        String [] result = null;
        try {
            result = (new SystemCurl()).visit("http://mapi.dangdang.com/index.php", this.detail_query_param);
            content = result[0];
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(content);
    }

    public void store() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set(REDIS_KEY_PREFIX + this.book_id, content);

        /*
        String test = jedis.get(REDIS_KEY_PREFIX + this.book_id);
        System.out.println(test);
        */
    }

    public void storeToDB(Book book) {
        BookService bookService = (BookService) applicationContext.getBean("bookService");

        bookService.save(book);
    }


    public static void main(String [] args) {
        BookList books = new BookList();
        Set<String> idset = books.listAll();
        for (Iterator<String> iter = idset.iterator(); iter.hasNext();) {
            String book_id = iter.next();
            BookDetail book = new BookDetail(book_id);
            book.fetch();
            book.store();
        }

        /*
        BookDetail bookDetail = new BookDetail("22791395");
        bookDetail.fetch();
        bookDetail.store();
        */
    }
}
