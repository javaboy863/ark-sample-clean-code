package com.ark.weather.platform.domain.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 天气预警持久对象数据
 *
 */
@Data
public class WeatherAlertInfoPO {
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
     * 发布时间
     */
    private Date pubTime;
    /**
     * 预警标题
     */
    private String title;
    /**
     * 预警内容
     */
    private String content;
    /**
     * 预警等级（蓝色、黄色等）
     */
    private String alertLevel;
    /**
     * 预警类型
     */
    private String alertName;
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
