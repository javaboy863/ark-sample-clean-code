package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

import java.util.List;

/**
 * 彩云天气请求返回的逐分钟数据
 *
 */
@Data
public class CaiYunWeatherMinutelyDO {
    /**
     * 2h分钟数据返回状态，OK成功
     */
    private String status;

    /**
     * 预报的自然语言描述
     */
    private String description;
    /**
     * 本地未来2小时逐分钟短临降水量预报，毫米/小时(mm/hr)
     */
    List<Float> precipitation_2h;
}
