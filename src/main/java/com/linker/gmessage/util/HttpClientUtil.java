package com.linker.gmessage.util;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author huang.ziqing
 * @date 2019/10/12
 */
public class HttpClientUtil {

    public static String sendGet(String url, Map<String, String> params) throws Exception {
        String result = "";
        if (params != null && params.size() > 0) {
            url += "?";
            int index = 0;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (0 != index++) {
                    url += "&";
                }
                url += (entry.getKey() + "=" + entry.getValue());
            }
        }
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendPost(String url, Map<String, String> params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> formparams = paseToFormParam(params);// new
        UrlEncodedFormEntity encodeEntity;
        try {
            encodeEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            post.setEntity(encodeEntity);
            CloseableHttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String resutlStr = EntityUtils.toString(entity);
                return resutlStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析参数的方法
     *
     * @param params
     * @return
     */
    public static List<NameValuePair> paseToFormParam(Map<String, String> params) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formparams;
    }

    /**
     * 发送json字符串
     *
     * @param url
     * @param jsonStr
     * @return
     * @throws Exception
     */
    public static String sendJsonData(String url, final String jsonStr) throws Exception {
        System.out.println(jsonStr);
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-type", "application/json; charset=utf-8");
        post.setHeader("Accept", "application/json");
        post.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));
        HttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String resutlStr = EntityUtils.toString(entity, "UTF-8");
            return resutlStr;
        }
        return null;
    }

}
