package com.coates.tools.httputil;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class HttpClient {
    public static final String SunX509 = "SunX509";
    public static final String JKS = "JKS";
    public static final String PKCS12 = "PKCS12";
    public static final String TLS = "TLS";
    /**
     * 默认：请求获取数据的超时时间，单位毫秒。
     */
    private static final int defaultSocketTimeout = 10000;
    /**
     * 默认：设置连接超时时间，单位毫秒。
     */
    private static final int defaultConnectTimeout = 3000;

    /**
     * 设置超时时间
     */
    public static void setTimeout( HttpRequestBase header ){
        header.setConfig(RequestConfig.custom().setSocketTimeout(defaultSocketTimeout).
                setConnectTimeout(defaultConnectTimeout).setConnectionRequestTimeout(defaultConnectTimeout).build());
    }

    /**
     * 通过GET方式获取指定URL的内容
     * 
     * @param url
     * @return
     * @throws Exception
     */
    public static String getUrlContent(String url) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        setTimeout(httpGet);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        return getResponseContent(response);
    }
    
    public static String getUrlContent(String url, Map<String, String> headerMap) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        setTimeout(httpGet);
        Set<Map.Entry<String, String>> set = headerMap.entrySet();
        Iterator<Map.Entry<String, String>> iter = set.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> me = iter.next();
            httpGet.addHeader(me.getKey(), me.getValue());
        }
        CloseableHttpResponse response = httpclient.execute(httpGet);
        return getResponseContent(response);
    }

    /**
     * 返回请求内容
     * 
     * @param response
     * @return
     * @throws Exception
     */
    public static String getResponseContent(CloseableHttpResponse response) throws Exception {
        try {
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity1 = response.getEntity();
                return EntityUtils.toString(entity1, "utf-8");
            } else {
                return response.toString();
            }
        } finally {
            response.close();
        }
    }

    /**
     * 向指定的URL发送请求
     * 
     * @param url
     * @param paraMap
     *            参数
     * @return
     * @throws Exception
     */
    public static String postParamsToUrl(String url, Map<String, String> paraMap) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        setTimeout(httpPost);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>(paraMap.size());
        Set<Map.Entry<String, String>> set = paraMap.entrySet();
        Iterator<Map.Entry<String, String>> iter = set.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> me = iter.next();
            nvps.add(new BasicNameValuePair(me.getKey(), me.getValue()));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        CloseableHttpResponse response = httpclient.execute(httpPost);

        return getResponseContent(response);
    }



    /**
     * 向指定的URL发送请求
     * 
     * @param url
     * @param paraMap
     *            参数
     * @return
     * @throws Exception
     */
    public static String postParamsToUrl(String url, Map<String, String> paraMap, Map<String, String> headerMap) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        setTimeout(httpPost);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>(paraMap.size());
        StringBuilder params = new StringBuilder();
        Set<Map.Entry<String, String>> set = paraMap.entrySet();
        Iterator<Map.Entry<String, String>> iter = set.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> me = iter.next();
            nvps.add(new BasicNameValuePair(me.getKey(), me.getValue()));
            if(params.length() > 0) {
                params.append("&");
            }
            params.append(me.getKey() + "=" + me.getValue());
        }
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        StringEntity entity = new StringEntity(params.toString(), "utf-8");// 解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        httpPost.setEntity(entity);

        //header
        set = headerMap.entrySet();
        iter = set.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> me = iter.next();
            httpPost.addHeader(me.getKey(), me.getValue());
        }
        httpPost.setHeader("content-type","application/x-www-form-urlencoded");

        CloseableHttpResponse response = httpclient.execute(httpPost);

        return getResponseContent(response);
    }

    /**
     * 发送json数据到指定的URL
     * 
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public static String postJsonDataToUrl(String url, String json) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        setTimeout(httpPost);
        StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        return getResponseContent(response);
    }





    public static String sendPost(String url, Map<String, String> paraMap, Map<String, String> headerMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {

            StringBuilder params = new StringBuilder();
            //body
            Set<Map.Entry<String, String>> set = paraMap.entrySet();
            Iterator<Map.Entry<String, String>> iter = set.iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> me = iter.next();
                if(params.length() > 0) {
                    params.append("&");
                }
                params.append(me.getKey() + "=" + me.getValue());
            }

            URL realUrl = new URL(url);
            HttpURLConnection  conn = (HttpURLConnection)realUrl.openConnection();  // 打开和URL之间的连接
            conn.setConnectTimeout(defaultConnectTimeout);
            conn.setReadTimeout(defaultSocketTimeout);
            //header
            set = headerMap.entrySet();
            iter = set.iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> me = iter.next();
                conn.setRequestProperty(me.getKey(), me.getValue());
            }
            // 发送POST请求必须设置如下两行

            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));  // 获取URLConnection对象对应的输出流
            out.print(params.toString());    // 发送请求参数
            out.flush();        // flush输出流的缓冲

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));  // 定义BufferedReader输入流来读取URL的响应
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
   /* public static void main(String[] args) {
		System.out.println(909925+48880);
	}*/


}
