package Common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Common.common.str_to_map;

public class Testmethod {
    //get请求，不带参数
    public static Map<String,String> doGet(String url){
        Map<String,String> resultMap = new HashMap<>();
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;

        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            httpGet = new HttpGet(url);
            httpGet.setHeader("Accept","application/json;charset=utf-8");
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            String result = String.valueOf(EntityUtils.toString(httpEntity,"utf-8"));
            String statuscode = String.valueOf(response.getStatusLine().getStatusCode());
            resultMap.put("statuscode",statuscode);
            resultMap.put("result",result);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(httpGet!=null){
                    httpGet.releaseConnection();
                }
                if(httpClient!=null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public static Map<String,String> doGetJson(String url, String params) {
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = "";
        String statuscode = "";
        Map<String,String> resultMap = new HashMap<>();
        Map<String,String> resultMsg = new HashMap<>();
        if (params == null){
            resultMap = doGet(url);
        }
        else {
            try {
                httpClient = HttpClients.createDefault();
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
                String ps = "";
                String ps1 ="";
                String strResult;
                String strs = params.replace(":","=");
                String strs1 = strs.replace(",","&");
                String strs2 = strs1.replace("{","");
                url = url + "?" + strs2.replace("}", "");
                httpGet = new HttpGet(url);
                httpGet.setHeader("Accept", "application/json;charset=utf-8");
                httpGet.setConfig(requestConfig);
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity httpEntity = response.getEntity();
                statuscode = String.valueOf(response.getStatusLine().getStatusCode());
                result = String.valueOf(EntityUtils.toString(httpEntity, "utf-8"));
                resultMap.put("statuscode",statuscode);
                resultMap.put("result",result);
                resultMsg = str_to_map(result);
                String msg = resultMsg.get("msg");
                resultMap.put("msg",msg);
                //return resultMap;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (httpGet != null) {
                        httpGet.releaseConnection();
                    }
                    if (httpClient != null) {
                        httpClient.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultMap;
    }

    //post请求
    public static String doPost(String url, Map<String, Object> paramsMap){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
                .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        for (String key : paramsMap.keySet()) {
            nvps.add(new BasicNameValuePair(key, String.valueOf(paramsMap.get(key))));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            //logger.info("httpPost ===**********===>>> " + EntityUtils.toString(httpPost.getEntity()));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String strResult = String.valueOf(EntityUtils.toString(response.getEntity()));
            if (response.getStatusLine().getStatusCode() == 200) {
                return strResult;
            } else {
                return "Error Response: " + response.getStatusLine().getStatusCode() + strResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "post failure :caused by-->" + e.getMessage().toString();
        }finally {
            if(null != httpClient){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //post请求，参数为请求体格式
    public static Map<String, String> PostForJson (String url, String jsonParams){
        Map<String,String> resultMap = new HashMap();
        Map<String,String> resultMsg = new HashMap();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
                .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();

        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type","application/json");  //
        try {
            httpPost.setEntity(new StringEntity(jsonParams,ContentType.create("application/json", "utf-8")));
            System.out.println("request parameters" + EntityUtils.toString(httpPost.getEntity()));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String statuscode = String.valueOf(response.getStatusLine().getStatusCode());
            String result = String.valueOf(EntityUtils.toString(entity));
            resultMap.put("statuscode", statuscode);
            resultMap.put("result", result);
            resultMsg = str_to_map(result);
            String msg = resultMsg.get("msg");
            resultMap.put("msg", msg);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            //return "post failure :caused by-->" + e.getMessage().toString();
        }finally {
            if(null != httpClient){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultMap;
    }
    //根据传参调用相应的请求方法
    public static Map<String,String> testcase(String method,String url,String params){
        Map<String,String> map = new HashMap();
        if (method.equals("post")){
            map = PostForJson(url, params);
            //System.out.println(map3);
        }
        else if (method.equals("get")){
            map = doGetJson(url, params);
            //System.out.println(map3);
        }
        return map;
    }

    public static void main(String[] args){
        String[][] testdata = ExcelReader.read_excel("C:\\Users\\good\\Desktop\\接口测试用例.xlsx","2007测试表");
        int row = testdata.length;
        Map<String,String> map3 = new HashMap();
        String method,url,params;
        for (int i =0;i<row;i++){
            method = String.valueOf(testdata[i][2]);
            url = String.valueOf(testdata[i][1]);
            params = String.valueOf(testdata[i][3]);
            map3 = testcase(method,url,params);
            System.out.println(map3);

        }
    }
}

