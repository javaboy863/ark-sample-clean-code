package com.ark.weather.platform.app.service;

import com.ark.weather.platform.api.IWeatherInfoService;
import com.ark.weather.platform.dto.cmd.QueryWeatherInfoCmd;
import com.ark.weather.platform.dto.domainmodel.response.WeatherInfoDTO;
import com.ark.layer.dto.Result;
import com.ark.weather.platform.app.command.query.QueryWeatherInfoExe;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 天气服务对外查询实现类
 *
 */
@Service(value = "weatherInfoImplService")
public class WeatherInfoServiceImpl implements IWeatherInfoService {
    @Resource
    private QueryWeatherInfoExe queryWeatherInfoExe;


    @Override
    public Result<List<WeatherInfoDTO>> queryWeatherInfo(QueryWeatherInfoCmd queryWeatherInfoCmd) {
        return queryWeatherInfoExe.query(queryWeatherInfoCmd);
    }
}
