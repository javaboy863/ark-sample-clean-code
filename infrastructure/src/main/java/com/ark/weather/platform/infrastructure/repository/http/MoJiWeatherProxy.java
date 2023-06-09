package com.ark.weather.platform.infrastructure.repository.http;

import com.ark.weather.platform.infrastructure.common.CommonConfig;
import com.ark.weather.platform.infrastructure.repository.dataobject.MoJiWeatherAllInfoDO;
import com.ark.weather.platform.infrastructure.util.HttpUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 墨迹天气远程请求代理类
 *
 */
@Service
@Slf4j
public class MoJiWeatherProxy {
    /**
     * 墨迹天气的默认语音参数
     */
    private static final String COUNTRY_LANGUAGE = "zh-CN";
    @Resource
    protected HttpUtils httpUtils;
    @Resource
    protected CommonConfig commonConfig;

    /**
     * 获取天气详情信息
     *
     * @param lat 经度
     * @param lng 纬度
     * @return
     */
    public MoJiWeatherAllInfoDO getMoJiWeatherAllInfo(double lat, double lng) {
        log.info("[墨迹天气-获取天气信息-请求第三方参数]：{} - {}", lat, lng);
        try {
            Map<String, String> params = getParams(lat, lng);
            MoJiWeatherAllInfoDO moJiWeatherAllInfoDO = httpUtils.get(commonConfig.getMoJiUrl(), null, params, MoJiWeatherAllInfoDO.class);
            log.info("[墨迹天气-获取天气信息-返回]：{}", moJiWeatherAllInfoDO);
            return moJiWeatherAllInfoDO;
        } catch (Exception ex) {
            log.error("[墨迹天气-获取天气信息-调用异常]：{} - {}， err: {}", lat, lng, ex);
        }
        return null;
    }


    /**
     * 获取请求参数
     *
     * @param lat
     * @param lng
     * @return
     */
    private Map<String, String> getParams(double lat, double lng) {
        Map<String, String> params = Maps.newHashMap();
        String timestamp = String.valueOf(System.currentTimeMillis());
        params.put("timestamp", timestamp);
        params.put("token", commonConfig.getMoJiToken());
        params.put("lat", String.valueOf(lat));
        params.put("lon", String.valueOf(lng));
        params.put("language", COUNTRY_LANGUAGE);
        params.put("key", getKey(commonConfig.getMoJiPassword(), timestamp, String.valueOf(lat), String.valueOf(lng)));
        return params;
    }

    /**
     * 根据paramMap和秘钥进行签名
     *
     * @return
     */
    private String getKey(String... params) {
        String string = StringUtils.join(params, "");
        //md5Key
        return Md5Util.MD5Encode(string, "MD5");
    }
}
