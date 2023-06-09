package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

import java.util.List;

/**
 * 彩云天气请求返回的预警数据
 *
 */
@Data
public class CaiYunWeatherAlertDO {
    /**
     * 预警数据返回状态
     */
    private String status;
    /**
     * 预警信息
     */
    private List<CaiYunWeatherAlertDataDO> content;


    /**
     * 墨迹天气请求返回的预警数据
     *
     */
    @Data
    public static class CaiYunWeatherAlertDataDO {
        /**
         * 发布时间,时间戳，单位秒
         */
        private Long pubtimestamp;
        /**
         * 预警信息的状态
         */
        private String status;
        /**
         * 预警代码
         * <p>
         * 拆分出预警等级和类型
         */
        private String code;
        /**
         * 标题
         */
        private String title;
        /**
         * 描述
         * 预警内容
         */
        private String description;

    }
}
