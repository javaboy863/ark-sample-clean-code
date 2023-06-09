package com.ark.weather.platform.infrastructure.repository.db;

import com.ark.weather.platform.domain.domain.WeatherFutureInfoPO;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

/**
 * 预发天气的数据库操作mapper
 *
 */
public interface WeatherFutureInfoMapper {


    /**
     * 通过主键获取天气预报信息
     *
     * @param id
     * @return
     */
    WeatherFutureInfoPO getById(@Param("id") Long id);

    /**
     * 通过唯一主键查询唯一天气预报数据
     *
     * @param weatherFutureInfoPO
     * @return
     */
    WeatherFutureInfoPO getBySubjectTypeAndDateTypeAndForecastTime(WeatherFutureInfoPO weatherFutureInfoPO);

    /**
     * 查询预报天气数据
     *
     * @param subjectType
     * @param dateType
     * @param startForecastTime
     * @param endForecastTime
     * @param cityId
     * @param stationCode
     * @return
     */
    List<WeatherFutureInfoPO> findBySubjectTypeAndDateTypeAndBetweenForecastTime(@Param("subjectType") Integer subjectType,
                                                                                 @Param("dateType") Integer dateType,
                                                                                 @Param("cityId") String cityId,
                                                                                 @Param("stationCode") String stationCode,
                                                                                 @Param("startForecastTime") Date startForecastTime,
                                                                                 @Param("endForecastTime") Date endForecastTime);

    /**
     * 插入天气预报数据信息
     *
     * @param weatherFutureInfoPO
     * @return
     */
    int insertSelective(WeatherFutureInfoPO weatherFutureInfoPO) throws SQLIntegrityConstraintViolationException;

    /**
     * 更新天气预报数据
     *
     * @param weatherFutureInfoPO
     * @return
     */
    int updateSelective(WeatherFutureInfoPO weatherFutureInfoPO);
}
