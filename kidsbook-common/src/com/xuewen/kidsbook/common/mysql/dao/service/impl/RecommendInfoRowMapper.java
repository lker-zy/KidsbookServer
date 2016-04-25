package com.xuewen.kidsbook.common.mysql.dao.service.impl;

import com.xuewen.kidsbook.common.book.RecommendInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 16-2-24.
 */
public class RecommendInfoRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        RecommendInfo recommendInfo = new RecommendInfo();
        recommendInfo.setRecommendAuthor(resultSet.getString("author"));
        recommendInfo.setRecommendText(resultSet.getString("content"));
        recommendInfo.setRecommendImgId(resultSet.getString("img_id"));
        return recommendInfo;
    }
}
