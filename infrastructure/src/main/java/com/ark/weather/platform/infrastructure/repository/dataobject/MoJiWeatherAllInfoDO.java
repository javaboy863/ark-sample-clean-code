package com.ark.weather.platform.infrastructure.repository.dataobject;

import lombok.Data;

/**
 * 墨迹天气请求返回的天气所有信息
 *
 */
@Data
public class MoJiWeatherAllInfoDO {
    /**
     * 返回码
     * 0:请求成功且返回天⽓数据
     * 其他: 异常，msg错误详细内容
     */
    private Integer code;
    /**
     * 返回结果状态描述信息
     */
    private String msg;
    /**
     * 数据信息
     */
    private MoJiWeatherDataDO data;

}
