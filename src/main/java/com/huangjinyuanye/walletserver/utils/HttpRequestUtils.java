package com.huangjinyuanye.walletserver.utils;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLDecoder;

public class HttpRequestUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);    //日志记录

    /**
     * httpPost
     * @param url  路径
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, Object jsonParam){
        return httpPost(url, jsonParam, false);
    }

    public static JSONObject httpPut(String url, Object jsonParam){
        return httpPut(url, jsonParam, false);
    }

    /**
     * put请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static JSONObject httpPut(String url,Object jsonParam, boolean noNeedResponse){
        //post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = new JSONObject();
        HttpPut method = new HttpPut(url);

        System.out.println("url:::::"+url);

        try {
            if (null != jsonParam) {
                System.out.println("jsonParam:!!!!!!!!"+jsonParam.toString());
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("text/plain");
                method.setEntity(entity);
            }
//            method.addHeader("Authorization", "Basic " + Base64.encode((AmbariProfile.user + ":" + AmbariProfile.pwd).getBytes()));
//            method.addHeader("X-Requested-By","ambari");

            HttpResponse result = httpClient.execute(method);
//            System.out.println("result:######"+result.toString());
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            int code = -1;
            if (String.valueOf(result.getStatusLine().getStatusCode()).startsWith("20")) {
                code = 200;
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    if(str!=null && str.length()>0){
                        jsonResult = (JSONObject) JSONObject.parse(str);
                    }
                } catch (Exception e) {
                    logger.error("put请求提交失败:" + url, e);
                }
            }else{
                code = result.getStatusLine().getStatusCode();
            }
            if(jsonResult==null){
                jsonResult = new JSONObject();
            }
            jsonResult.put("statusCode",code);
            httpClient.close();
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }



    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url,Object jsonParam, boolean noNeedResponse){
        logger.info("url:#####"+url);
        logger.info("jsonParam:#####"+jsonParam);

        //post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = new JSONObject();
        HttpPost method = new HttpPost(url);

        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("text/plain");
                method.setEntity(entity);
            }
//            method.addHeader("Authorization", "Basic " + Base64.encode((AmbariProfile.user + ":" + AmbariProfile.pwd).getBytes()));
//            method.addHeader("X-Requested-By","ambari");

            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            int code = -1;

            if (String.valueOf(result.getStatusLine().getStatusCode()).startsWith("20")) {
                code = 200;
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    jsonResult = (JSONObject) JSONObject.parse(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }else{
                code = result.getStatusLine().getStatusCode();
            }
            if(jsonResult==null){
                jsonResult = new JSONObject();
            }
            jsonResult.put("statusCode",code);
            httpClient.close();
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }




    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static JSONObject httpGet(String url){
        logger.info("url:#####"+url);
        //get请求返回结果
        JSONObject jsonResult = new JSONObject();
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //发送get请求
            HttpGet request = new HttpGet(url);
//            request.addHeader("Authorization", "Basic " + Base64.encode((AmbariProfile.user + ":" + AmbariProfile.pwd).getBytes()));
            HttpResponse response = httpClient.execute(request);

            /**请求发送成功，并得到响应**/
            int code = -1;
            if (String.valueOf(response.getStatusLine().getStatusCode()).startsWith("20")) {
                code = 200;
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                if(strResult!=null && strResult.length()>0) {
                    jsonResult = (JSONObject) JSONObject.parse(strResult);
                }
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
                code = 500;
            }
            if(jsonResult==null){
                jsonResult = new JSONObject();
            }
            jsonResult.put("statusCode",code);
            httpClient.close();

        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }
    /**
     * put请求
     * @param url         url地址
     * @return
     */
    public static JSONObject httpDelete(String url){
        logger.info("delete请求"+url);
        //delete请求返回结果
        JSONObject jsonResult = new JSONObject();
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //发送get请求
            HttpDelete request = new HttpDelete(url);
//            request.addHeader("Authorization", "Basic " + Base64.encode((AmbariProfile.user + ":" + AmbariProfile.pwd).getBytes()));
//            request.addHeader("X-Requested-By","X-Requested-By");
            HttpResponse response = httpClient.execute(request);

            /**请求发送成功，并得到响应**/
            int code = -1;
            if (String.valueOf(response.getStatusLine().getStatusCode()).startsWith("20")) {
                code = 200;
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                if(strResult!=null && strResult.length()>0) {
                    jsonResult = (JSONObject) JSONObject.parse(strResult);
                }
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("delete请求提交失败:" + url);
                code = response.getStatusLine().getStatusCode();
            }
            if(jsonResult==null){
                jsonResult = new JSONObject();
            }
            jsonResult.put("statusCode",code);
            httpClient.close();

        } catch (IOException e) {
            logger.error("delete请求提交失败:" + url, e);
        }
        return jsonResult;
    }
}