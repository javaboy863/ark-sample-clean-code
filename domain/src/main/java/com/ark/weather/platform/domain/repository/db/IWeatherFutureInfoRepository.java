package com.ark.weather.platform.domain.repository.db;

import com.ark.weather.platform.domain.domain.WeatherFutureInfoPO;

import java.util.Date;
import java.util.List;

/**
 */
public interface IWeatherFutureInfoRepository {
    /**
     * 保存门店天气实况信息
     *
     * @param id
     * @return 插入数量
     */
    WeatherFutureInfoPO getById(Long id);


    /**
     * 批量更新（不存在做插入操作）
     *
     * @param weatherFutureInfoList
     */
    void updateOrInsert(List<WeatherFutureInfoPO> weatherFutureInfoList);

    /**
     * 根据天气主题类型、天气采集的日期粒度、城市、门店和预报日期查询天气信息
     *
     * @param subjectType
     * @param dateType
     * @param cityId
     * @param stationCode
     * @param startForecastTime
     * @param endForecastTime
     * @return
     */
    List<WeatherFutureInfoPO> findWeatherFutureInfosByParams(Integer subjectType, Integer dateType, String cityId, String stationCode, Date startForecastTime, Date endForecastTime);
}
