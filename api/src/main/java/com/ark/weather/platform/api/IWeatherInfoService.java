package com.ark.weather.platform.api;

import com.ark.weather.platform.dto.cmd.QueryWeatherInfoCmd;
import com.ark.weather.platform.dto.domainmodel.response.WeatherInfoDTO;
import com.ark.layer.dto.Result;

import java.util.List;

/**
 * 天气服务
 *
 */
public interface IWeatherInfoService {
    /**
     * 查询天气信息
     *
     * @param queryWeatherInfoCmd
     * @return
     */
    Result<List<WeatherInfoDTO>> queryWeatherInfo(QueryWeatherInfoCmd queryWeatherInfoCmd);
}
