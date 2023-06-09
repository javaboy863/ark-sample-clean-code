package com.ark.weather.platform.infrastructure.repository.db;

import com.ark.weather.platform.domain.domain.CityInfoPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 城市数据库操作mapper
 *
 */
public interface CityInfoMapper {
    /**
     * 通过城市编码获取城市信息
     *
     * @param cityId 城市编码
     * @return
     */
    CityInfoPO getByCityId(@Param("cityId") String cityId);

    /**
     * 查询城市列表
     *
     * @param cityIds
     * @return
     */
    List<CityInfoPO> findByCityIds(@Param("cityIds") List<String> cityIds);

    /**
     * 根据城市id或城市名称查找城市信息
     *
     * @param cityId
     * @param cityName
     * @return
     */
    List<CityInfoPO> findByCityIdOrCityName(@Param("cityId") String cityId, @Param("cityName") String cityName);
}
