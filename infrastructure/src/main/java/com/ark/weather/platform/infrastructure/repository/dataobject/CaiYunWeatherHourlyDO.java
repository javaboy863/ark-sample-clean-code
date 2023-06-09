package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 彩云天气请求返回的逐小时数据
 *
 */
@Data
public class CaiYunWeatherHourlyDO {
    /**
     * 小时数据返回状态，OK成功
     */
    private String status;

    /**
     * 预报的自然语言描述
     */
    private String description;

    /**
     * 空气质量
     */
    private AirQuality air_quality;

    /**
     * 能见度
     */
    private List<HourlyObj<Float>> visibility;
    
    /**
     * 风向，风速
     */
    private List<Wind> wind;
    /**
     * 温度
     */
    private List<HourlyObj<Float>> temperature;
    /**
     * 湿度
     */
    private List<HourlyObj<Float>> humidity;
    /**
     * 逐小时的降雨，毫米/小时(mm/hr)
     */
    private List<HourlyObj<Float>> precipitation;
    /**
     * 天气现象编码
     */
    private List<HourlyObj<String>> skycon;

    /**
     * 未来天气的小时维度组成对象
     *
     * @param <T>
     */
    @Data
    public static class HourlyObj<T> {
        private Date datetime;
        private T value;
    }

    /**
     * 空气质量数据
     */
    @Data
    public static class AirQuality {
        private List<HourlyObj<AqiValue>> aqi;

        private List<HourlyObj<Integer>> pm25;
    }


    @Data
    public static class AqiValue {
        private Integer chn;
        private Integer usa;
    }

    @Data
    public static class Wind {
        private Date datetime;
        /**
         * 风速，公里/小时（km/hr）
         */
        private Float speed;
        /**
         * 风向，从北顺时针(0~360°)
         */
        private Float direction;
    }
}
