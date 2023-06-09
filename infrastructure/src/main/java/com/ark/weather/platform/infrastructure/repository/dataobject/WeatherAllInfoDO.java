package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

import java.util.List;

/**
 * 天气领域对象
 *
 */
@Data
public class WeatherAllInfoDO {
    /**
     * 天气预警信息
     */
    private List<WeatherAlertInfoDO> alert;
    /**
     * 当前天气信息
     */
    private WeatherInfoDO realtime;
    /**
     * 预报天气数据（分钟粒度）
     */
    private List<WeatherInfoDO> minutely;
    /**
     * 预报天气数据（小时粒度）
     */
    private List<WeatherInfoDO> hourly;
    /**
     * 预报天气数据（天粒度）
     */
    private List<WeatherInfoDO> daily;
}
