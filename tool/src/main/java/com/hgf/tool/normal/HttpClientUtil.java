package com.hgf.tool.normal;

import com.alibaba.excel.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>Description: HttpClient工具</p>
 */
public class HttpClientUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 最大连接数
     */
    private static final int POOL_MAX_TOTAL = 100;
    /**
     * 每个路由的最大连接数,特别注意:若不设置，则默认2
     */
    private static final int POOL_MAX_PER_ROUTE = 100;
    /**
     * 连接超时时间
     */
    private static final int CONNECT_TIMEOUT = 6 * 1000;
    /**
     * 等待数据超时时间
     */
    private static final int SOCKET_TIMEOUT = 6 * 1000;
    /**
     * 从连接池获取连接的超时时间
     */
    private static final int CONNECT_REQUEST_TIMEOUT = 5 * 1000;

    /**
     * 默认编码
     */
    private static final String DEFAULT_ENCODE = "UTF-8";

    private static final String FORM_AND = "&";
    private static final String FORM_EQUAL = "=";
    private static final String FORM_QUESTION = "?";

    private static final PoolingHttpClientConnectionManager connMgr;
    private static final RequestConfig requestConfig;
    private static SSLConnectionSocketFactory sslSF;

    static {
        connMgr = new PoolingHttpClientConnectionManager();
        connMgr.setMaxTotal(POOL_MAX_TOTAL);
        connMgr.setDefaultMaxPerRoute(POOL_MAX_PER_ROUTE);

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        configBuilder.setConnectTimeout(CONNECT_TIMEOUT);
        configBuilder.setSocketTimeout(SOCKET_TIMEOUT);
        configBuilder.setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT);
        requestConfig = configBuilder.build();

