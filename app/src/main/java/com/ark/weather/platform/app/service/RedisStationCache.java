package com.ark.weather.platform.app.service;

import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.infrastructure.common.CommonConfig;
import com.ark.weather.platform.infrastructure.repository.dataobject.StationInfoDO;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * redis缓存服务类
 *
 */
@Slf4j
@Service
public class RedisStationCache {
    /**
     * 存储门店(门店是hash结构)的Key
     */
    private static final String WEATHER_STATION_KEY = "WEATHER::STATION";
    @Resource
    private Sedis sedis;
    @Resource
    private CommonConfig commonConfig;

    /**
     * 获取所有门店缓存数据
     *
     * @return
     */
    public synchronized Map<String, StationInfoDO> getAllCache() {
        Map<String, String> stationValues = sedis.hgetAll(WEATHER_STATION_KEY);

        Map<String, StationInfoDO> retStationMap = Maps.newHashMapWithExpectedSize(stationValues.size());
        for (Map.Entry<String, String> entry : stationValues.entrySet()) {
            retStationMap.put(entry.getKey(), JSON.parseObject(entry.getValue(), StationInfoDO.class));
        }
        return retStationMap;
    }

    /**
     * 放入批量缓存
     *
     * @param map
     */
    public synchronized void putAll(Map<String, StationInfoDO> map) {
        if (map == null || map.size() == 0) {
            return;
        }
        Map<String, String> cacheMap = Maps.newHashMapWithExpectedSize(map.size());
        for (Map.Entry<String, StationInfoDO> entry : map.entrySet()) {
            if (StringUtils.isBlank(entry.getKey())) {
                continue;
            }
            cacheMap.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
        }
        sedis.del(WEATHER_STATION_KEY);
        sedis.hmset(WEATHER_STATION_KEY, cacheMap);
        Integer stationCacheExpireSeconds = commonConfig.getStationCacheExpireSeconds();
        if (stationCacheExpireSeconds != null) {
            sedis.expire(WEATHER_STATION_KEY, stationCacheExpireSeconds);
        }
    }

    /**
     * 获取单个缓存
     *
     * @param key
     */
    public synchronized StationInfoDO get(String key) {
        try {
            String value = sedis.hget(WEATHER_STATION_KEY, key);
            if (StringUtils.isNotBlank(value)) {
                return JSON.parseObject(value, StationInfoDO.class);
            }
        } catch (Exception ex) {
            log.error("缓存内容转换为门店异常。key:{}, err:{}", key, ex);
        }
        return null;
    }

    /**
     * 放入单个缓存
     *
     * @param key
     * @param value
     */
    public void put(String key, StationInfoDO value) {
        sedis.hset(WEATHER_STATION_KEY, key, JSON.toJSONString(value));
        Integer stationCacheExpireSeconds = commonConfig.getStationCacheExpireSeconds();
        if (stationCacheExpireSeconds != null) {
            sedis.expire(WEATHER_STATION_KEY, stationCacheExpireSeconds);
        }
    }


}
