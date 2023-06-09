package com.ark.weather.platform.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 位置模型
 *
 */
@Data
public class PositionInfoDTO implements Serializable {
    /**
     * 坐标对应到了城市上
     */
    private String cityId;
    /**
     * 开始时间
     */
    private Integer start;
    /**
     * 结束时间
     */
    private Integer end;
    /**
     * 纬度
     */
    private Double lat;
    /**
     * 经度
     */
    private Double lng;
}
