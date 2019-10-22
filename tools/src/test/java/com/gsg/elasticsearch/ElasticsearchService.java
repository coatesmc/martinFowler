package com.gsg.elasticsearch;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ElasticsearchService
 * @Description TODO
 * @Author mc
 * @Date 2019/9/6 10:03
 * @Version 1.0
 **/
public class ElasticsearchService {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient start = ElasticsearchClients.start();
        //createIndex(start);
        //getIndex(start);
        searchDocument(start);
    }






    public static void createIndex(RestHighLevelClient client) {
      //索引名称
        IndexRequest request = new IndexRequest("hello");
        request.id("1");
        Map<String, Object> id = new HashMap<>();
        id.put("type", "text");
        id.put("store", true);
        Map<String, Object> title = new HashMap<>();
        title.put("type", "text");
        title.put("store", true);
        title.put("index", true);
        title.put("analyzer", "standard");
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", id);
        properties.put("title", title);
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        request.opType(DocWriteRequest.OpType.CREATE);
        request.opType("create");
        request.source(mapping);
      /*  //分片副本
        request.settings(Settings.builder().put("index.number_of_shards", 5)
                .put("index.number_of_replicas", 1));
        //内容
        Map<String, Object> id = new HashMap<>();
        id.put("type", "text");
        id.put("store", true);
        Map<String, Object> title = new HashMap<>();
        title.put("type", "text");
        title.put("store", true);
        title.put("index", true);
        title.put("analyzer", "standard");
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", id);
        properties.put("title", title);
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        request.mapping(String.valueOf(mapping));
        */
        try {
            IndexResponse index = client.index(request, RequestOptions.DEFAULT);
            System.out.println(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getIndex(RestHighLevelClient client) throws IOException {
        //索引名称
        GetIndexRequest request = new GetIndexRequest("hello");
        try {
            boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
            System.out.println(exists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除索引
     */
    public void delIndex(RestHighLevelClient client) {
        DeleteIndexRequest request = new DeleteIndexRequest("hello");
        try {
            AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
            System.out.println(delete.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据文档ID查询文档
     * @param client
     */
    public static void getDocument(RestHighLevelClient client){
        GetRequest getRequest = new GetRequest("hello", "1");
        GetResponse documentFields = null;
        try {
            documentFields = client.get(getRequest, RequestOptions.DEFAULT);
            System.out.println(documentFields.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询文档
     * @param client
     */
    public static void searchDocument(RestHighLevelClient client){
        SearchRequest searchRequest = new SearchRequest("hello");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
