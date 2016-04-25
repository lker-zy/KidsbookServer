package com.xuewen.kidsbook.common.mysql.dao.service;

import com.xuewen.kidsbook.common.book.RecommendInfo;

/**
 * Created by root on 16-2-24.
 */
public interface RecommandInfoService {
    public abstract void save(RecommendInfo info);

    public abstract void update(RecommendInfo info);

    public abstract RecommendInfo get(Long id);

}
