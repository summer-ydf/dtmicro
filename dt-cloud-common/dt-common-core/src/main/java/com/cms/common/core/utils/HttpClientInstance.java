package com.cms.common.core.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author DT辰白 Created by 2022/4/13 10:25
 */
public class HttpClientInstance {
    /**
     * 最大连接数
     */
    private int maxTotal = 200;
    /**
     * 设置每个主机的并发数
     */
    private int defaultMaxPerRoute = 200;
    /**
     * 多少时间去检查可用的链接
     */
    private int validateAfterInactivity = 3600;
    /**
     * 创建连接的最长时间
     */
    private int connectTimeout = 5000;
    /**
     * 从连接池中获取到连接的最长时间
     */
    private int connectionRequestTimeout = 500;
    /**
     * 数据传输的最长时间
     */
    private int socketTimeout = 30000;
    /**
     * 设置连接池管理器
     */
    private PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    /**
     * HttpClient对象的构建器
     */
    private HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    /**
     * 请求配置的构建器
     */
    private RequestConfig.Builder builder = RequestConfig.custom();

    private RequestConfig requestConfig = null;

    private CloseableHttpClient httpclient = null;

    private static Log log = LogFactory.getLog(HttpClientInstance.class);

    private volatile static HttpClientInstance instance = null;

    @FunctionalInterface
    public interface HttpStreamCallback{
        <T> T callback(InputStream inputStream);
    }
    @FunctionalInterface
    public interface HttpCallback<T>{
        T callback(CloseableHttpResponse response) throws Exception;
    }
    @FunctionalInterface
    public interface WebserviceParseXml{
        String parseXml(String interfaceName,String xml);
    }

    private HttpClientInstance() {
    }

    public static HttpClientInstance getInstance() {
        if (instance == null) {
            synchronized (HttpClientInstance.class) {
                if (instance == null) {
                    instance = new HttpClientInstance();
                    instance.init();
                }
            }
        }
        return instance;
    }

    private void init() {
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(defaultMaxPerRoute);
        cm.setValidateAfterInactivity(validateAfterInactivity);
        httpClientBuilder.setConnectionManager(cm);
        builder.setConnectTimeout(connectTimeout);
        builder.setConnectionRequestTimeout(connectionRequestTimeout);
        builder.setSocketTimeout(socketTimeout);
        requestConfig = builder.build();
        httpclient=httpClientBuilder.build();
    }

