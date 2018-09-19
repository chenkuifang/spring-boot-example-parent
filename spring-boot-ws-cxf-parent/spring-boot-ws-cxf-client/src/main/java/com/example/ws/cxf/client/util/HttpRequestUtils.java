package com.example.ws.cxf.client.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * http 请求工具
 *
 * @author Quifar
 */
public final class HttpRequestUtils {

    private static final String ENCODING = "UTF-8";

    static RequestConfig defaultRequestConfig;

    static {
        defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(30000)
                .setConnectTimeout(30000)
                .setConnectionRequestTimeout(30000)
                .setStaleConnectionCheckEnabled(true)
                .build();
    }

    /**
     * get 请求
     *
     * @param urlWithParams 带参数的请求路径
     * @return
     * @throws Exception
     */
    public static String get(String urlWithParams) throws Exception {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(urlWithParams);
        CloseableHttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, ENCODING);
        httpget.releaseConnection();

        return result;
    }

    /**
     * post 请求
     * contentType 为:text/xml; charset=utf-8
     *
     * @param url     请求地址
     * @param content 请求参数
     * @return
     */
    public static String post(String url, String content) throws IOException {
        String contentType = "text/xml; charset=utf-8";
        return post(url, content, contentType);
    }

    /**
     * post 请求
     *
     * @param url         请求地址
     * @param content     请求参数
     * @param contentType 请求内容类型
     * @return 请求参数
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String post(String url, String content, String contentType) throws ClientProtocolException, IOException {
        HttpPost httpPost = null;
        String result;

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(defaultRequestConfig).build();
            httpPost = new HttpPost(url);
            httpPost.addHeader("content-type", contentType);
            StringEntity se = new StringEntity(content, ENCODING);
            httpPost.setEntity(se);

            // result of response
            CloseableHttpResponse response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), ENCODING);
        } finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }

        return result;
    }

    /**
     * post 请求
     *
     * @param url
     * @param content
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postOrg(String url, String content)
            throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient;
        HttpPost httppost = null;
        String result;

        try {
            httpclient = HttpClientBuilder.create()
                    .setDefaultRequestConfig(defaultRequestConfig).build();

            httppost = new HttpPost(url);
            httppost.addHeader("content-type", "text/xml; charset=utf-8");
            httppost.addHeader("SOAPAction", "urn:doService");
            StringEntity se = new StringEntity(content, ENCODING);
            httppost.setEntity(se);
            CloseableHttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            entity.getContent();
            result = EntityUtils.toString(entity, ENCODING);
        } finally {
            if (httppost != null) {
                httppost.releaseConnection();
            }
        }

        return result;
    }


    /**
     * 符号转换
     * 去掉空格等
     *
     * @param xml
     * @return
     */
    public static String replaceEscapeSequence(String xml) {
        return xml.replaceAll("&gt;", ">")
                .replaceAll("&lt;", "<")
                .replaceAll("&quot;", "\"");
    }

    /**
     * @param xml
     * @return
     */
    public static String replaceEscapeSequenceFor(String xml) {
        return xml.replaceAll(">", "&gt;")
                .replaceAll("<", "&lt;")
                .replaceAll("\"", "&quot;");
    }
}