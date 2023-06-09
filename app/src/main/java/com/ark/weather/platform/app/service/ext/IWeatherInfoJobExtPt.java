package com.ark.weather.platform.app.service.ext;

import com.ark.weather.platform.infrastructure.repository.dataobject.PositionInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherAllInfoDO;
import com.ark.layer.extension.IExtensionPoint;

/**
 * 天气任务扩展点
 *
 */
public interface IWeatherInfoJobExtPt extends IExtensionPoint {


    /**
     * 调用远程天气服务商
     *
     * @param positionInfoDO 绑定参数
     * @return
     */
    WeatherAllInfoDO callRemote(PositionInfoDO positionInfoDO);

    /**
     * 存储天气信息
     *
     * @param weatherAllInfoDO 天气数据
     * @param positionInfoDO   天气对应的位置信息
     */
    void store(WeatherAllInfoDO weatherAllInfoDO, PositionInfoDO positionInfoDO);
}
