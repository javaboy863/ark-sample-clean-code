package com.ark.weather.platform.infrastructure.repository;

import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.domain.domain.WeatherAlertInfoPO;
import com.ark.weather.platform.domain.repository.db.IWeatherAlertInfoRepository;
import com.ark.weather.platform.infrastructure.repository.db.WeatherAlertInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 */
@Slf4j
@Service
public class WeatherAlertInfoRepository implements IWeatherAlertInfoRepository {
    @Resource
    private WeatherAlertInfoMapper weatherAlertInfoMapper;

    /**
     * 保存门店天气预警信息信息
     *
     * @param weatherAlertInfoPO
     * @return 插入数量
     */
    @Override
    public int save(WeatherAlertInfoPO weatherAlertInfoPO) {
        try {
            return weatherAlertInfoMapper.insert(weatherAlertInfoPO);
        } catch (Exception ex) {
            log.error("保存天气预警数据异常：{} , weatherAlertInfoPO:{}", ex.getMessage(), JSON.toJSON(weatherAlertInfoPO));
        }
        return 0;
    }

    @Override
    public int count() {
        return weatherAlertInfoMapper.count();
    }
}
