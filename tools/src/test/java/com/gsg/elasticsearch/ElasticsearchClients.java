package com.gsg.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @ClassName RestHighLevelClient
 * @Description TODO
 * @Author mc
 * @Date 2019/9/6 9:59
 * @Version 1.0
 **/
public class ElasticsearchClients {

    private static String hosts = "127.0.0.1"; // 集群地址，多个用,隔开
    private static int port = 9200; // 使用的端口号
    private static String schema = "http"; // 使用的协议
    private static ArrayList<HttpHost> hostList = null;
    private static RestHighLevelClient client = null;

    private static int connectTimeOut = 1000; // 连接超时时间
    private static int socketTimeOut = 30000; // 连接超时时间
    private static int connectionRequestTimeOut = 500; // 获取连接的超时时间

    private static int maxConnectNum = 100; // 最大连接数
    private static int maxConnectPerRoute = 100; // 最大路由连接数


    static {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hosts, port, "http"),
                        new HttpHost(hosts, 9201, "http")));
    }

    /**
     * 连接ES
     * @return
     */
    public static RestHighLevelClient start() {
        return client;
    }

    public void stop(RestHighLevelClient client) throws IOException {
        client.close();
    }
}
