package com.xuewen.kidsbook.web.action;

/**
 * Created by root on 16-5-14.
 */
import com.opensymphony.xwork2.ActionSupport;
import com.xuewen.kidsbook.common.book.BookReview;
import com.xuewen.kidsbook.common.mysql.dao.service.ReviewService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WebEssenceDetail extends ActionSupport {
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath*:mysql_bean.xml");

    private static String imgPrefix = "http://180.76.176.227/web/img/detail/";

    private String essenceId;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEssenceId() {
        return essenceId;
    }

    public void setEssenceId(String essenceId) {
        this.essenceId = essenceId;
    }

    @Override
    public String execute() {
        ReviewService reviewService = (ReviewService) applicationContext.getBean("reviewService");
        BookReview bookReview = reviewService.get(Long.valueOf(getEssenceId()));

        System.out.println("content is: " + bookReview.getContent());

        setContent(bookReview.getContent());

        return SUCCESS;
    }
}
