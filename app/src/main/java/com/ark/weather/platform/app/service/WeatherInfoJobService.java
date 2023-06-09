package com.ark.weather.platform.app.service;

import com.ark.weather.platform.app.command.update.WeatherInfoJobExe;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 天气任务服务类
 *
 */
@Service
public class WeatherInfoJobService {
    @Resource
    private WeatherInfoJobExe weatherInfoJobExe;

    /**
     * 获取天气信息任务
     */
    public void getWeatherInfo(boolean cityTask, boolean stationTask) {
        // 执行获取天气信息任务
        if (stationTask) {
            weatherInfoJobExe.getStationWeatherInfoTask();
        }
        if (cityTask) {
            weatherInfoJobExe.getCityWeatherInfoTask();
        }
    }
}
