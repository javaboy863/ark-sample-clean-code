package com.ark.weather.platform.domain.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 天气实况持久对象数据
 *
 */
@Data
public class WeatherCurrentInfoPO {
    /**
     * ID
     */
    private Long id;
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
     * 天气服务商类型
     * 墨迹天气：1
     * 彩云天气：2
     */
    private Integer supplierType;
    /**
     * 观察时间, 天气数据的实际观测时间
     */
    private Date obsTime;
    /**
     * 温度
     */
    private BigDecimal temperature;
    /**
     * 相对湿度, 百分比，1 ~ 100
     */
    private Integer humidity;
    /**
     * 风向角度, 0 ~ 360
     */
    private BigDecimal windDegrees;
    /**
     * 风向, 中文名称
     */
    private String windDir;
    /**
     * 风力等级, 0 ~ 12
     */
    private Integer windLevel;
    /**
     * 降水量, 过去一小时降水量
     */
    private BigDecimal precipitation;
    /**
     * 天气现象
     */
    private String weather;
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
