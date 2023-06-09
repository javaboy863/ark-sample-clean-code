package com.ark.weather.platform.domain.repository.db;

import com.ark.weather.platform.domain.domain.WeatherCurrentInfoPO;

/**
 */
public interface IWeatherCurrentInfoRepository {
    /**
     * 保存门店天气实况信息
     *
     * @param weatherCurrentInfoPO
     * @return 插入数量
     */
    int save(WeatherCurrentInfoPO weatherCurrentInfoPO);
}
