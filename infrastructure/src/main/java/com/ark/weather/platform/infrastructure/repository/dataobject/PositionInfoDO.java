package com.ark.weather.platform.infrastructure.repository.dataobject;

import com.ark.weather.platform.domain.enums.SubjectTypeEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 天气数据的位置模型
 *
 */
@Data
public class PositionInfoDO {
    /**
     * 位置对应类型
     * 1：城市  2：门店
     *
     * @see SubjectTypeEnum
     */
    private Integer subjectType;

    /**
     * 城市区域编码
     */
    private String cityId;
    /**
     * 城市名称
     */
    private String cityName;

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
}
