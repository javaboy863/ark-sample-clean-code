package com.ark.weather.platform.infrastructure.repository.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 墨迹天气请求返回的预警数据
 *
 */
@Data
public class MoJiWeatherAlertDataDO {
    /**
     * 预警等级
     */
    private String alert_level;
    /**
     * 预警类型
     */
    private String alert_name;
    /**
     * 预警内容
     */
    private String content;
    /**
     * 预警处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date deal_time;
    /**
     * 预警类型等级id 1 ~ 155
     */
    private Integer info_id;
    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date pub_time;
    /**
     * 预警标题
     */
    private String title;
}
