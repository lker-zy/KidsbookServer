package com.xuewen.kidsbook.common.redis;

/**
 * Created by root on 16-2-17.
 */
public class RedisConf {
    private String server;
    private int    port;

    public RedisConf(String server, int port) {
        setServer(server);
        setPort(port);
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
