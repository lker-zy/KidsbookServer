package com.xuewen.kidsbook.common.book;

/**
 * Created by root on 16-2-17.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xuewen.kidsbook.common.mysql.dao.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by root on 16-2-16.
 */

class PublishInfoItemContent {
    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

class BookDetailInfo {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private ProductInfo product_info_new;
    private List<ProductDescSorted> product_desc_sorted;
    private ProductDesc product_desc;

    public BookDetailInfo() {}

    public ProductInfo getProduct_info_new() {
        return product_info_new;
    }

    public void setProduct_info_new(ProductInfo product_info_new) {
        this.product_info_new = product_info_new;
    }

    public List<ProductDescSorted> getProduct_desc_sorted() {
        return product_desc_sorted;
    }

    public void setProduct_desc_sorted(List<ProductDescSorted> product_desc_sorted) {
        this.product_desc_sorted = product_desc_sorted;
    }

    public ProductDesc getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(ProductDesc product_desc) {
        this.product_desc = product_desc;
    }
}

class PublishInfo {
    private String publisher;
    private String author_name;
    private String number_of_pages;
    private String number_of_words;
    private String version_num;
    private String standard_id;

    public PublishInfo() {}

    public String getStandard_id() {
        return standard_id;
    }

    public void setStandard_id(String standard_id) {
        this.standard_id = standard_id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getNumber_of_pages() {
        return number_of_pages;
    }

    public void setNumber_of_pages(String number_of_pages) {
        this.number_of_pages = number_of_pages;
    }

    public String getNumber_of_words() {
        return number_of_words;
    }

    public void setNumber_of_words(String number_of_words) {
        this.number_of_words = number_of_words;
    }

    public String getVersion_num() {
        return version_num;
    }

    public void setVersion_num(String version_num) {
        this.version_num = version_num;
    }
}

class ProductDescSorted {
    private String name;
    private Object content;
    private String format;

    public ProductDescSorted() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("add product desc sorted: " + name);
        this.name = name;
    }

    public String getContent() {
        return (String) content;
    }

    public void setContent(String content) {
        System.out.println("add content: " + content);
        this.content = content;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}

class ProductDesc {
    private String _abstract;
    private String content;

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

class ProductInfo {
    private String product_id;
    private PublishInfo publish_info;

    public ProductInfo() {}

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public PublishInfo getPublish_info() {
        return publish_info;
    }

    public void setPublish_info(PublishInfo publish_info) {
        this.publish_info = publish_info;
    }

}

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

    public BookDetail(Book book) {
        this.baseinfo = book;
    }

    public BookDetail(String id) {
        this.book_id = id;
    }

    public void storeToDB(Book book) {
        BookService bookService = (BookService) applicationContext.getBean("bookService");

        bookService.save(book);
    }

    public void parse(Book book) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String content = jedis.get(REDIS_KEY_PREFIX + this.book_id);
        System.out.println(content);

        BookDetailInfo info = JSON.parseObject(content, BookDetailInfo.class);

        ProductInfo pinfo = info.getProduct_info_new();
        PublishInfo pubinfo = pinfo.getPublish_info();

        book.setAuthor(pubinfo.getAuthor_name());
        book.setPublishOrg(pubinfo.getPublisher());
        book.setIsbn(pubinfo.getStandard_id());
        /*
        book.setPages(Integer.parseInt(pubinfo.getNumber_of_pages()));
        book.setWordsNum(Long.parseLong(pubinfo.getNumber_of_words()));
        */

        String pub_name = "出版信息";
        String brief_name = "简介";
        String recommend_name = "推荐语";

        String bookdesc = "";
        List<ProductDescSorted> descArray = info.getProduct_desc_sorted();
        for (Iterator<ProductDescSorted> iter = descArray.iterator(); iter.hasNext(); ) {
            ProductDescSorted desc = iter.next();

            logger.info("desc name: " + desc.getName());

            if (desc.getName().equals(pub_name)) {
                List<PublishInfoItemContent> items = JSON.parseObject(desc.getContent(),
                        new TypeReference<List<PublishInfoItemContent>>(){});
                for (int i = 0; i < items.size(); ++i) {
                    PublishInfoItemContent item = items.get(i);

                    if (item.getName().equals("字数")) {
                        book.setWordsNum(Long.parseLong(item.getContent()));
                    }
                    if (item.getName().equals("出版时间")) {
                        book.setPublishTime(item.getContent());
                    }
                }
            } else if (desc.getName().equals(brief_name) == true) {
                bookdesc = desc.getContent();
            } else if (desc.getName().equals(recommend_name) == true) {
                bookdesc += desc.getContent();
            }
        }

        ProductDesc desc = info.getProduct_desc();
        if (desc != null) {
            logger.info("abstract: " + desc.getAbstract() + "; content : " + desc.getClass());
        }

        logger.info("book id: " + book.getDDId() + "; book descript: " + bookdesc);
        book.setDesc(bookdesc);
    }

    public void parse() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String content = jedis.get(REDIS_KEY_PREFIX + this.book_id);

        BookDetailInfo info = JSON.parseObject(content, BookDetailInfo.class);

        ProductInfo pinfo = info.getProduct_info_new();
        PublishInfo pubinfo = pinfo.getPublish_info();

        System.out.println("author is : " + pubinfo.getAuthor_name());
    }

}
