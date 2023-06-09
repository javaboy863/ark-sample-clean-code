package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

/**
 * 彩云天气请求返回的天气所有信息
 *
 */
@Data
public class CaiYunWeatherAllInfoDataDO {
    /**
     * 预警数据信息
     */
    private CaiYunWeatherAlertDO alert;
    /**
     * 实况数据信息
     */
    private CaiYunWeatherRealTimeDO realtime;

    /**
     * 逐分钟预报天气数据
     */
    private CaiYunWeatherMinutelyDO minutely;

    /**
     * 逐小时预报天气数据
     */
    private CaiYunWeatherHourlyDO hourly;

    /**
     * 逐天预报天气数据
     */
    private CaiYunWeatherDailyDO daily;

}
