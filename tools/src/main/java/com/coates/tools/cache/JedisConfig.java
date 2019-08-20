package com.coates.tools.cache;

import redis.clients.jedis.JedisPoolConfig;

/**
 * 缓存配置
 * Created by huangyp on 2017/9/6.
 */
public class JedisConfig extends JedisPoolConfig {

    /**
     * 地址
     */
    private String address;

    /**
     * 端口
     */
    private int port = 6379;

    /**
     * 超时时间
     */
    private int timeout = 10000;

    /**
     * 密码
     */
    private String auth;

    public JedisConfig() {
        super();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
