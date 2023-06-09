package com.ark.weather.platform.app.service;

import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.SpringBaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * 门店任务测试类
 *
 */
@Slf4j
public class StationInfoJobServiceTest extends SpringBaseTest {
    @Resource
    private StationInfoJobService stationInfoJobService;
    @Resource
    private RedisStationCache redisStationCache;

    @Test
    public void testGetWeatherInfo() {
        // 这里要是打印不为空，就是com.ark.weather.platform.configuration.AutoLoadStationData类影响，可以先注释掉再测试
        log.info("localCacheBefore: {}", JSON.toJSONString(redisStationCache.getAllCache()));
        log.info("-------");
        stationInfoJobService.cacheAllOpenStationJob();
        log.info("localCacheAfter: {}", JSON.toJSONString(redisStationCache.getAllCache()));
    }
}

