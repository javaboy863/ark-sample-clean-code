package com.ark.weather.platform.dto.cmd;

import com.ark.layer.dto.Command;
import lombok.Data;

/**
 * 获取天气信息的请求命令
 *
 */
@Data
public class QueryWeatherInfoCmd extends Command {
    /**
     * 来源AppCode
     */
    private String appCode;
    /**
     * 城市编码，例如 北京市 -> 110000
     */
    private String cityId;
    /**
     * 城市标准名称
     */
    private String cityName;
    /**
     * 纬度
     */
    private Double lat;

    /**
     * 经度
     */
    private Double lng;

    /**
     * 当前天气的时间，按照场景时间粒度的开始时间
     */
    private Integer start;

    /**
     * 结束天气的时间，按照场景时间粒度的结束时间
     */
    private Integer end;
}
