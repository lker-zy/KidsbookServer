package com.xuewen.kidsbook.common.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by root on 16-2-17.
 */
public class RedisHandler {
    private Jedis jedis = null;
    private RedisConf conf;

    public RedisHandler(RedisConf conf) {
        this.conf = conf;
    }

    private  Jedis connect() {
        Jedis jedis = new Jedis(this.conf.getServer(), this.conf.getPort());
        this.jedis = jedis;

        return jedis;
    }

    public void init() {
        connect();
    }

    /* temp used */
    public Jedis getJedis() {
        return this.jedis;
    }
}
