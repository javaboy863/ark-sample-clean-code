package com.ark.weather.platform.infrastructure.repository.db;


import com.ark.weather.platform.domain.domain.WeatherAlertInfoPO;

/**
 */
public interface WeatherAlertInfoMapper {
    /**
     * 插入门店天气预警信息
     *
     * @param record 数据
     * @return
     */
    int insert(WeatherAlertInfoPO record);

    /**
     * 查询预警数据总数
     * @return
     */
    int count();
}
