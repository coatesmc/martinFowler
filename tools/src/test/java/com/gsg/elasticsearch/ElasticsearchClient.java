package com.gsg.elasticsearch;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.coates.tools.util.DateUtils;
import com.google.gson.JsonObject;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @ClassName ElasticsearchClient
 * @Description TODO
 * @Author mc
 * @Date 2019/8/19 10:07
 * @Version 1.0
 **/
public class ElasticsearchClient {

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
/*        hostList = new ArrayList<>();
        String[] hostStrs = hosts.split(",");
        for (String host : hostStrs) {
            hostList.add(new HttpHost(host, port, schema));
        }*/
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hosts, port, "http"),
                        new HttpHost(hosts, 9201, "http")));
    }


    public static void main(String[] args) throws IOException {
        //删除测试负数
        // deleteIndexFor();
        //查询
        //createIndex();
        //getQuery();
        searchIndex();
        //  chineseSearch("user","kimchy0");

        client.close();
    }

    /*
     *//**
     * 纯中文搜索
     * @return
     *//*
    public static QueryBuilder chineseSearch(String filed, String value){

        DisMaxQueryBuilder disMaxQueryBuilder=QueryBuilders.disMaxQuery();
        //以关键字开头(优先级最高)
        MatchQueryBuilder q1=QueryBuilders.matchQuery(filed+".ngram",value).analyzer("ngram_search").boost(5);
        //完整包含经过分析过的关键字
//         boolean  whitespace=key.contains(" ");
//         int slop=whitespace?50:5;
        QueryBuilder q2=QueryBuilders.matchQuery(filed, value).analyzer("ik_search").minimumShouldMatch("100%");
        disMaxQueryBuilder.add(q1);
        disMaxQueryBuilder.add(q2);

        SearchResponse searchResponse = client.search(disMaxQueryBuilder, RequestOptions.DEFAULT);
        return  ;
    }*/

    /**
     * 条件搜索
     */
    public static void searchIndex() throws IOException {

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("user", "kimchy"));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println("searchResponse = {" + searchResponse + "}");

        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> tmp = hit.getSourceAsMap();
            System.out.println("tmp = {" + tmp + "}");
        }

      /*
        SearchRequest searchRequest = new SearchRequest("posts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("user", "kimchy0"));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> tmp = hit.getSourceAsMap();
            System.out.println("tmp={" + tmp + "}");
        }
        */
    }


    /**
     * 删除测试负数
     *
     * @throws IOException
     */
    private static void deleteIndexFor() throws IOException {
        for (int i = 0; i <= 10; i++) {
            String index = "posts";
            String id = i + "";
            if (exists(index, id)) {
                deleteRequest(index, id);
            }
        }
    }

    /**
     * 创建索引
     */
    private static void createIndex() throws IOException {
        IndexRequest request = new IndexRequest("posts");
        for (int i = 0; i <= 10; i++) {
            request.id(i + "");
            JSONObject source = new JSONObject();
            JSONObject indexPattern = new JSONObject();
            JSONObject json = new JSONObject();
            String userName = "kimchy" + i;
            json.put("user", "kimchy" + i);
            json.put("postDate", DateUtils.getDateUtils().getTodayDate());
            json.put("message", "trying out Elasticsearch {" + i + "}");

            json.put("title", userName);
          /*  indexPattern.put("fields", json);
            source.put("index-pattern", indexPattern);*/
            request.opType(DocWriteRequest.OpType.CREATE);
            request.opType("create");

            request.source(json);

            IndexResponse index = client.index(request, RequestOptions.DEFAULT);
            System.out.println(index);
        }
    }


    /**
     * 删除索引
     *
     * @param index
     * @param id
     * @throws IOException
     */
    public static void deleteRequest(String index, String id) throws IOException {

        DeleteRequest getRequest = new DeleteRequest(index, id);

        DeleteResponse delete = client.delete(getRequest, RequestOptions.DEFAULT);

        System.out.println("delete = {" + delete + "}");

        if (delete != null || index.equals(delete.getIndex())) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }

    }

    /**
     * 查询索引是否存在
     *
     * @param index
     * @param id
     * @throws IOException
     */
    public static boolean exists(String index, String id) throws IOException {
        GetRequest getRequest = new GetRequest(
                index,
                id);
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
        return exists;
    }


    /**
     * 查询？
     *
     * @throws IOException
     */
    public static void getQuery() throws IOException {
        GetRequest request = new GetRequest("posts", "1");
        //禁用默认情况下启用的源检索
        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        request.storedFields("message");
        String[] includes = new String[]{"message"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        request.fetchSourceContext(fetchSourceContext);

        GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
        String message = getResponse.getField("message").getValue();


        try {
           /* GetRequest request = new GetRequest("posts", "1");
              GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);*/
            System.out.println("message = {" + getResponse + "}");
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println(e.getMessage());
            }
        }
    }


}