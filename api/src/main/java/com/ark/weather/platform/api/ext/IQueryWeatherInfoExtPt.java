package com.ark.weather.platform.api.ext;

import com.ark.weather.platform.dto.PositionInfoDTO;
import com.ark.weather.platform.dto.cmd.QueryWeatherInfoCmd;
import com.ark.weather.platform.dto.domainmodel.response.WeatherInfoDTO;
import com.ark.layer.extension.IExtensionPoint;

import java.util.List;

/**
 * 查询天气扩展点
 *
 */
public interface IQueryWeatherInfoExtPt extends IExtensionPoint {

    /**
     * 验证参数方法
     *
     * @param cmd 参数
     * @return
     */
    void validateCmd(QueryWeatherInfoCmd cmd);

    /**
     * 将参数处理为位置对象
     *
     * @param cmd
     * @return
     */
    PositionInfoDTO handleCmd(QueryWeatherInfoCmd cmd);

    /**
     * 调用远程天气服务商
     *
     * @param positionInfoDTO 位置参数
     * @return
     */
    List<WeatherInfoDTO> callRemote(PositionInfoDTO positionInfoDTO);
}
