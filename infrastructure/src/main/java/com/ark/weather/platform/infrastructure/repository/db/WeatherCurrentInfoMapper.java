package com.ark.weather.platform.infrastructure.repository.db;


import com.ark.weather.platform.domain.domain.WeatherCurrentInfoPO;

/**
 */
public interface WeatherCurrentInfoMapper {
    /**
     * 插入门店天气实况信息
     *
     * @param record 数据
     * @return
     */
    int insert(WeatherCurrentInfoPO record);
}
