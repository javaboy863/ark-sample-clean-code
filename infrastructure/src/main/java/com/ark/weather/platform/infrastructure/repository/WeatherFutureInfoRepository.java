package com.ark.weather.platform.infrastructure.repository;

import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.domain.domain.WeatherFutureInfoPO;
import com.ark.weather.platform.infrastructure.repository.db.WeatherFutureInfoMapper;
import com.google.common.collect.Lists;
import com.ark.weather.platform.domain.repository.db.IWeatherFutureInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

/**
 */
@Slf4j
@Service
public class WeatherFutureInfoRepository implements IWeatherFutureInfoRepository {
    @Resource
    private WeatherFutureInfoMapper weatherFutureInfoMapper;

    /**
     * 根据主键获取天气预报数据
     *
     * @param id
     * @return
     */
    @Override
    public WeatherFutureInfoPO getById(Long id) {
        return weatherFutureInfoMapper.getById(id);
    }

    /**
     * 更新系统天气预报数据（如果不存在则插入操作）
     *
     * @param weatherFutureInfoList
     */
    @Override
    public void updateOrInsert(List<WeatherFutureInfoPO> weatherFutureInfoList) {
        WeatherFutureInfoPO weatherFutureInfoPO = null;
        try {
            WeatherFutureInfoPO weatherFutureInfo = null;
            for (int i = 0; i < weatherFutureInfoList.size(); i++) {
                weatherFutureInfoPO = weatherFutureInfoList.get(i);
                weatherFutureInfo = weatherFutureInfoMapper.getBySubjectTypeAndDateTypeAndForecastTime(weatherFutureInfoPO);
                if (weatherFutureInfo == null) {
                    // insert
                    weatherFutureInfoMapper.insertSelective(weatherFutureInfoPO);
                } else {
                    // update
                    weatherFutureInfoPO.setId(weatherFutureInfo.getId());
                    weatherFutureInfoMapper.updateSelective(weatherFutureInfoPO);
                }
            }
        } catch (SQLException ex) {
            if (ex instanceof SQLIntegrityConstraintViolationException) {
                log.warn("保存天气主键冲突异常：{} , weatherFutureInfoPO:{}", ex.getMessage(), JSON.toJSON(weatherFutureInfoPO));
            } else {
                log.error("保存更新天气预报数据异常：{} , weatherFutureInfoPO:{}", ex.getMessage(), JSON.toJSON(weatherFutureInfoPO));
            }
        }  catch (DuplicateKeyException ex) {
            log.warn("保存天气主键冲突异常DuplicateKeyException：{} , weatherFutureInfoPO:{}", ex.getMessage(), JSON.toJSON(weatherFutureInfoPO));
        } catch (Exception ex) {
            log.error("保存更新天气预报数据异常：{} , weatherFutureInfoPO:{}", ex.getMessage(), JSON.toJSON(weatherFutureInfoPO));
        }
    }


    @Override
    public List<WeatherFutureInfoPO> findWeatherFutureInfosByParams(Integer subjectType, Integer dateType, String cityId, String stationCode,
                                                                    Date startForecastTime, Date endForecastTime) {
        if (StringUtils.isBlank(cityId) && StringUtils.isBlank(stationCode)) {
            return Lists.newArrayListWithCapacity(0);
        }
        if (subjectType == null || dateType == null || startForecastTime == null || endForecastTime == null || startForecastTime.after(endForecastTime)) {
            return Lists.newArrayListWithCapacity(0);
        }
        return weatherFutureInfoMapper.findBySubjectTypeAndDateTypeAndBetweenForecastTime(subjectType, dateType, cityId, stationCode, startForecastTime, endForecastTime);
    }
}
