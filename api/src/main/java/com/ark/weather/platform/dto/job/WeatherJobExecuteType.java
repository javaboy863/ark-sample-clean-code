package com.ark.weather.platform.dto.job;

import lombok.Data;

/**
 * 天气job执行类型
 *
 **/
@Data
public class WeatherJobExecuteType {

    boolean cityTask = false;

    boolean stationTask = false;

}