    public CloseableHttpClient getHttpclient(){
        return httpclient;
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public String get(String url) {
        CloseableHttpResponse response = null;
        try {
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(this.requestConfig);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            log.debug("response status>>>>>"+response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            log.error("get["+url+"]>>>> error",e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public String getSSl(String url) {
        CloseableHttpResponse response = null;
        try {
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(this.requestConfig);
            // 执行请求
            response = createSSLClientDefault().execute(httpGet);
            // 判断返回状态是否为200
            log.debug("response status>>>>>"+response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            log.error("get", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public String getSSl(String url,String token) {
        CloseableHttpResponse response = null;
        try {
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(this.requestConfig);
            httpGet.addHeader("Authorization","Bearer ".concat(token));
            // 执行请求
            response = createSSLClientDefault().execute(httpGet);
            // 判断返回状态是否为200
            log.debug("response status>>>>>"+response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            log.error("get", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public <T> T getStream(String url,HttpStreamCallback callback) {
        CloseableHttpResponse response = null;
        InputStream instream=null;
        try {
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(this.requestConfig);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            log.debug("response status>>>>>"+response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            if(entity!=null){
                instream = entity.getContent();
            }
            return callback.callback(instream);
        } catch (Exception e) {
            log.error("getStream", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
            IOUtils.closeQuietly(instream);
        }
        return null;
    }

    public String get(String url, Map<String,String> header) {
        CloseableHttpResponse response = null;
        try {
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(this.requestConfig);
            if (header != null) {
                Set<String> keys = header.keySet();
                for (Iterator<String> i = keys.iterator(); i.hasNext();) {
                    String key = (String) i.next();
                    httpGet.addHeader(key, header.get(key));
                }
            }
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            log.debug("response status>>>>>"+response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            log.error("get", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public String post(String url, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String str=null;
        try {
            if (null != params) {
                ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), ObjectUtils.toString(entry.getValue())));
                }
                // 构造一个form表单式的实体
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, Charset.forName("utf-8"));
                // 将请求实体设置到httpPost对象中
                httpPost.setEntity(entity);
            }
            httpPost.setConfig(this.requestConfig);
            // 伪装成浏览器
            httpPost.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36");
            // 执行请求
            response = httpclient.execute(httpPost);
            log.debug("response status>>>>>"+response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            log.error("post["+url+"] error", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public String postJson(String url, Object jsonObje,boolean islog) {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        String str=null;
        String req=null;
        if (jsonObje != null) {
            // 构造一个form表单式的实体
            req= JSON.toJSONString(jsonObje);
            StringEntity stringEntity = new StringEntity(req, ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }
        String code=null;
        if(islog){
            code=getString(req);
            log.info("req["+code+"]>>>>>"+req);
        }
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            log.info("response status>>>>>"+response.getStatusLine().getStatusCode());
            String result= EntityUtils.toString(response.getEntity(), "UTF-8");
            if(islog){
                log.info("resp["+code+"]>>>>>"+result);
            }
            return result;
        }catch (Exception e){
            log.error("post json",e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public <T> T postJson(String url, Object jsonObje,HttpCallback<T> httpCallback) {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        if (jsonObje != null) {
            // 构造一个form表单式的实体
            String req= JSON.toJSONString(jsonObje);
            StringEntity stringEntity = new StringEntity(req, ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            return httpCallback.callback(response);
        }catch (Exception e){
            log.error("post json",e);
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public <T> T postJson(String url, Object jsonObje,Map<String, String> header,HttpCallback<T> httpCallback) {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        if (jsonObje != null) {
            // 构造一个form表单式的实体
            String req= JSON.toJSONString(jsonObje);
            StringEntity stringEntity = new StringEntity(req, ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }
        for (Map.Entry<String, String> entry:header.entrySet()){
            httpPost.setHeader(entry.getKey(),entry.getValue());
        }
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            return httpCallback.callback(response);
        }catch (Exception e){
            log.error("post json",e);
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public String postJsonSSl(String url, Object jsonObje,boolean islog) {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        String str=null;
        String req=null;
        if (jsonObje != null) {
            // 构造一个form表单式的实体
            req= JSON.toJSONString(jsonObje);
            StringEntity stringEntity = new StringEntity(req, ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }
        httpPost.setHeader("X-Requested-With","XMLHttpRequest");
        String code=null;
        if(islog){
            code=getString(req);
            log.info("req["+code+"]>>>>>"+req);
        }
        CloseableHttpResponse response = null;
        try {
            response = createSSLClientDefault().execute(httpPost);
            log.info("response status>>>>>"+response.getStatusLine().getStatusCode());
            String result=EntityUtils.toString(response.getEntity(), "UTF-8");
            if(islog){
                log.info("resp["+code+"]>>>>>"+result);
            }
            return result;
        }catch (Exception e){
            log.error("post json",e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public String postString(String url, String params){
        CloseableHttpResponse response=null;
        String str=null;
        try {
            HttpPost httpost = new HttpPost(url);
            httpost.setConfig(requestConfig);
            if(params!=null){
                ByteArrayEntity pentity = null;
                pentity = new ByteArrayEntity(params.getBytes("UTF-8"));
                httpost.setEntity(pentity);
            }

            response = httpclient.execute(httpost);
            log.debug("response status>>>>>"+response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }catch (Exception e){
            log.error("postString url["+url+"] error",e);
        }finally {
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public String postStringByteArrayEntity(String url, ByteArrayEntity pentity){
        CloseableHttpResponse response=null;
        String str=null;
        try {
            HttpPost httpost = new HttpPost(url);
            httpost.setConfig(requestConfig);
            if(pentity!=null){
                httpost.setEntity(pentity);
            }
            response = httpclient.execute(httpost);
            log.debug("response status>>>>>"+response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }catch (Exception e){
            log.error("postString",e);
        }finally {
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public String postJsonThrows(String url, Object jsonObje) throws  Exception {
        long start=System.currentTimeMillis();
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        String str=null;
        String req=null;
        if (jsonObje != null) {
            // 构造一个form表单式的实体
            req= JSON.toJSONString(jsonObje);
            StringEntity stringEntity = new StringEntity(req, ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }
        String code=getString(req);
        log.debug("req["+code+"]>>>>>"+req);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            log.debug("response status>>>>>"+response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }catch (Exception e){
            log.error("post json",e);
            throw  e;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
            long end=System.currentTimeMillis();
            log.debug("耗时["+url+"]>>>>"+(end-start)/1000+"秒");
        }
    }

    public String postFile(String url, String filename, File file, Map<String,Object> params){
        try {
            return postFile(url,filename, FileUtils.openInputStream(file),params);
        } catch (IOException e) {
            log.error("upload file error",e);
        }
        return null;
    }

    public String postFile(String url,String filename,InputStream inputStream,Map<String,Object> params){
        CloseableHttpResponse response=null;
        String str=null;
        try {
            HttpPost httpost = new HttpPost(url);
            httpost.setConfig(requestConfig);
            MultipartEntityBuilder multipartEntityBuilder=MultipartEntityBuilder.create();
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            if(params!=null){
                for(Map.Entry<String,Object> entry:params.entrySet()){
                    multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue().toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
                }
            }
            InputStreamBody bin = new InputStreamBody(inputStream,filename);
            multipartEntityBuilder.addPart("file",bin).setCharset(CharsetUtils.get("UTF-8"));
            httpost.setEntity(multipartEntityBuilder.build());
            response = httpclient.execute(httpost);
            log.debug("response status>>>>>"+response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }catch (Exception e){
            log.error("postString",e);
        }finally {
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public String postFile(String url,String filename,byte[] bytes,Map<String,Object> params){
        CloseableHttpResponse response=null;
        String str=null;
        try {
            HttpPost httpost = new HttpPost(url);
            httpost.setConfig(requestConfig);

            MultipartEntityBuilder multipartEntityBuilder=MultipartEntityBuilder.create();
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            if(params!=null){
                for(Map.Entry<String,Object> entry:params.entrySet()){
                    multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue().toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
                }
            }
            ByteArrayBody bin = new ByteArrayBody(bytes,filename);
            multipartEntityBuilder.addPart("file",bin).setCharset(CharsetUtils.get("UTF-8"));
            httpost.setEntity(multipartEntityBuilder.build());
            response = httpclient.execute(httpost);
            log.debug("response status>>>>>"+response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }catch (Exception e){
            log.error("postString",e);
        }finally {
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public String postWebservice(String postUrl, Map<String,Object> map, String interfaceName, String UserNo,WebserviceParseXml webserviceParseXml){
        String retStr = "";
        String soapXml=getWebserviceRequestParams(map, interfaceName, UserNo);
        CloseableHttpResponse response=null;
        try {
            HttpPost httpPost = new HttpPost(postUrl);
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
            httpPost.setHeader("SOAPAction", "http://tempuri.org/"+interfaceName);
            StringEntity data = new StringEntity(soapXml,Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            response = httpclient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                retStr = EntityUtils.toString(httpEntity, "UTF-8");
                return webserviceParseXml.parseXml(interfaceName,retStr);
            }
            return null;
        } catch (Exception e) {
            log.error("post service ["+interfaceName+"] error",e);
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public String sslPost(String url ,String params,String pass,String p12_file){
        CloseableHttpResponse response=null;
        InputStream inputStream=null;
        try{
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            inputStream =new FileInputStream(new File(p12_file)) ;
            keyStore.load(inputStream, pass.toCharArray());
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, pass.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            HttpPost httpost = new HttpPost(url);
            httpost.setConfig(requestConfig);
            ByteArrayEntity pentity = new ByteArrayEntity(params.getBytes("UTF-8"));
            httpost.setEntity(pentity);
            response = httpclient.execute(httpost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String xml= EntityUtils.toString(response.getEntity(), "UTF-8");
                if(log.isInfoEnabled()){
                    log.info("sslPost:--"+xml);
                }
                return xml;
            }
        }catch (Exception e){
            log.error("sslPost error",e);
        }finally {
            try {
                if(response!=null){
                    response.close();
                }
                if(inputStream!=null){
                    inputStream.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    static Pattern p = Pattern.compile(",\"Code\":\"(.*?)\"");

    private static String getString(String str){
        Matcher m = p.matcher(str);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            //使用 loadTrustMaterial() 方法实现一个信任策略，信任所有证书
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            //NoopHostnameVerifier类:  作为主机名验证工具，实质上关闭了主机名验证，它接受任何
            //有效的SSL会话并匹配到目标主机。
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            log.info("createSSLClientDefault error",e);
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    static private String getWebserviceRequestParams(Map<String,Object> params,String interfaceName,String UserNo){
        StringBuffer data=new StringBuffer();
        data.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        data.append("\n");
        data.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        data.append("\n");
        data.append("<soap:Body>");
        data.append("\n");
        data.append("<"+interfaceName+" xmlns=\"http://tempuri.org/\">");
        data.append("\n");
        if (params==null){
            params=new HashMap();
        }
        if (StringUtils.isNotBlank(UserNo)){
            params.put("UserNo",UserNo);
        }
        if (params!=null) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                data.append("<" + key + ">" + params.get(key) + "</" + key + ">");
            }
        }
        data.append("</"+interfaceName+">");
        data.append("\n");
        data.append("</soap:Body>");
        data.append("</soap:Envelope>");
        return data.toString();
    }

}
