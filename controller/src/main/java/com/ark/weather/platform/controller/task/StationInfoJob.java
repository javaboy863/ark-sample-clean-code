package com.ark.weather.platform.controller.task;

import com.ark.weather.platform.app.service.StationInfoJobService;
import com.google.common.base.Stopwatch;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 微仓定时任务,每天00:05分执行一次
 *
 */
@Slf4j
public class StationInfoJob implements SimpleJob {
    @Resource
    private StationInfoJobService stationInfoJobService;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("[天气平台-获取门店信息任务-开始]");
        Stopwatch stopwatch = Stopwatch.createStarted();
        stationInfoJobService.cacheAllOpenStationJob();
        log.info("[天气平台-获取门店信息任务-结束] cost {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
