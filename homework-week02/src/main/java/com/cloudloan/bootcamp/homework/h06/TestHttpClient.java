package com.cloudloan.bootcamp.homework.h06;

import okhttp3.*;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Http Client
 * <p>
 * 使用 Apache Http Client 实现
 *
 * @author zhaochen
 */
public class TestHttpClient {

    /**
     * Http Server 地址
     */
    private static final String BIO_SERVER_URL = "http://127.0.0.1:8801";
    private static CloseableHttpClient apacheHttpClient = HttpClients.createDefault();
    private static OkHttpClient okHttpClient = new OkHttpClient();

    /**
     * @param args args
     * @throws IOException IOException
     */
    public static void main(String[] args) throws IOException {
        doGetWithApacheHttpClient(BIO_SERVER_URL);
        System.out.println("========================");
        doPostWithApacheHttpClient(BIO_SERVER_URL);
        System.out.println("========================");
        doGetWithOkHttp(BIO_SERVER_URL);
        System.out.println("========================");
        doPostWithOkHttp(BIO_SERVER_URL);
    }

    /**
     * 使用 apache http client 发送 GET 请求
     *
     * @param url url
     */
    private static void doGetWithApacheHttpClient(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = apacheHttpClient.execute(httpGet)) {
            System.out.println("[HTTP GET] status:" + response.getStatusLine());
            System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
        }
    }

    /**
     * 使用 apache http client 发送 POST 请求
     *
     * @param url url
     */
    private static void doPostWithApacheHttpClient(String url) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicNameValuePair("username", "zhaochen"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);
        httpPost.setEntity(entity);
        ResponseHandler<String> responseHandler = response -> {
            System.out.println("[HTTP POST] status:" + response.getStatusLine());
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        };
        String responseBody = apacheHttpClient.execute(httpPost, responseHandler);
        System.out.println(responseBody);
    }

    /**
     * 使用 okhttp3 发送 GET 请求
     *
     * @param url url
     */
    private static void doGetWithOkHttp(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body() != null ? response.body().string() : null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用 okhttp3 发送 POST 请求
     *
     * @param url url
     */
    private static void doPostWithOkHttp(String url) {
        RequestBody formBody = new FormBody.Builder()
                .add("username", "zhaochen")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        final Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body() != null ? response.body().string() : null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
