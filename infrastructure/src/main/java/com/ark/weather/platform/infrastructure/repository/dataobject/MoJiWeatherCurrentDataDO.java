package com.ark.weather.platform.infrastructure.repository.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 墨迹天气请求返回的当前天气数据
 *
 */
@Data
public class MoJiWeatherCurrentDataDO {
    /**
     * 舒适度
     */
    private Integer comfort;
    /**
     * 露点温度, 摄⽒度, 暂不⽀持精度到⼩数点后
     */
    private Integer dewpoint;
    /**
     * 相对湿度，百分比，0~100
     */
    private Integer humidity;
    /**
     * 观察时间
     * 2021-08-25 14:42:18
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date obs_time;
    /**
     * 过去⼀⼩时降⽔量,mm/h
     */
    private Float precip_1h;
    /**
     * 体感温度, 摄⽒度, 暂不⽀持精度到⼩数点后
     */
    private Integer real_feel;
    /**
     * 温度, 摄⽒度
     */
    private Float temp;
    /**
     * 天⽓现象
     */
    private String weather;
    /**
     * 天⽓现象id
     */
    private Integer weather_id;
    /**
     * ⻛向⻆度, 0~360
     */
    private Integer wind_degrees;
    /**
     * ⻛向
     */
    private String wind_dir;
    /**
     * ⻛向id
     */
    private Integer wind_dir_id;
    /**
     * ⻛⼒等级
     */
    private Integer wind_level;
    /**
     * ⻛速
     */
    private Float wspd;
}
