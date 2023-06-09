package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

import java.util.List;

/**
 * hhh
 *
 */
@Data
public class MoJiWeatherDataDO {
    /**
     * 预警数据
     */
    private List<MoJiWeatherAlertDataDO> alerts;
    /**
     * 城市信息
     */
    private MoJiWeatherCityDataDO city;
    /**
     * 实况天气数据
     */
    private MoJiWeatherCurrentDataDO current;
}
