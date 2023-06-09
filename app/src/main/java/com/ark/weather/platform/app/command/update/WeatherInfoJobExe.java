package com.ark.weather.platform.app.command.update;

import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.app.common.WeatherExtPointConstant;
import com.ark.weather.platform.app.common.util.CommonUtils;
import com.ark.weather.platform.app.service.CityInfoService;
import com.ark.weather.platform.app.service.RedisStationCache;
import com.ark.weather.platform.app.service.ext.IWeatherInfoJobExtPt;
import com.ark.weather.platform.infrastructure.common.CommonConfig;
import com.ark.weather.platform.infrastructure.repository.dataobject.CityInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.PositionInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.StationInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherAllInfoDO;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.ark.layer.exception.BizException;
import com.ark.layer.extension.BizScenario;
import com.ark.layer.extension.ExtensionExecutor;
import com.ark.monitor.api.BusinessMonitor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 定时虚拟号绑定续期
 *
 */
@Component
@Slf4j
public class WeatherInfoJobExe {
    /**
     * 门店每个分区处理的总数
     */
    private static final int PARTITION_COUNT = 100;
    /**
     * 线程工厂
     */
    private static final ThreadFactory ACTION_THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("thread-getWeatherJob-runner-%d").build();
    /**
     * 定时任务中获取天气的线程池
     */
    protected static final ThreadPoolExecutor EXECUTOR_SERVICE = new ThreadPoolExecutor(
            4,
            8,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(100),
            ACTION_THREAD_FACTORY);
    @Resource
    private ExtensionExecutor extensionExecutor;
    @Resource
    private CommonConfig commonConfig;
    @Resource
    private CityInfoService cityInfoService;
    @Resource
    private RedisStationCache redisStationCache;

    /**
     * 获取城市天气的天气任务
     */
    public void getStationWeatherInfoTask() {
        // 获取门店位置数据
        List<PositionInfoDO> stationPositionList = filterStationPositionInfo();
        if (CollectionUtils.isEmpty(stationPositionList)) {
            log.warn("获取天气信息的门店位置信息为空");
            BusinessMonitor.recordError("STATION_TASK_ERROR");
            return;
        }
        log.info("待获取天气信息的门店位置信息数量：{}", stationPositionList.size());
        // 分区，多线程获取天气信息
        List<List<PositionInfoDO>> stationPositionPartition = ListUtils.partition(stationPositionList, PARTITION_COUNT);
        asyncExecuteWeatherInfoJob(stationPositionPartition);
    }

    /**
     * 获取城市天气的天气任务
     */
    public void getCityWeatherInfoTask() {
        // 获取城市位置数据
        List<PositionInfoDO> cityPositionList = filterCityPositionInfo();
        log.info("待获取天气信息的城市位置列表：{}", JSON.toJSONString(cityPositionList));
        if (CollectionUtils.isEmpty(cityPositionList)) {
            log.warn("获取天气信息的城市位置信息为空");
            BusinessMonitor.recordError("CITY_TASK_ERROR");
            return;
        }
        // 分区，多线程获取天气信息
        List<List<PositionInfoDO>> cityPositionPartition = ListUtils.partition(cityPositionList, PARTITION_COUNT);
        asyncExecuteWeatherInfoJob(cityPositionPartition);
    }

    /**
     * 获取门店位置信息
     *
     * @return
     */
    private List<PositionInfoDO> filterStationPositionInfo() {
        Map<String, StationInfoDO> allCache = redisStationCache.getAllCache();
        if (MapUtils.isEmpty(allCache)) {
            return Lists.newArrayList();
        }
        List<StationInfoDO> stationWeatherList = Lists.newArrayList();

        // 获取门店数据
        if (commonConfig.isAllStationSwitch()) {
            // 1.查看开关配置是否打开全量门店
            stationWeatherList.addAll(allCache.values());
        } else {
            // 按照省份过滤需要获取天气的门店
            stationWeatherList = allCache.values().stream().filter(this::provinceFilter)
                    .collect(Collectors.toList());
        }
        return stationWeatherList.stream().filter(stationInfoDO -> {
            boolean errorXY = stationInfoDO.getLat().equals(BigDecimal.ONE) || stationInfoDO.getLng().equals(BigDecimal.ONE);
            if (errorXY) {
                System.out.println("_______________" + JSON.toJSONString(stationInfoDO));
            }
            return !errorXY;
        }).map(CommonUtils::getPositionInfo).collect(Collectors.toList());
    }


