package com.ark.weather.platform.domain.repository.db;

import com.ark.weather.platform.domain.domain.WeatherAlertInfoPO;

/**
 */
public interface IWeatherAlertInfoRepository {
    /**
     * 保存门店天气预警信息信息
     *
     * @param weatherAlertInfoPO
     * @return 插入数量
     */
    int save(WeatherAlertInfoPO weatherAlertInfoPO);

    /**
     * 预警总数
     * @return
     */
    int count();
}
