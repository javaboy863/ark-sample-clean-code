package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 彩云天气请求返回的逐天数据
 *
 */
@Data
public class CaiYunWeatherDailyDO {
    /**
     * 天数据返回状态，OK成功
     */
    private String status;

    /**
     * 空气质量数据，AQI和PM2.5数据的最大值，平均值，最小值
     */
    private AirQuality air_quality;

    /**
     * 能见度，最大值，平均值，最小值
     */
    private List<DailyObj<Double>> visibility;

    /**
     * 风力与风向，最大值，平均值，最小值
     */
    private List<DailyObj<Wind>> wind;

    /**
     * 温度，最大值，平均值，最小值
     */
    private List<DailyObj<Float>> temperature;
    /**
     * 相对湿度，最大值，平均值，最小值
     */
    private List<DailyObj<Float>> humidity;
    /**
     * 降雨量，最大值，平均值，最小值
     */
    private List<DailyObj<Float>> precipitation;

    /**
     * 全天天气状况
     */
    private List<Skycon> skycon;
    /**
     * 白天主要天气现象
     */
    private List<Skycon> skycon_08h_20h;
    /**
     * 夜晚主要天气现象
     */
    private List<Skycon> skycon_20h_32h;

    /**
     * 未来天气的天维度组成对象
     *
     * @param <T>
     */
    @Data
    public static class DailyObj<T> {
        private Date date;
        private T max;
        private T min;
        private T avg;
    }

    @Data
    public static class Wind {
        /**
         * 风速，公里/小时（km/hr）
         */
        private Float speed;
        /**
         * 风向，从北顺时针(0~360°)
         */
        private Float direction;
    }


    @Data
    public static class AirQuality {
        /**
         * AQI，最大值，平均值，最小值
         */
        private List<DailyObj<AqiValue>> aqi;
        /**
         * PM25，最大值，平均值，最小值
         */
        private List<DailyObj<Integer>> pm25;
    }


    @Data
    public static class AqiValue {
        private Integer chn;
        private Integer usa;
    }


    @Data
    public static class Skycon {
        private Date date;
        private String value;
    }
}
