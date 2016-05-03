package com.xuewen.kidsbook.common.book;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-2-17.
 */
public class Book {
    private Long id = null;
    private String name = null;
    private String price = null;
    private String desc = null;
    private String DDId = null;
    private int    ageRangeMin  = -1;
    private int    ageRangeMax  = -1;
    private String category1 = null;
    private String category2 = null;
    private String category3 = null;
    private String imgUrl = null;

    private String resourceTag = null;

    private int pages = 0;
    private long wordsNum = 0;

    private String publishTime = null;
    private String publishOrg = null;
    private String version = null;
    private String author = null;
    private String isbn = null;
    private String descContent = null;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getResourceTag() {
        return resourceTag;
    }

    public void setResourceTag(String resourceTag) {
        this.resourceTag = resourceTag;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public long getWordsNum() {
        return wordsNum;
    }

    public void setWordsNum(long wordsNum) {
        this.wordsNum = wordsNum;
    }

    public String getPublishOrg() {
        return publishOrg;
    }

    public void setPublishOrg(String publishOrg) {
        this.publishOrg = publishOrg;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescContent() {
        return descContent;
    }

    public void setDescContent(String descContent) {
        this.descContent = descContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDDId() {
        return DDId;
    }

    public void setDDId(String DDId) {
        this.DDId = DDId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Map<String, Object> toJsonMap() {
        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("id", getId());
        jsonMap.put("name", getName());
        jsonMap.put("author", getAuthor());
        jsonMap.put("price", getPrice());
        jsonMap.put("desc", getDesc());
        jsonMap.put("DDId", getDDId());
        jsonMap.put("isbn", getIsbn());
        jsonMap.put("puborg", getPublishOrg());
        jsonMap.put("imageUrl", getImgUrl());
        jsonMap.put("img", getImgUrl());

        return jsonMap;
    }
}
