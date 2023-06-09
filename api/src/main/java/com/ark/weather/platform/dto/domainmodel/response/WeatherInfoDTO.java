package com.ark.weather.platform.dto.domainmodel.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * RPC天气服务的返回对象
 *
 */
@Data
public class WeatherInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 观察时间, 天气数据的实际观测时间
     */
    private Date obsTime;

    /**
     * 自然语言描述，可能为空
     * 如果描述中存在参考时间，则参考时间为obsTime
     */
    private String description;
    /**
     * 对应请求时间粒度的时间格式字符串
     */
    private String date;
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
    private Double visibility;
    /**
     * 风向角度, 0 ~ 360
     */
    private Double windDegrees;
    /**
     * 风向, 中文名称
     */
    private String windDir;

    /**
     * 风速，公里/小时（km/hr）
     */
    private Double windSpeed;
    /**
     * 风力等级, 0 ~ 17
     */
    private Integer windLevel;

    /**
     * 温度，摄氏度
     */
    private Double temperature;
    /**
     * 相对湿度, 百分比，1 ~ 100
     */
    private Integer humidity;
    /**
     * 降水量, 过去一小时降水量, 毫米/小时(mm/hr)
     */
    private Double precipitation;
    /**
     * 主要天气现象编码
     */
    private String skycon;
    /**
     * 主要天气现象描述
     */
    private String skyconName;
    /**
     * 白天天气现象编码
     */
    private String skyconDay;
    /**
     * 白天天气现象描述
     */
    private String skyconDayName;

    /**
     * 夜间天气现象编码
     */
    private String skyconNight;
    /**
     * 夜间天气现象描述
     */
    private String skyconNightName;

}