//        try {
//            // 信任所有
//            SSLContext sslContext = new SSLContextBuilder()
//                    .loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true)
//                    .build();
//
//            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
//            sslSF = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
//        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
//            LOG.error("SSLContext构建异常", e);
//        }

        try {
            // 另一种方式，绕过验证，针对12306这些网址
            SSLContext sslContext = createIgnoreVerifySSL();

            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            sslSF = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            LOG.error("SSLContext构建异常", e);
        }
    }

    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("TLS");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

    private HttpClientUtil() {

    }

    private static class HttpClientSingleHolder {
        private static final CloseableHttpClient HTTP_CLIENT_INSTANCE = HttpClients.custom()
                .setConnectionManager(connMgr)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    private static class HttpsClientSingleHolder {
        private static final CloseableHttpClient SSL_CLIENT_INSTANCE = HttpClients.custom()
                .setConnectionManager(connMgr)
                .setDefaultRequestConfig(requestConfig)
                .setSSLSocketFactory(sslSF)
                .build();
    }

    /**
     * 获取HTTPClient实例
     *
     * @Return org.apache.http.impl.client.CloseableHttpClient
     */
    private static CloseableHttpClient getHttpClient() {
        return HttpClientSingleHolder.HTTP_CLIENT_INSTANCE;
    }

    /**
     * 获取SSLClient实例
     *
     * @Return org.apache.http.impl.client.CloseableHttpClient
     */
    private static CloseableHttpClient getHttpsClient() {
        return HttpsClientSingleHolder.SSL_CLIENT_INSTANCE;
    }

    /**
     * 发送POST请求（HTTP/HTTPS协议、JSON形式）
     *
     * @param url           请求URL
     * @param json          json格式请求参数
     * @param authorization 请求授权（可为空）
     * @param isHttps       是否https请求（默认否）
     * @Return java.lang.String
     */
    public static String doPost(String url, String json, String authorization, boolean isHttps) {
        Map<String, String> authorizationMap = new HashMap<>();
        if (StringUtils.isNotBlank(authorization)) {
            authorizationMap.put("Authorization", authorization);
        }
        return HttpClientUtil.doHttpPost(url, json, authorizationMap, isHttps);
    }

    /**
     * 发送POST请求（HTTP/HTTPS协议、JSON形式）
     *
     * @param url       请求URL
     * @param json      json格式请求参数
     * @param headerMap
     * @param isHttps   是否https请求（默认否）
     * @Return java.lang.String
     */
    public static String doHttpPost(String url, String json, Map<String, String> headerMap, boolean isHttps) {
        CloseableHttpClient httpClient;
        if (isHttps) {
            httpClient = getHttpsClient();
        } else {
            httpClient = getHttpClient();
        }
        int code = 0;
        String responseStr = null;
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        try {
            // 解决中文乱码问题
            StringEntity stringEntity = new StringEntity(json,
                    "UTF-8");
            stringEntity.setContentEncoding(DEFAULT_ENCODE);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            if (Objects.nonNull(headerMap) && !headerMap.isEmpty()) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            code = response.getStatusLine().getStatusCode();
            responseStr = EntityUtils.toString(entity, DEFAULT_ENCODE);
        } catch (IOException e) {
            LOG.error("IO异常", e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.error("IO异常", e);
                }
            }
        }

        if (code != HttpStatus.SC_OK) {
            LOG.error("doPost code:{}, data:{}", code, responseStr);
            return null;
        }
        return responseStr;
    }

    /**
     * @param url
     * @param json
     * @return
     */
    public static String doPost(String url, String json, boolean isHttps) {
        return HttpClientUtil.doPost(url, json, null, isHttps);
    }

    /**
     * @param url
     * @param json
     * @return
     */
    public static String doHttpsPost(String url, String json) {
        return HttpClientUtil.doPost(url, json, null, true);
    }

    /**
     * 根据参数组装URL
     *
     * @param url    请求URL
     * @param params 参数
     */
    public static String formatGetURL(String url, Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder requestUrl = new StringBuilder(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value = entry.getValue();
            if (value == null || "".equals(value)) {
                continue;
            }

            String key = entry.getKey();
            value = URLEncoder.encode(value, DEFAULT_ENCODE);
            if (requestUrl.toString().matches(".*\\?.*")) {
                requestUrl.append(FORM_AND);
            } else {
                requestUrl.append(FORM_QUESTION);
            }
            requestUrl.append(key).append(FORM_EQUAL).append(value);
        }
        return requestUrl.toString();
    }

    /**
     * HTTP GET 请求
     *
     * @param url 请求URL
     * @return String
     */
    public static String doGet(String url) {
        return doGet(url, null, false);
    }

    /**
     * HTTP/HTTPS GET 请求
     *
     * @param url
     * @param isHttps 是否HTTPS协议
     * @return String
     */
    public static String doGet(String url, boolean isHttps) {
        return doGet(url, null, isHttps);
    }

    /**
     * GET请求（HTTP/HTTPS）
     *
     * @param url
     * @param params
     * @param isHttps
     * @return java.lang.String
     * @author chennanhao
     * @date 2019/11/8 18:42
     * @since Blue2.5
     */
    public static String doGet(String url, Map<String, String> params, boolean isHttps) {
        return doGet(url, null, params, isHttps);
    }

    /**
     * GET请求（HTTP/HTTPS）
     *
     * @param url     地址
     * @param params  参数
     * @param isHttps 是否https
     * @return String
     */
    public static String doGet(String url, Map<String, String> headerMap, Map<String, String> params, boolean isHttps) {
        HttpEntity responseEntity;
        CloseableHttpClient httpClient;
        if (isHttps) {
            httpClient = getHttpsClient();
        } else {
            httpClient = getHttpClient();
        }
        int code = 0;
        String reponseStr = null;
        HttpResponse response = null;
        try {
            if (params != null && !params.isEmpty()) {
                url = formatGetURL(url, params);
            }
            HttpGet httpGet = new HttpGet(url);
            if (Objects.nonNull(headerMap) && !headerMap.isEmpty()) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }
            response = httpClient.execute(httpGet);
            responseEntity = response.getEntity();
            code = response.getStatusLine().getStatusCode();
            reponseStr = EntityUtils.toString(responseEntity, DEFAULT_ENCODE);
            if (code != HttpStatus.SC_OK) {
                LOG.error("doGet code:{}, data:{}", code, reponseStr);
                return null;
            }
        } catch (IOException e) {
            LOG.error("IO异常", e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.error("IO异常", e);
                }
            }
        }
        return reponseStr;
    }

    /**
     * HTTP/HTTPS 获取文件
     * ps:使用时，注意资源释放ByteArrayOutputStream
     *
     * @param url 文件URL
     * @return ByteArrayOutputStream
     */
    public static ByteArrayOutputStream doGetFile(String url, boolean isHttps) {
        ByteArrayOutputStream outStream = null;
        CloseableHttpClient httpClient;
        if (isHttps) {
            httpClient = getHttpsClient();
        } else {
            httpClient = getHttpClient();
        }
        int code = 0;
        HttpResponse response = null;
        InputStream in = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK) {
                LOG.error("doGet code:{}, data:{}", code, EntityUtils.toString(entity, DEFAULT_ENCODE));
                return null;
            }
            in = entity.getContent();
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
        } catch (UnsupportedOperationException | IOException e) {
            LOG.error("IO异常", e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.error("IO异常", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOG.error("IO异常", e);
                }
            }
        }
        return outStream;
    }

    /**
     * post请求, 响应体为二进制图片数据或Json数据
     *
     * @param url
     * @param body
     * @return
     */
    public static byte[] responseBinaryPost(String url, String body) {
        LOG.info("body: {}", body);
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-type", "application/json; charset=UTF-8");
        StringEntity s = new StringEntity(body, StandardCharsets.UTF_8);
        LOG.info("jsonParameter: {}", s);
        s.setContentEncoding(StandardCharsets.UTF_8.name());
        request.setEntity(s);
        try (CloseableHttpClient httpclient = HttpClients.createDefault();
             CloseableHttpResponse response = httpclient.execute(request)) {
            HttpEntity entity = Optional.ofNullable(response.getEntity())
                    .orElseThrow(IOException::new);

            LOG.info("response: {}", response);
            String contentType = response.getFirstHeader("Content-Type").getValue();
            if (Objects.equals(contentType, "image/jpeg")) {
                InputStream inputStream = entity.getContent();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }

                byte[] bytes = outStream.toByteArray();

                inputStream.close();
                outStream.close();
                return bytes;
            } else if (Objects.equals(contentType, "application/json; charset=UTF-8")) {
                String msg = EntityUtils.toString(entity);
                LOG.info("Json响应体: {}", msg);
                return null;
            } else {
                String msg = EntityUtils.toString(entity);
                LOG.error("未知数据: {}", msg);
                return null;
            }
        } catch (IOException e) {
            LOG.error("HTTP请求失败", e);
            return null;
        }
    }

}
