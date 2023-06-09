package com.ark.weather.platform.infrastructure.http;

import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.SpringBaseTest;
import com.ark.weather.platform.infrastructure.repository.dataobject.CaiYunWeatherAllInfoDO;
import com.ark.weather.platform.infrastructure.repository.http.CaiYunWeatherProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * 彩云天气测试类
 *
 */
@Slf4j
public class CaiYunWeatherProxyTest extends SpringBaseTest {
    //
    @Resource
    private CaiYunWeatherProxy caiYunWeatherProxy;

    @Test
    public void testGetCaiYunWeatherAllInfo() {
        double lat = 39.90403;
        double lng = 116.407526;
        CaiYunWeatherAllInfoDO caiYunWeatherAllInfo = caiYunWeatherProxy.getCaiYunWeatherAllInfo(lat, lng);
        log.info("result: {}", JSON.toJSONString(caiYunWeatherAllInfo));
    }


    @Test
    public void testGetCaiYunWeatherDailyInfo() {
        double lat = 39.90403;
        double lng = 116.407526;
        CaiYunWeatherAllInfoDO caiYunWeatherAllInfo = caiYunWeatherProxy.getCaiYunWeatherDailyInfo(lat, lng);
        log.info("result: {}", JSON.toJSONString(caiYunWeatherAllInfo));
    }
}
