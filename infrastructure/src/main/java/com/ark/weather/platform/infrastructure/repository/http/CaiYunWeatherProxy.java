package com.ark.weather.platform.infrastructure.repository.http;

import ch.hsr.geohash.GeoHash;
import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.infrastructure.common.CommonConfig;
import com.ark.weather.platform.infrastructure.repository.dataobject.CaiYunWeatherAllInfoDO;
import com.ark.weather.platform.infrastructure.util.HttpUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 彩云天气远程请求代理类
 *
 */
@Service
@Slf4j
public class CaiYunWeatherProxy {
    /**
     * geoHash的长度， 误差+/-2KM范围
     */
    private static final int GEO_HASH_LEN = 5;
    /**
     * 天气缓存key前缀
     */
    private static final String WEATHER_GEOHASH_CACHE_PRE = " WEATHER::GEOHASH::";
    public static final String STATUS_OK = "ok";

    @Resource
    protected HttpUtils httpUtils;
    @Resource
    protected CommonConfig commonConfig;
    @Resource
    private Sedis sedis;

    /**
     * 获取彩云天气的实时信息和报警信息
     *
     * @param lat
     * @param lng
     */
    public CaiYunWeatherAllInfoDO getCaiYunWeatherAllInfo(double lat, double lng) {
        log.info("[彩云天气-获取天气通用信息-请求第三方参数]：{} - {}", lat, lng);
        CaiYunWeatherAllInfoDO cacheWeatherAllInfo = getByCacheWeatherInfo(lat, lng);
        if (cacheWeatherAllInfo != null) {
            log.info("[彩云天气-获取天气通用信息-缓存返回]：{}", JSON.toJSONString(cacheWeatherAllInfo));
            return cacheWeatherAllInfo;
        }
        try {
            String url = String.format(commonConfig.getCaiYunUrlFormat(), commonConfig.getCaiYunToken(), lng, lat);
            CaiYunWeatherAllInfoDO weatherAllInfo = httpUtils.get(url, null, getParams(), CaiYunWeatherAllInfoDO.class);
            log.info("[彩云天气-获取天气通用信息-返回]：{}", JSON.toJSONString(weatherAllInfo));
            setWeatherInfoCache(lat, lng, weatherAllInfo);
            return weatherAllInfo;
        } catch (Exception ex) {
            log.error("[彩云天气-获取天气通用信息-调用异常]：{} - {}， err: {}", lat, lng, ex);
        }
        return null;
    }


    /**
     * 获取彩云天气的未来天气预报信息
     * 返回的对象中只有【result.daily】信息，其他信息无
     *
     * @param lat
     * @param lng
     */
    public CaiYunWeatherAllInfoDO getCaiYunWeatherDailyInfo(double lat, double lng) {
        log.info("[彩云天气-获取天级预报接口信息-请求第三方参数]：{} - {}", lat, lng);
        try {
            String url = String.format(commonConfig.getCaiYunDailyUrlFormat(), commonConfig.getCaiYunToken(), lng, lat);
            CaiYunWeatherAllInfoDO weatherAllInfo = httpUtils.get(url, null, getParams(), CaiYunWeatherAllInfoDO.class);
            log.info("[彩云天气-获取天级预报接口信息-返回]：{}", JSON.toJSONString(weatherAllInfo));
            return weatherAllInfo;
        } catch (Exception ex) {
            log.error("[彩云天气-获取天级预报接口信息-调用异常]：{} - {}， err: {}", lat, lng, ex);
        }
        return null;
    }

    /**
     * 通过经纬度获取geohash，并从缓存中获取数据
     *
     * @param lat
     * @param lng
     * @return
     */
    private CaiYunWeatherAllInfoDO getByCacheWeatherInfo(double lat, double lng) {
        try {
            String geoHash = GeoHash.withCharacterPrecision(lat, lng, GEO_HASH_LEN).toBase32();
            Long ttl = sedis.ttl(WEATHER_GEOHASH_CACHE_PRE + geoHash);
            if (ttl == null || ttl <= 0) {
                return null;
            }
            String info = sedis.get(WEATHER_GEOHASH_CACHE_PRE + geoHash);
            if (StringUtils.isNotBlank(info)) {
                return JSON.parseObject(info, CaiYunWeatherAllInfoDO.class);
            }
        } catch (Exception ex) {
            log.warn("获取缓存数据获取异常", ex);
        }
        return null;
    }

    /**
     * 缓存天气信息
     *
     * @param lat
     * @param lng
     * @param caiYunWeatherAllInfoDO
     */
    private void setWeatherInfoCache(double lat, double lng, CaiYunWeatherAllInfoDO caiYunWeatherAllInfoDO) {
        if (caiYunWeatherAllInfoDO == null || !StringUtils.equals(STATUS_OK, caiYunWeatherAllInfoDO.getStatus())) {
            return;
        }
        try {
            String geoHash = GeoHash.withCharacterPrecision(lat, lng, GEO_HASH_LEN).toBase32();
            sedis.set(WEATHER_GEOHASH_CACHE_PRE + geoHash, JSON.toJSONString(caiYunWeatherAllInfoDO));
            sedis.expire(WEATHER_GEOHASH_CACHE_PRE + geoHash, commonConfig.getWeatherCacheExpireSeconds());
        } catch (Exception ex) {
            log.warn("缓存数据设置异常", ex);
        }
    }


    /**
     * 获取请求参数
     * 开启同时获取预警信息
     *
     * @return
     */
    private Map<String, String> getParams() {
        return Maps.newHashMap();
    }

}
