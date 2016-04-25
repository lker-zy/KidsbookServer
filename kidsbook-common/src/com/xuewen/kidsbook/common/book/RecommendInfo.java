package com.xuewen.kidsbook.common.book;

/**
 * Created by root on 16-2-24.
 */
public class RecommendInfo {
    private long   id = -1;
    private String recommendText    = null;
    private String recommendAuthor  = null;
    private String recommendImgId   = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRecommendImgId() {
        return recommendImgId;
    }

    public void setRecommendImgId(String recommendImgId) {
        this.recommendImgId = recommendImgId;
    }

    public String getRecommendAuthor() {
        return recommendAuthor;
    }

    public void setRecommendAuthor(String recommendAuthor) {
        this.recommendAuthor = recommendAuthor;
    }

    public String getRecommendText() {
        return recommendText;
    }

    public void setRecommendText(String recommendText) {
        this.recommendText = recommendText;
    }
}
