package com.xuewen.kidsbook.common.mysql.dao.service.impl;

import com.xuewen.kidsbook.common.book.RecommendInfo;
import com.xuewen.kidsbook.common.mysql.dao.service.RecommandInfoService;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by root on 16-2-24.
 */
public class RecommendInfoServiceBean implements RecommandInfoService {
    private static String TABLE = "recommend_info";

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private DataSource dataSource;
    JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(RecommendInfo info) {
        jdbcTemplate.update("insert into " + TABLE +
                        "(id, author, content, img_id) " + "" +
                        "values(?, ?, ?, ?)",
                new Object[] {info.getId(), info.getRecommendAuthor(), info.getRecommendText(), info.getRecommendImgId()},
                new int[] {Types.BIGINT, Types.CHAR, Types.VARCHAR, Types.CHAR}
        );
    }

    @Override
    public void update(RecommendInfo info) {
        jdbcTemplate.update("update " + TABLE + " set content=?, author=? , img_id where id=?",
                new Object[] {info.getRecommendText(), info.getRecommendAuthor(), info.getRecommendImgId(), info.getId()},
                new int[] {Types.LONGNVARCHAR, Types.CHAR, Types.CHAR, Types.BIGINT});
    }

    @Override
    public RecommendInfo get(Long id) {
        logger.info("sql : select * from " + TABLE);
        RecommendInfo info = (RecommendInfo) jdbcTemplate.queryForObject(
                "select * from " + TABLE + " where id = ?",
                new Object[] {id}, new int[] {Types.BIGINT},
                new RecommendInfoRowMapper()
        );

        return info;
    }
}
