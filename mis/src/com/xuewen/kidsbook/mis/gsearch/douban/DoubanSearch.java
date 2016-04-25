package com.xuewen.kidsbook.mis.gsearch.douban;

/**
 * Created by root on 16-3-5.
 */

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

public class DoubanSearch {
    public DoubanBook go(String isbn) throws ClientProtocolException, IOException {
        String baseUrl = "http://api.douban.com/book/subject/isbn/";
        String url =  baseUrl + isbn;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();

        DoubanBook book = new BookXmlParser(is).getBook();
        System.out.println("title:->" + book.getTitle());
        System.out.println("summary:->" + book.getSummary());
        System.out.println("price:-->" + book.getPrice());
        System.out.println("author:-->" + book.getAuthor());
        System.out.println("ImagePath:-->" + book.getImagePath());

        return book;
    }

    public static void main(String[] args) throws ClientProtocolException, IOException {
        DoubanSearch doubanSearch = new DoubanSearch();
        doubanSearch.go("9787308083256");
    }
}

