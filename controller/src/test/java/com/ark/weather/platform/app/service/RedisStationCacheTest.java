package com.ark.weather.platform.app.service;

import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.SpringBaseTest;
import com.ark.weather.platform.infrastructure.repository.dataobject.StationInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * redis缓存测试类
 *
 */
@Slf4j
public class RedisStationCacheTest extends SpringBaseTest {
    @Resource
    private RedisStationCache redisStationCache;


    @Test
    public void testPutAndAsMap() {
        StationInfoDO abcStation = new StationInfoDO();
        abcStation.setStationCode("abc");

        StationInfoDO defStation = new StationInfoDO();
        defStation.setStationCode("def");


        StationInfoDO xyzStation = new StationInfoDO();
        xyzStation.setStationCode("xyz");

        Map<String, StationInfoDO> allCache1 = redisStationCache.getAllCache();
        log.info("All station1:{}", JSON.toJSONString(allCache1));

        redisStationCache.put(abcStation.getStationCode(), abcStation);


        StationInfoDO stationInfoDO = redisStationCache.get(abcStation.getStationCode());
        log.info("abc sstationDO:{}", JSON.toJSONString(stationInfoDO));

        redisStationCache.put(defStation.getStationCode(), defStation);

        Map<String, StationInfoDO> allCache2 = redisStationCache.getAllCache();
        log.info("All station2:{}", JSON.toJSONString(allCache2));


        Map<String, StationInfoDO> map = new HashMap<>(3);
        map.put(xyzStation.getStationCode(), xyzStation);
        map.put(defStation.getStationCode(), defStation);
        map.put(abcStation.getStationCode(), abcStation);
        redisStationCache.putAll(map);

        Map<String, StationInfoDO> allCache3 = redisStationCache.getAllCache();
        log.info(JSON.toJSONString(allCache3));
    }

}
