package com.ark.weather.platform.controller.task;

import com.ark.weather.platform.app.service.WeatherInfoJobService;
import com.ark.weather.platform.dto.job.WeatherJobExecuteType;
import com.google.common.base.Stopwatch;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 天气定时任务,10分钟执行一次
 *
 */
@Slf4j
public class WeatherInfoJob implements SimpleJob {
    private static final int ODD = 2;
    @Resource
    private WeatherInfoJobService weatherInfoJobService;

    @Override
    public void execute(ShardingContext shardingContext) {

        //判断任务执行是城市纬度还是门店纬度
        WeatherJobExecuteType weatherJobExecuteType = cityOrStation(shardingContext);

        log.info("[天气平台-获取天气信息任务-开始]");
        Stopwatch stopwatch = Stopwatch.createStarted();
        weatherInfoJobService.getWeatherInfo(weatherJobExecuteType.isCityTask(), weatherJobExecuteType.isStationTask());
        log.info("[天气平台-获取天气信息任务-结束] cost {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    /**
     * 判断任务执行是城市纬度还是门店纬度
     * @param shardingContext
     * @param cityTask
     * @param stationTask
     */
    private WeatherJobExecuteType cityOrStation(ShardingContext shardingContext){
        WeatherJobExecuteType weatherJobExecuteType = new WeatherJobExecuteType();
        // 总分片数
        int shardingTotalCount = shardingContext.getShardingTotalCount();
        if(shardingTotalCount < ODD ){
            weatherJobExecuteType.setCityTask(true);
            weatherJobExecuteType.setStationTask(true);
            return weatherJobExecuteType;
        }

        // 当前分片作业
        int shardingItem = shardingContext.getShardingItem();
        if (shardingItem % ODD == 1) {
            weatherJobExecuteType.setCityTask(true);
        } else {
            weatherJobExecuteType.setStationTask(true);
        }

      return   weatherJobExecuteType;
    }






}
