package com.ark.weather.platform.domain.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 城市持久对象数据
 *
 */
@Data
public class CityInfoPO {
    /**
     * ID
     */
    private Integer id;
    /**
     * 城市区域编码
     */
    private String cityId;
    /**
     * 纬度
     */
    private BigDecimal lat;
    /**
     * 经度
     */
    private BigDecimal lng;
    /**
     * 省份名称
     */
    private String province;
    /**
     * 城市名称
     */
    private String city;
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
