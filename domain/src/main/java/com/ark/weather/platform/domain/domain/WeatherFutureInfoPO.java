package com.ark.weather.platform.domain.domain;

import com.ark.weather.platform.domain.enums.DateTypeEnum;
import com.ark.weather.platform.domain.enums.SubjectTypeEnum;
import com.ark.weather.platform.enums.SkyconEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 天气预报持久对象数据
 *
 */
@Data
public class WeatherFutureInfoPO {
    /**
     * ID
     */
    private Long id;
    /**
     * 天气的主体类型
     *
     * @see SubjectTypeEnum
     */
    private Integer subjectType;
    /**
     * 天气数据采集的日期粒度
     *
     * @see DateTypeEnum
     */
    private Integer dateType;

    /**
     * 城市区域编码
     */
    private String cityId;

    /**
     * 门店编码
     */
    private String stationCode;

    /**
     * 门店名称
     */
    private String stationName;
    /**
     * 纬度
     */
    private BigDecimal lat;
    /**
     * 经度
     */
    private BigDecimal lng;

    /**
     * 观察时间, 天气数据的实际观测时间
     */
    private Date obsTime;

    /**
     * 预报的天气日期
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
     * 风速，公里/小时（km/hr）
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
     * 降雨量/降雪量，毫米/小时(mm/hr)
     * 根据 skycon 字段判断是降雨or降雪
     */
    private BigDecimal precipitation;
    /**
     * 主要天气现象代码
     *
     * @see SkyconEnum
     */
    private String skycon;
    /**
     * 主要天气现象名称
     *
     * @see SkyconEnum
     */
    private String skyconName;
    /**
     * 白天天气现象代码
     *
     * @see SkyconEnum
     */
    private String skyconDay;
    /**
     * 白天天气现象名称
     *
     * @see SkyconEnum
     */
    private String skyconDayName;
    /**
     * 夜间天气现象代码
     *
     * @see SkyconEnum
     */
    private String skyconNight;
    /**
     * 夜间天气现象名称
     *
     * @see SkyconEnum
     */
    private String skyconNightName;
    /**
     * 自然语言描述。描述中如有时间性描述，基准为obs_time
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateUser;

}
