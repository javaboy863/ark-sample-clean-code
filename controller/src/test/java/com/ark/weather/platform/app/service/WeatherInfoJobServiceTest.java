package com.ark.weather.platform.app.service;

import com.ark.weather.platform.SpringBaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * 天气定时任务测试类
 *
 */
public class WeatherInfoJobServiceTest extends SpringBaseTest {
    @Resource
    private WeatherInfoJobService weatherInfoJobService;
    @Resource
    private StationInfoJobService stationInfoJobService;

    @Test
    public void testGetWeatherInfo() {
        stationInfoJobService.cacheAllOpenStationJob();
        for (int i = 0; i < 1000; i++) {
            weatherInfoJobService.getWeatherInfo(false, true);
        }
    }
}
