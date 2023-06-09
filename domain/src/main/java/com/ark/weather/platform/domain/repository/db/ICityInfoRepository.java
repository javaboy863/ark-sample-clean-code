package com.ark.weather.platform.domain.repository.db;

import com.ark.weather.platform.domain.domain.CityInfoPO;

import java.util.List;

/**
 * 城市持久数据的repository规范
 *
 */
public interface ICityInfoRepository {

    /**
     * 通过城市编码获取城市信息
     *
     * @param cityId 城市编码
     * @return
     */
    CityInfoPO getByCityId(String cityId);

    /**
     * 根据城市编码信息查询城市列表
     *
     * @param cityIds
     * @return
     */
    List<CityInfoPO> findByCityIds(List<String> cityIds);

    /**
     * 根据城市id或城市名称查找城市信息
     *
     * @param cityId
     * @param cityName
     * @return
     */
    List<CityInfoPO> findByCityIdOrCityName(String cityId, String cityName);
}
