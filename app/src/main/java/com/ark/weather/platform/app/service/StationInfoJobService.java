package com.ark.weather.platform.app.service;

import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.infrastructure.repository.dataobject.StationInfoDO;
import com.ark.basic.station.model.enums.StationEnum;
import com.ark.basic.station.model.response.MidBaseStationDTO;
import com.ark.layer.util.DeepConvertUtil;
import com.ark.monitor.api.BusinessMonitor;
import com.ark.weather.platform.app.command.query.QueryStationInfoExe;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 门店任务服务类
 *
 */
@Slf4j
@Service
public class StationInfoJobService {
    @Resource
    private QueryStationInfoExe queryStationInfoExe;
    @Resource
    private RedisStationCache redisStationCache;

    /**
     * 缓存所有门店任务
     */
    public void cacheAllOpenStationJob() {
        // 执行获取门店信息任务
        List<MidBaseStationDTO> allOpenStation = queryStationInfoExe.getAllOpenStation();
        //checkStation
        if (checkStation(allOpenStation)) {
            return;
        }
        // 转换成缓存对象
        Map<String, StationInfoDO> stationInfoMap = filterStation(allOpenStation);
        log.info("定时获取门店列表：{}", JSON.toJSONString(stationInfoMap));
        // 放入缓存
        redisStationCache.putAll(stationInfoMap);
    }

    private Map<String, StationInfoDO> filterStation(List<MidBaseStationDTO> allOpenStation) {
        return allOpenStation.stream()
                .filter(stationDto -> Objects.nonNull(stationDto.getStationType()))
                .filter(stationDto -> {
                    return StationEnum.StationTypeEnum.JSD.getCode() == stationDto.getStationType()
                            || StationEnum.StationTypeEnum.DA_XX.getCode() == stationDto.getStationType();
                })
                .map(this::getStationInfoDO)
                .collect(Collectors.toMap(StationInfoDO::getStationCode, dto -> dto, (a, b) -> a));
    }

    /**
     * checkStation 门店
     */
    private boolean checkStation(List<MidBaseStationDTO> allOpenStation) {
        if (CollectionUtils.isEmpty(allOpenStation)) {
            log.error("缓存所有门店任务失败");
            BusinessMonitor.recordError("STATION_CACHE_ERROR");
            return true;
        }
        return false;
    }

    /**
     * 门店返回DTO对象转换为缓存门店DO
     *
     * @param midBaseStationDTO 门店返回DTO
     * @return
     */
    private StationInfoDO getStationInfoDO(MidBaseStationDTO midBaseStationDTO) {
        return DeepConvertUtil.deepCopy(midBaseStationDTO, StationInfoDO.class);
    }
}
