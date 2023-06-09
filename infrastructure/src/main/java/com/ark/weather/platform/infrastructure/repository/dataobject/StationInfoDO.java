package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 门店信息
 *
 */
@Data
public class StationInfoDO {
    /**
     * 城市编码
     */
    private String cityId;

    /**
     * 所在城市名称
     */
    private String city;
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
     * 省ID
     */
    private String provinceId;
}
