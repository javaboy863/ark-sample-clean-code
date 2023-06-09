package com.ark.weather.platform.infrastructure.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * RestTemplate配置类
 *
 */
@Configuration
@Slf4j
public class RestTemplateConfig {
    public static final int SOCKET_TIMEOUT = 4000;

    public static final int CONNECT_TIMEOUT = 4000;

    public static final int CONNECTION_REQUEST_TIMEOUT = 2000;

    public static final int MAX_TOTAL = 400;
    public static final int DEFAULT_MAX_PER_ROUTE = 200;
    public static final int RETRY_COUNT = 2;
    public static final int VALIDATE_AFTER_INACTIVITY = 4000;


    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
        //我们采用RestTemplate内部的MessageConverter
        //重新设置StringHttpMessageConverter字符集，解决中文乱码问题
        modifyDefaultCharset(restTemplate);
        //设置错误处理器
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        connectionManager.setDefaultSocketConfig(socketConfig);

        //设置整个连接池最大连接数
        connectionManager.setMaxTotal(MAX_TOTAL);
        //路由是对maxTotal的细分
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        RequestConfig requestConfig = RequestConfig.custom()
                //服务器返回数据(response)的时间，超过该时间抛出read timeout
                .setSocketTimeout(SOCKET_TIMEOUT)
                //连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
                .setConnectTimeout(CONNECT_TIMEOUT)
                //从连接池中获取连接的超时时间，超过该时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(RETRY_COUNT, true))
                .setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
                .build();
    }



    /**
     * 修改默认的字符集类型为utf-8
     *
     * @param restTemplate
     */
    private void modifyDefaultCharset(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (StringHttpMessageConverter.class == item.getClass()) {
                converterTarget = item;
                break;
            }
        }
        if (null != converterTarget) {
            converterList.remove(converterTarget);
        }
        Charset defaultCharset = Charset.forName("utf-8");
        converterList.add(1, new StringHttpMessageConverter(defaultCharset));
    }

    /**
     * KeepAlive策略
     */
    public class CustomConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {
        private final long DEFAULT_SECONDS = 30;
        @Override
        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
            return Arrays.asList(response.getHeaders(HTTP.CONN_KEEP_ALIVE))
                    .stream()
                    .filter(h -> StringUtils.equalsIgnoreCase(h.getName(), "timeout")
                            && StringUtils.isNumeric(h.getValue()))
                    .findFirst()
                    .map(h -> NumberUtils.toLong(h.getValue(), DEFAULT_SECONDS))
                    .orElse(DEFAULT_SECONDS) * 1000;
        }
    }
}


