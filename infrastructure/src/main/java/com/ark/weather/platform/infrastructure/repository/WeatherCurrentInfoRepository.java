package com.ark.weather.platform.infrastructure.repository;

import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.domain.domain.WeatherCurrentInfoPO;
import com.ark.weather.platform.domain.repository.db.IWeatherCurrentInfoRepository;
import com.ark.weather.platform.infrastructure.repository.db.WeatherCurrentInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 */
@Slf4j
@Service
public class WeatherCurrentInfoRepository implements IWeatherCurrentInfoRepository {
    @Resource
    private WeatherCurrentInfoMapper weatherCurrentInfoMapper;

    /**
     * 保存门店天气实况信息
     *
     * @param weatherCurrentInfoPO
     * @return 插入数量
     */
    @Override
    public int save(WeatherCurrentInfoPO weatherCurrentInfoPO) {
        try {
            return weatherCurrentInfoMapper.insert(weatherCurrentInfoPO);
        } catch (Exception ex) {
            log.error("保存天气实况数据异常：{} , weatherCurrentInfoPO:{}", ex.getMessage(), JSON.toJSON(weatherCurrentInfoPO));
        }
        return 0;
    }
}
