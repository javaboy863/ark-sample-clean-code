package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 城市信息
 *
 */
@Data
public class CityInfoDO {
    /**
     * 城市编码
     */
    private String cityId;

    /**
     * 所在城市名称
     */
    private String city;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 经度
     */
    private BigDecimal lng;
}
