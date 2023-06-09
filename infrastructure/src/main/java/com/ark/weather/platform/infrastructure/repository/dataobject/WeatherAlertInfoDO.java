package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

import java.util.Date;

/**
 * 天气预警信息领域对象
 *
 */
@Data
public class WeatherAlertInfoDO {
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
}
