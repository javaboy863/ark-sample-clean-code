package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

/**
 * 彩云天气请求返回的预警数据
 *
 */
@Data
public class CaiYunWeatherRealTimeDO {
    /**
     * 实况数据返回状态
     */
    private String status;

    /**
     * 空气质量
     */
    private AirQuality air_quality;
    /**
     * 能见度
     */
    private Float visibility;
    /**
     * 温度
     */
    private Float temperature;
    /**
     * 相对湿度(%),地表2米湿度，小数表示，最大1
     */
    private Float humidity;

    /**
     * 风向，风速
     */
    private CaiYunWeatherWindDataDO wind;
    /**
     * 降雨
     */
    private CaiYunWeatherPrecipitationDataDO precipitation;

    /**
     * 主要天气现象
     */
    private String skycon;

    /**
     * 风速风向信息
     */
    @Data
    public static class CaiYunWeatherWindDataDO {

        /**
         * 风速
         */
        private Float speed;
        /**
         * 风向, 0 ~ 360
         */
        private Float direction;
    }


    /**
     * 彩云降雨信息
     */
    @Data
    public static class CaiYunWeatherPrecipitationDataDO {
        /**
         * 本地降雨
         */
        private PrecipitationData local;
    }

    /**
     * 降雨数据
     */
    @Data
    public static class PrecipitationData {
        /**
         * 状态
         */
        private String status;
        /**
         * 降水量
         */
        private Float intensity;
    }

    /**
     * 空气质量数据
     */
    @Data
    public static class AirQuality {
        private AqiValue aqi;
        /**
         * pm25，质量浓度值
         */
        private Integer pm25;
    }


    @Data
    public static class AqiValue {
        /**
         * AQI（中国标准）
         */
        private Integer chn;
        /**
         * AQI（美国标准）
         */
        private Integer usa;
    }
}