    /**
     * 获取要请求天气的门店信息
     *
     * @return
     */
    private List<PositionInfoDO> filterCityPositionInfo() {
        Map<String, StationInfoDO> allCache = redisStationCache.getAllCache();
        if (MapUtils.isEmpty(allCache)) {
            return Lists.newArrayList();
        }
        Set<String> cityIdSet = allCache.values().stream().map(StationInfoDO::getCityId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(cityIdSet)) {
            return Lists.newArrayList();
        }
        // 获取所有城市信息
        List<CityInfoDO> cityInfoDOList = cityInfoService.findByCityIds(Lists.newArrayList(cityIdSet));

        if (CollectionUtils.isEmpty(cityInfoDOList)) {
            return Lists.newArrayList();
        }

        return cityInfoDOList.stream().map(CommonUtils::getPositionInfo).collect(Collectors.toList());
    }

    /**
     * 异步执行获取天气
     *
     * @param positionLists
     */
    private void asyncExecuteWeatherInfoJob(List<List<PositionInfoDO>> positionLists) {
        for (List<PositionInfoDO> positionInfoDOList : positionLists) {
            // 彩云天气
            EXECUTOR_SERVICE.submit(() -> caiYunWeatherInfoJob(positionInfoDOList));
        }
    }


    /**
     * 通过彩云天气获取门店天气信息
     *
     * @param positionInfoDOList
     */
    private void caiYunWeatherInfoJob(List<PositionInfoDO> positionInfoDOList) {
        // 构造彩云的天气任务类场景
        BizScenario bizScenario = BizScenario.valueOf(WeatherExtPointConstant.BIZ_ID_CAI_YUN, WeatherExtPointConstant.USE_CASE_WEATHER, WeatherExtPointConstant.SCENARIO_WEATHER_JOB);
        // 真实执行任务
        execute(bizScenario, positionInfoDOList);
    }

    /**
     * 执行获取天气数据
     *
     * @param bizScenario        场景
     * @param positionInfoDOList
     */
    private void execute(BizScenario bizScenario, List<PositionInfoDO> positionInfoDOList) {
        // 循环执行
        for (PositionInfoDO positionInfoDO : positionInfoDOList) {
            try {
                // 获取天气信息
                WeatherAllInfoDO weatherAllInfoDO = extensionExecutor.execute(IWeatherInfoJobExtPt.class, bizScenario, e -> e.callRemote(positionInfoDO));
                // 保存天气信息
                extensionExecutor.executeVoid(IWeatherInfoJobExtPt.class, bizScenario, e -> e.store(weatherAllInfoDO, positionInfoDO));
            } catch (BizException ex) {
                BusinessMonitor.recordOne("WEATHER_TASK_ERROR_" + positionInfoDO.getSubjectType());
                log.error("执行定时任务失败。positionInfoDO:{}", JSON.toJSONString(positionInfoDO));
            }
        }
    }

    /**
     * 按照省份配置过滤门店信息
     *
     * @param stationInfoDO 门店信息
     * @return
     */
    private boolean provinceFilter(StationInfoDO stationInfoDO) {
        for (String provinceId : commonConfig.getProvinceIdArr()) {
            if (provinceId.equals(stationInfoDO.getProvinceId())) {
                return true;
            }
        }
        return false;
    }
}
