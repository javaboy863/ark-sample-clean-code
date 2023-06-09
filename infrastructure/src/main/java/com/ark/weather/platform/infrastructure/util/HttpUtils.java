package com.ark.weather.platform.infrastructure.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;

/**
 * Http 工具包
 *
 */

@Slf4j
@Component
public class HttpUtils {

    @Resource
    private RestTemplate restTemplate;

    /**
     * @param url
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T post(String url, String jsonStr, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String value = jsonStr;
        if (StringUtils.isBlank(value)) {
            value = "{}";
        }
        HttpEntity<String> request = new HttpEntity<>(value, headers);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, request, clazz);
        log.info("postForEntity responseEntity:{}", JSONObject.toJSONString(responseEntity));
        return responseEntity.getBody();
    }


    /**
     * get 请求
     *
     * @param url
     * @param headerMap
     * @param params
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String url, Map<String, String> headerMap, Map<String, String> params, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        if (!CollectionUtils.isEmpty(headerMap)) {
            headerMap.forEach((k, v) -> headers.add(k, v));
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        if (!CollectionUtils.isEmpty(params)) {
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            int i = 0;
            StringBuilder urlBuilder = new StringBuilder(url);
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                if (i == 0) {
                    urlBuilder.append("?");
                } else {
                    urlBuilder.append("&");
                }
                urlBuilder.append(next.getKey()).append("=").append(next.getValue());
                i++;
            }
            url = urlBuilder.toString();
        }
        log.info("get url:{}", url);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, clazz);
        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            return responseEntity.getBody();
        } else {
            log.error("#method# 远程调用失败 httpCode = [{}]", responseEntity.getStatusCode());
        }
        return null;
    }
}
