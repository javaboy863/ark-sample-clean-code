package com.ark.weather.platform.app.service;

import com.ark.weather.platform.app.common.util.CommonUtils;
import com.ark.weather.platform.domain.domain.WeatherFutureInfoPO;
import com.ark.weather.platform.domain.repository.db.IWeatherFutureInfoRepository;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherInfoDO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 天气预报服务
 *
 */
@Service
@Slf4j
public class WeatherFutureInfoService {
    @Resource
    private IWeatherFutureInfoRepository weatherFutureInfoRepository;


    public List<WeatherInfoDO> findWeatherFutureInfos(Integer subjectType, Integer dateType, String cityId, String stationCode, Date startForecastTime, Date endForecastTime) {
        List<WeatherFutureInfoPO> weatherFutureInfoPOList = weatherFutureInfoRepository.findWeatherFutureInfosByParams(subjectType, dateType, cityId, stationCode, startForecastTime, endForecastTime);
        if (CollectionUtils.isEmpty(weatherFutureInfoPOList)) {
            return Lists.newArrayListWithCapacity(0);
        }

        return weatherFutureInfoPOList.stream().map(CommonUtils::getWeatherInfoDO).collect(Collectors.toList());
    }
}
