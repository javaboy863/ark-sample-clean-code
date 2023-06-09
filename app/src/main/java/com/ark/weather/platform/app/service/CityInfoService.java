package com.ark.weather.platform.app.service;

import com.ark.weather.platform.app.common.util.CommonUtils;
import com.ark.weather.platform.domain.domain.CityInfoPO;
import com.ark.weather.platform.infrastructure.repository.dataobject.CityInfoDO;
import com.google.common.collect.Lists;
import com.ark.weather.platform.domain.repository.db.ICityInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 城市服务类
 *
 */
@Service
@Slf4j
public class CityInfoService {
    @Autowired
    private ICityInfoRepository cityInfoRepository;

    /**
     * 通过城市编码获取城市信息
     *
     * @param cityIds 城市编码列表
     * @return
     */
    public List<CityInfoDO> findByCityIds(List<String> cityIds) {
        List<CityInfoPO> cityInfoPOList = cityInfoRepository.findByCityIds(cityIds);
        if (CollectionUtils.isEmpty(cityInfoPOList)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return cityInfoPOList.stream().map(CommonUtils::getCityInfoDO).collect(Collectors.toList());
    }

    /**
     * 通过城市编码获取城市信息
     *
     * @param cityId 城市编码
     * @return
     */
    public CityInfoDO findByCityId(String cityId) {
        CityInfoPO cityInfoPO = cityInfoRepository.getByCityId(cityId);
        if (cityInfoPO == null) {
            return null;
        }
        return CommonUtils.getCityInfoDO(cityInfoPO);
    }


    /**
     * 通过城市编码或城市名称获取城市信息
     * 只返回一个值，优先匹配cityId
     *
     * @param cityId   城市编码
     * @param cityName 城市名称
     * @return
     */
    public CityInfoDO findByCityIdOrCityName(String cityId, String cityName) {
        List<CityInfoPO> cityInfoPOList = cityInfoRepository.findByCityIdOrCityName(cityId, cityName);
        if (CollectionUtils.isEmpty(cityInfoPOList)) {
            return null;
        }
        CityInfoPO cityInfoPO = null;
        if (cityInfoPOList.size() == 1) {
            cityInfoPO = cityInfoPOList.get(0);
        }

        if (cityInfoPO == null && StringUtils.isNotBlank(cityId)) {
            for (CityInfoPO info : cityInfoPOList) {
                if (StringUtils.equals(info.getCityId(), cityId)) {
                    cityInfoPO = info;
                    break;
                }
            }
        }

        if (cityInfoPO == null) {
            cityInfoPO = cityInfoPOList.get(0);
        }

        return CommonUtils.getCityInfoDO(cityInfoPO);
    }
}
