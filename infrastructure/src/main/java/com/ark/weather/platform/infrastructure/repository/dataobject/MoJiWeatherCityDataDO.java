package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

/**
 * 墨迹天气请求返回的预警数据
 *
 */
@Data
public class MoJiWeatherCityDataDO {
    /**
     * 州名称
     */
    private String region_name;
    /**
     * 国家编码
     */
    private String country_code;
    /**
     * 国家名称
     */
    private String country_name;
    /**
     * 城市名称
     */
    private String parent_names;
    /**
     * 地区名称
     */
    private String name;
    /**
     * 经度
     */
    private Double latitude;
    /**
     * 纬度
     */
    private Double longtitude;
    /**
     * 时区
     */
    private Integer time_zone;
    /**
     * 时区地区
     */
    private String time_zone_name;

}
