package com.ark.weather.platform.controller.rpc;

import com.ark.layer.dto.Result;
import com.ark.weather.platform.api.IWeatherInfoService;
import com.ark.weather.platform.dto.cmd.QueryWeatherInfoCmd;
import com.ark.weather.platform.dto.domainmodel.response.WeatherInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 天气服务对外查询实现类
 *
 */
@Slf4j
@Service(value = "weatherInfoRpcServiceImpl")
public class WeatherInfoRpcServiceImpl implements IWeatherInfoService {

    @Resource(name = "weatherInfoImplService")
    IWeatherInfoService weatherInfoService;


    @Override
    public Result<List<WeatherInfoDTO>> queryWeatherInfo(QueryWeatherInfoCmd queryWeatherInfoCmd) {
        return weatherInfoService.queryWeatherInfo(queryWeatherInfoCmd);
    }

}
