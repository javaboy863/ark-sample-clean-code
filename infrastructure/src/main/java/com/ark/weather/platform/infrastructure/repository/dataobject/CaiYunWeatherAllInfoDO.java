package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

/**
 * 彩云天气请求返回的天气所有信息
 *
 */
@Data
public class CaiYunWeatherAllInfoDO {
    /**
     * 返回码
     * ok:请求成功且返回天⽓数据
     * 其他: 异常
     */
    private String status;
    /**
     * 服务器时间,UTC时间到秒单位
     */
    private Long server_time;
    /**
     * 数据信息
     */
    private CaiYunWeatherAllInfoDataDO result;


}
