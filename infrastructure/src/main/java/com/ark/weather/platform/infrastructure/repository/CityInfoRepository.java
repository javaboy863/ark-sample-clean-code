package com.ark.weather.platform.infrastructure.repository;

import com.ark.weather.platform.domain.domain.CityInfoPO;
import com.ark.weather.platform.domain.repository.db.ICityInfoRepository;
import com.ark.weather.platform.infrastructure.repository.db.CityInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 城市信息数据层处理类
 *
 */
@Service
@Slf4j
public class CityInfoRepository implements ICityInfoRepository {
    @Resource
    private CityInfoMapper cityInfoMapper;

    /**
     * 通过城市编码获取城市信息
     *
     * @param cityId
     * @return
     */
    @Override
    public CityInfoPO getByCityId(String cityId) {
        return cityInfoMapper.getByCityId(cityId);
    }

    @Override
    public List<CityInfoPO> findByCityIds(List<String> cityIds) {
        return cityInfoMapper.findByCityIds(cityIds);
    }

    @Override
    public List<CityInfoPO> findByCityIdOrCityName(String cityId, String cityName) {
        return cityInfoMapper.findByCityIdOrCityName(cityId, cityName);
    }
}
