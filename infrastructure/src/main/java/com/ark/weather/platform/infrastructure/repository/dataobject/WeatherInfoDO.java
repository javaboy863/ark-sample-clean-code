package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 天气信息领域对象
 */
@Data
public class WeatherInfoDO {
    /**
     * 观察时间, 天气数据的实际观测时间
     */
    private Date obsTime;
    /**
     * 天气数据对应的日期
     */
    private Date forecastTime;
    /**
     * 国标AQI
     */
    private Integer aqiChn;
    /**
     * PM25浓度(μg/m3)
     */
    private Integer pm25;
    /**
     * 能见度
     */
    private BigDecimal visibility;
    /**
     * 风向角度, 0 ~ 360
     */
    private BigDecimal windDegrees;
    /**
     * 风向, 中文名称
     */
    private String windDir;
    /**
     * 风速
     */
    private BigDecimal windSpeed;
    /**
     * 风力等级, 0 ~ 17
     */
    private Integer windLevel;
    /**
     * 温度
     */
    private BigDecimal temperature;
    /**
     * 相对湿度, 百分比，1 ~ 100
     */
    private Integer humidity;
    /**
     * 降水量, 过去一小时降水量
     */
    private BigDecimal precipitation;
    /**
     * 主要天气现象代码
     */
    private String skycon;
    /**
     * 主要天气现象描述
     */
    private String skyconName;
    /**
     * 白天天气现象代码
     */
    private String skyconDay;
    /**
     * 白天天气现象描述
     */
    private String skyconDayName;
    /**
     * 夜间天气现象代码
     */
    private String skyconNight;
    /**
     * 夜间天气现象描述
     */
    private String skyconNightName;
    /**
     * 自然语言描述。描述中如有时间性描述，基准为obs_time
     */
    private String description;
}
