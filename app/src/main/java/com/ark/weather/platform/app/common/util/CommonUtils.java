package com.ark.weather.platform.app.common.util;

import com.google.common.collect.Lists;
import com.ark.weather.platform.app.common.SupplerTypeEnum;
import com.ark.weather.platform.app.common.WeatherAlertLevelEnum;
import com.ark.weather.platform.app.common.WeatherAlertTypeEnum;
import com.ark.weather.platform.app.common.WeatherConstant;
import com.ark.weather.platform.app.common.WeatherWindDirEnum;
import com.ark.weather.platform.app.common.WeatherWindLevelEnum;
import com.ark.weather.platform.domain.domain.CityInfoPO;
import com.ark.weather.platform.domain.domain.WeatherAlertInfoPO;
import com.ark.weather.platform.domain.domain.WeatherCurrentInfoPO;
import com.ark.weather.platform.domain.domain.WeatherFutureInfoPO;
import com.ark.weather.platform.domain.enums.DateTypeEnum;
import com.ark.weather.platform.domain.enums.SubjectTypeEnum;
import com.ark.weather.platform.dto.domainmodel.response.WeatherInfoDTO;
import com.ark.weather.platform.enums.SkyconEnum;
import com.ark.weather.platform.infrastructure.repository.dataobject.CaiYunWeatherAlertDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.CaiYunWeatherAllInfoDataDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.CaiYunWeatherDailyDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.CaiYunWeatherHourlyDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.CaiYunWeatherMinutelyDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.CaiYunWeatherRealTimeDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.CityInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.PositionInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.StationInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherAlertInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherAllInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherInfoDO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 工具类
 *
 */
public final class CommonUtils {
    /**
     * 1分钟毫秒数
     */
    private static final long ONE_MINUTE = 1000 * 60L;
    public static final int CODE_FOUR = 4;

    /**
     * 如果给定对象为空则返回默认对象
     *
     * @param obj        原始对象
     * @param defaultObj 默认对象
     * @param <T>        泛型
     * @return
     */
    public static <T> T defaultIfNull(T obj, T defaultObj) {
        return Objects.nonNull(obj) ? obj : defaultObj;
    }


    /**
     * 预警类型
     *
     * @return
     */
    private static String getAlertTypeName(String code) {
        if (StringUtils.length(code) != CODE_FOUR) {
            return "";
        }
        WeatherAlertTypeEnum weatherAlertTypeEnum = WeatherAlertTypeEnum.valueOfCode(code.substring(0, 2));
        if (weatherAlertTypeEnum != null) {
            return weatherAlertTypeEnum.getName();
        }
        return "";
    }

    /**
     * 预警级别
     *
     * @return
     */
    private static String getAlertLevel(String code) {
        if (StringUtils.length(code) != CODE_FOUR) {
            return "";
        }
        WeatherAlertLevelEnum weatherAlertLevelEnum = WeatherAlertLevelEnum.valueOfCode(code.substring(2, 4));
        if (weatherAlertLevelEnum != null) {
            return weatherAlertLevelEnum.getName();
        }
        return "";
    }


    /**
     * 门店转换成更通用的位置信息
     *
     * @param stationInfoDO
     * @return
     */
    public static PositionInfoDO getPositionInfo(StationInfoDO stationInfoDO) {
        if (stationInfoDO == null) {
            return null;
        }
        PositionInfoDO positionInfoDO = new PositionInfoDO();
        positionInfoDO.setSubjectType(SubjectTypeEnum.STATION.getCode());
        positionInfoDO.setCityId(stationInfoDO.getCityId());
        positionInfoDO.setCityName(stationInfoDO.getCity());
        positionInfoDO.setStationCode(stationInfoDO.getStationCode());
        positionInfoDO.setStationName(stationInfoDO.getStationName());
        positionInfoDO.setLat(stationInfoDO.getLat());
        positionInfoDO.setLng(stationInfoDO.getLng());

        return positionInfoDO;
    }


    /**
     * 城市转换成更通用的位置信息
     *
     * @param cityInfoDO
     * @return
     */
    public static PositionInfoDO getPositionInfo(CityInfoDO cityInfoDO) {
        if (cityInfoDO == null) {
            return null;
        }
        PositionInfoDO positionInfoDO = new PositionInfoDO();
        positionInfoDO.setSubjectType(SubjectTypeEnum.CITY.getCode());
        positionInfoDO.setCityId(cityInfoDO.getCityId());
        positionInfoDO.setCityName(cityInfoDO.getCity());
        positionInfoDO.setLat(cityInfoDO.getLat());
        positionInfoDO.setLng(cityInfoDO.getLng());

        return positionInfoDO;
    }

    /**
     * 将城市的存储对象转换为领域对象
     *
     * @param cityInfoPO 存储对象
     * @return
     */
    public static CityInfoDO getCityInfoDO(CityInfoPO cityInfoPO) {
        if (cityInfoPO == null) {
            return null;
        }
        CityInfoDO cityInfoDO = new CityInfoDO();
        cityInfoDO.setCityId(cityInfoPO.getCityId());
        cityInfoDO.setCity(cityInfoPO.getCity());
        cityInfoDO.setLat(cityInfoPO.getLat());
        cityInfoDO.setLng(cityInfoPO.getLng());

        return cityInfoDO;
    }


    /**
     * 将彩云的天气领域模型转换为通用天气领域模型
     *
     * @param caiYunWeatherAllInfoData
     * @return
     */
    public static WeatherAllInfoDO getWeatherAllInfoDO(CaiYunWeatherAllInfoDataDO caiYunWeatherAllInfoData, Date obsTime) {
        if (caiYunWeatherAllInfoData == null) {
            return null;
        }
        WeatherAllInfoDO weatherAllInfoDO = new WeatherAllInfoDO();
        // 预警信息
        weatherAllInfoDO.setAlert(getWeatherAlertInfoDOList(caiYunWeatherAllInfoData.getAlert()));
        // 当前实时天气
        weatherAllInfoDO.setRealtime(getWeatherInfoDO(caiYunWeatherAllInfoData.getRealtime(), obsTime));
        // 分钟的预警天气 minutely
        weatherAllInfoDO.setMinutely(getWeatherInfoDO(caiYunWeatherAllInfoData.getMinutely(), obsTime));
        // 小时的预警天气 hourly
        weatherAllInfoDO.setHourly(getWeatherInfoDO(caiYunWeatherAllInfoData.getHourly(), obsTime));
        // 天粒度的预警天气 daily
        weatherAllInfoDO.setDaily(getWeatherInfoDO(caiYunWeatherAllInfoData.getDaily(), obsTime));

        return weatherAllInfoDO;
    }

    /**
     * 彩云天气预警信息转换为通用预警列表信息
     *
     * @param caiYunAlertData
     * @return
     */
    public static List<WeatherAlertInfoDO> getWeatherAlertInfoDOList(CaiYunWeatherAlertDO caiYunAlertData) {
        if (caiYunAlertData == null
                || !WeatherConstant.CAI_YUN_SUCC_STATUS.equals(caiYunAlertData.getStatus())) {
            return null;
        }
        return caiYunAlertData.getContent().stream().map(content -> getWeatherAlertInfoDO(content)).filter(Objects::nonNull).collect(Collectors.toList());
    }


    /**
     * 彩云天气预警信息转换为通用预警信息
     *
     * @param weatherAlertData
     * @return
     */
    public static WeatherAlertInfoDO getWeatherAlertInfoDO(CaiYunWeatherAlertDO.CaiYunWeatherAlertDataDO weatherAlertData) {
        if (weatherAlertData == null || !WeatherConstant.CAI_YUN_ALERTING_STATUS.equals(weatherAlertData.getStatus())) {
            return null;
        }
        WeatherAlertInfoDO weatherAlertInfoDTO = new WeatherAlertInfoDO();
        weatherAlertInfoDTO.setPubTime(new Date(weatherAlertData.getPubtimestamp() * 1000));
        weatherAlertInfoDTO.setTitle(weatherAlertData.getTitle());
        weatherAlertInfoDTO.setContent(weatherAlertData.getDescription());
        weatherAlertInfoDTO.setAlertLevel(getAlertLevel(weatherAlertData.getCode()));
        weatherAlertInfoDTO.setAlertName(getAlertTypeName(weatherAlertData.getCode()));
        return weatherAlertInfoDTO;
    }

    /**
     * 彩云实时天气信息转换为通用天气领域信息
     *
     * @param caiYunWeatherRealTimeDO
     * @return
     */
    public static WeatherInfoDO getWeatherInfoDO(CaiYunWeatherRealTimeDO caiYunWeatherRealTimeDO, Date obsTime) {
        if (caiYunWeatherRealTimeDO == null || !WeatherConstant.CAI_YUN_SUCC_STATUS.equals(caiYunWeatherRealTimeDO.getStatus())) {
            return null;
        }
        WeatherInfoDO weatherInfoDO = new WeatherInfoDO();
        weatherInfoDO.setObsTime(obsTime);
        weatherInfoDO.setForecastTime(weatherInfoDO.getObsTime());
        CaiYunWeatherRealTimeDO.AirQuality airQuality = caiYunWeatherRealTimeDO.getAir_quality();
        if (airQuality != null) {
            weatherInfoDO.setAqiChn(airQuality.getAqi().getChn());
            weatherInfoDO.setPm25(airQuality.getPm25());
        }
        weatherInfoDO.setVisibility(caiYunWeatherRealTimeDO.getVisibility() != null ? new BigDecimal(caiYunWeatherRealTimeDO.getVisibility().toString()) : null);
        CaiYunWeatherRealTimeDO.CaiYunWeatherWindDataDO wind = caiYunWeatherRealTimeDO.getWind();
        weatherInfoDO.setWindDegrees(wind.getDirection() != null ? new BigDecimal(wind.getDirection().toString()) : null);
        weatherInfoDO.setWindDir(WeatherWindDirEnum.valueOfDegrees(wind.getDirection()).getName());
        weatherInfoDO.setWindSpeed(wind.getSpeed() != null ? new BigDecimal(wind.getSpeed().toString()) : null);
        weatherInfoDO.setWindLevel(WeatherWindLevelEnum.valueOfSpeed(wind.getSpeed()).getLevel());
        weatherInfoDO.setTemperature(caiYunWeatherRealTimeDO.getTemperature() != null ? new BigDecimal(caiYunWeatherRealTimeDO.getTemperature().toString()) : null);
        weatherInfoDO.setHumidity((int) (caiYunWeatherRealTimeDO.getHumidity() * 100));
        CaiYunWeatherRealTimeDO.PrecipitationData localPrecipitation = caiYunWeatherRealTimeDO.getPrecipitation().getLocal();
        if (localPrecipitation != null && WeatherConstant.CAI_YUN_SUCC_STATUS.equals(localPrecipitation.getStatus())) {
            weatherInfoDO.setPrecipitation(new BigDecimal(localPrecipitation.getIntensity().toString()));
        }
        // 天气现象转换
        weatherInfoDO.setSkycon(caiYunWeatherRealTimeDO.getSkycon());
        weatherInfoDO.setSkyconName(SkyconEnum.valueOf(caiYunWeatherRealTimeDO.getSkycon()).getName());
        return weatherInfoDO;
    }


    /**
     * 彩云分钟预报天气信息转换为通用天气领域信息
     *
     * @param caiYunWeatherMinutelyDO
     * @return
     */
    public static List<WeatherInfoDO> getWeatherInfoDO(CaiYunWeatherMinutelyDO caiYunWeatherMinutelyDO, Date obsTime) {
        if (caiYunWeatherMinutelyDO == null
                || !WeatherConstant.CAI_YUN_SUCC_STATUS.equals(caiYunWeatherMinutelyDO.getStatus())
                || CollectionUtils.isEmpty(caiYunWeatherMinutelyDO.getPrecipitation_2h())) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        String description = caiYunWeatherMinutelyDO.getDescription();
        // 分钟降雨信息
        List<Float> precipitationList = caiYunWeatherMinutelyDO.getPrecipitation_2h();
        List<WeatherInfoDO> restList = Lists.newArrayListWithCapacity(precipitationList.size());
        WeatherInfoDO weatherInfoDO = null;
        long time = obsTime.getTime();
        for (int i = 0; i < precipitationList.size(); i++) {
            Float precipitation = precipitationList.get(i);
            weatherInfoDO = new WeatherInfoDO();
            weatherInfoDO.setObsTime(obsTime);
            // 去除秒，只保留分钟级别字符串
            String dateStr = DateUtils.format(new Date(time + ONE_MINUTE * i), DateUtils.YYYY_MM_DD_HH_MM);
            weatherInfoDO.setForecastTime(DateUtils.parse(dateStr, DateUtils.YYYY_MM_DD_HH_MM));
            weatherInfoDO.setPrecipitation(new BigDecimal(precipitation.toString()));
            weatherInfoDO.setDescription(description);
            restList.add(weatherInfoDO);
        }
        return restList;
    }

    /**
     * 彩云小时预报天气信息转换为通用天气领域信息
     *
     * @param caiYunWeatherHourlyDO
     * @return
     */
    public static List<WeatherInfoDO> getWeatherInfoDO(CaiYunWeatherHourlyDO caiYunWeatherHourlyDO, Date obsTime) {
        if (caiYunWeatherHourlyDO == null
                || !WeatherConstant.CAI_YUN_SUCC_STATUS.equals(caiYunWeatherHourlyDO.getStatus())
                || CollectionUtils.isEmpty(caiYunWeatherHourlyDO.getSkycon())) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        String description = caiYunWeatherHourlyDO.getDescription();
        // 空气质量、 能见度、风力风向、温度、湿度、降水量和天气现象
        CaiYunWeatherHourlyDO.AirQuality airQuality = caiYunWeatherHourlyDO.getAir_quality();
        List<CaiYunWeatherHourlyDO.HourlyObj<CaiYunWeatherHourlyDO.AqiValue>> aqiList = airQuality.getAqi();
        List<CaiYunWeatherHourlyDO.HourlyObj<Integer>> pm25List = airQuality.getPm25();
        List<CaiYunWeatherHourlyDO.HourlyObj<Float>> visibilityList = caiYunWeatherHourlyDO.getVisibility();
        List<CaiYunWeatherHourlyDO.Wind> windList = caiYunWeatherHourlyDO.getWind();
        List<CaiYunWeatherHourlyDO.HourlyObj<Float>> temperatureList = caiYunWeatherHourlyDO.getTemperature();
        List<CaiYunWeatherHourlyDO.HourlyObj<Float>> humidityList = caiYunWeatherHourlyDO.getHumidity();
        List<CaiYunWeatherHourlyDO.HourlyObj<Float>> precipitationList = caiYunWeatherHourlyDO.getPrecipitation();
        List<CaiYunWeatherHourlyDO.HourlyObj<String>> skyconList = caiYunWeatherHourlyDO.getSkycon();
        // 解析数据
        List<WeatherInfoDO> restList = Lists.newArrayListWithCapacity(skyconList.size());
        WeatherInfoDO weatherInfoDO = null;
        for (int i = 0; i < skyconList.size(); i++) {
            weatherInfoDO = new WeatherInfoDO();
            weatherInfoDO.setObsTime(obsTime);
            // 天气现象
            CaiYunWeatherHourlyDO.HourlyObj<String> skycon = skyconList.get(i);
            weatherInfoDO.setForecastTime(skycon.getDatetime());
            weatherInfoDO.setSkycon(skycon.getValue());
            weatherInfoDO.setSkyconName(SkyconEnum.valueOf(skycon.getValue()).getName());
            // 空气质量
            CaiYunWeatherHourlyDO.HourlyObj<CaiYunWeatherHourlyDO.AqiValue> aqi = aqiList.get(i);
            CaiYunWeatherHourlyDO.HourlyObj<Integer> pm25 = pm25List.get(i);
            weatherInfoDO.setAqiChn(aqi.getValue().getChn());
            weatherInfoDO.setPm25(pm25.getValue());
            // 能见度
            CaiYunWeatherHourlyDO.HourlyObj<Float> visibility = visibilityList.get(i);
            weatherInfoDO.setVisibility(new BigDecimal(visibility.getValue().toString()));
            // 风力风速信息
            CaiYunWeatherHourlyDO.Wind wind = windList.get(i);
            weatherInfoDO.setWindDegrees(wind.getDirection() != null ? new BigDecimal(wind.getDirection().toString()) : null);
            weatherInfoDO.setWindDir(WeatherWindDirEnum.valueOfDegrees(wind.getDirection()).getName());
            weatherInfoDO.setWindSpeed(wind.getSpeed() != null ? new BigDecimal(wind.getSpeed().toString()) : null);
            weatherInfoDO.setWindLevel(WeatherWindLevelEnum.valueOfSpeed(wind.getSpeed()).getLevel());
            // 温度
            CaiYunWeatherHourlyDO.HourlyObj<Float> temperature = temperatureList.get(i);
            weatherInfoDO.setTemperature(new BigDecimal(temperature.getValue().toString()));
            // 相对湿度
            CaiYunWeatherHourlyDO.HourlyObj<Float> humidity = humidityList.get(i);
            weatherInfoDO.setHumidity((int) (humidity.getValue() * 100));
            // 降水量
            CaiYunWeatherHourlyDO.HourlyObj<Float> precipitation = precipitationList.get(i);
            weatherInfoDO.setPrecipitation(new BigDecimal(precipitation.getValue().toString()));
            // 当前天气描述
            weatherInfoDO.setDescription(description);
            restList.add(weatherInfoDO);
        }
        return restList;
    }


    /**
     * 彩云天预报天气信息转换为通用天气领域信息
     *
     * @param caiYunWeatherDailyDO
     * @return
     */
    public static List<WeatherInfoDO> getWeatherInfoDO(CaiYunWeatherDailyDO caiYunWeatherDailyDO, Date obsTime) {
        if (caiYunWeatherDailyDO == null
                || !WeatherConstant.CAI_YUN_SUCC_STATUS.equals(caiYunWeatherDailyDO.getStatus())
                || CollectionUtils.isEmpty(caiYunWeatherDailyDO.getSkycon())) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        // 空气质量、 能见度、风力风向、温度、湿度、降水量和天气现象
        CaiYunWeatherDailyDO.AirQuality airQuality = caiYunWeatherDailyDO.getAir_quality();
        List<CaiYunWeatherDailyDO.DailyObj<CaiYunWeatherDailyDO.AqiValue>> aqiList = airQuality.getAqi();
        List<CaiYunWeatherDailyDO.DailyObj<Integer>> pm25List = airQuality.getPm25();
        List<CaiYunWeatherDailyDO.DailyObj<Double>> visibilityList = caiYunWeatherDailyDO.getVisibility();
        List<CaiYunWeatherDailyDO.DailyObj<CaiYunWeatherDailyDO.Wind>> windList = caiYunWeatherDailyDO.getWind();
        List<CaiYunWeatherDailyDO.DailyObj<Float>> temperatureList = caiYunWeatherDailyDO.getTemperature();
        List<CaiYunWeatherDailyDO.DailyObj<Float>> humidityList = caiYunWeatherDailyDO.getHumidity();
        List<CaiYunWeatherDailyDO.DailyObj<Float>> precipitationList = caiYunWeatherDailyDO.getPrecipitation();
        List<CaiYunWeatherDailyDO.Skycon> skyconList = caiYunWeatherDailyDO.getSkycon();
        List<CaiYunWeatherDailyDO.Skycon> skyconDayList = caiYunWeatherDailyDO.getSkycon_08h_20h();
        List<CaiYunWeatherDailyDO.Skycon> skyconNightList = caiYunWeatherDailyDO.getSkycon_20h_32h();
        // 解析数据
        List<WeatherInfoDO> restList = Lists.newArrayListWithCapacity(skyconList.size());
        String todayStr = DateUtils.format(new Date(), DateUtils.YYYY_MM_DD);
        WeatherInfoDO weatherInfoDO = null;
        CaiYunWeatherDailyDO.Skycon skycon = null;
        CaiYunWeatherDailyDO.Skycon skyconDay = null;
        CaiYunWeatherDailyDO.Skycon skyconNight = null;
        for (int i = 0; i < skyconList.size(); i++) {
            weatherInfoDO = new WeatherInfoDO();
            weatherInfoDO.setObsTime(obsTime);
            // 天气现象
            skycon = skyconList.get(i);
            // 抛弃当天的的天粒度预报信息
            if (StringUtils.equals(DateUtils.format(skycon.getDate(), DateUtils.YYYY_MM_DD), todayStr)) {
                continue;
            }
            weatherInfoDO.setForecastTime(skycon.getDate());
            skyconDay = skyconDayList.get(i);
            skyconNight = skyconNightList.get(i);
            // 天气现象
            weatherInfoDO.setSkycon(skycon.getValue());
            weatherInfoDO.setSkyconName(SkyconEnum.valueOf(skycon.getValue()).getName());
            weatherInfoDO.setSkyconDay(skyconDay.getValue());
            weatherInfoDO.setSkyconDayName(SkyconEnum.valueOf(skyconDay.getValue()).getName());
            weatherInfoDO.setSkyconNight(skyconNight.getValue());
            weatherInfoDO.setSkyconNightName(SkyconEnum.valueOf(skyconNight.getValue()).getName());
            // 空气质量
            CaiYunWeatherDailyDO.DailyObj<CaiYunWeatherDailyDO.AqiValue> aqi = aqiList.get(i);
            CaiYunWeatherDailyDO.DailyObj<Integer> pm25 = pm25List.get(i);
            weatherInfoDO.setAqiChn(aqi.getAvg().getChn());
            weatherInfoDO.setPm25(pm25.getAvg());
            // 能见度
            CaiYunWeatherDailyDO.DailyObj<Double> visibility = visibilityList.get(i);
            weatherInfoDO.setVisibility(new BigDecimal(visibility.getAvg().toString()));
            // 风力风速信息
            CaiYunWeatherDailyDO.Wind wind = windList.get(i).getAvg();
            weatherInfoDO.setWindDegrees(wind.getDirection() != null ? new BigDecimal(wind.getDirection().toString()) : null);
            weatherInfoDO.setWindDir(WeatherWindDirEnum.valueOfDegrees(wind.getDirection()).getName());
            weatherInfoDO.setWindSpeed(wind.getSpeed() != null ? new BigDecimal(wind.getSpeed().toString()) : null);
            weatherInfoDO.setWindLevel(WeatherWindLevelEnum.valueOfSpeed(wind.getSpeed()).getLevel());
            // 温度
            CaiYunWeatherDailyDO.DailyObj<Float> temperature = temperatureList.get(i);
            weatherInfoDO.setTemperature(new BigDecimal(temperature.getAvg().toString()));
            // 相对湿度
            CaiYunWeatherDailyDO.DailyObj<Float> humidity = humidityList.get(i);
            weatherInfoDO.setHumidity((int) (humidity.getAvg() * 100));
            // 降水量
            Float precipitation = precipitationList.get(i).getAvg();
            weatherInfoDO.setPrecipitation(new BigDecimal(precipitation.toString()));
            restList.add(weatherInfoDO);
        }
        return restList;

    }


    public static List<WeatherAlertInfoPO> getWeatherAlertInfoPO(List<WeatherAlertInfoDO> alert, PositionInfoDO positionInfoDO) {
        if (CollectionUtils.isEmpty(alert)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return alert.stream().map(infoDO -> {
            return getWeatherAlertInfoPO(infoDO, positionInfoDO);
        }).collect(Collectors.toList());
    }


    public static WeatherAlertInfoPO getWeatherAlertInfoPO(WeatherAlertInfoDO weatherAlertInfoDO, PositionInfoDO positionInfoDO) {
        if (weatherAlertInfoDO == null) {
            return null;
        }
        WeatherAlertInfoPO weatherAlertInfoPO = new WeatherAlertInfoPO();
        weatherAlertInfoPO.setStationCode(positionInfoDO.getStationCode());
        weatherAlertInfoPO.setStationName(positionInfoDO.getStationName());
        weatherAlertInfoPO.setLat(positionInfoDO.getLat());
        weatherAlertInfoPO.setLng(positionInfoDO.getLng());
        weatherAlertInfoPO.setSupplierType(SupplerTypeEnum.CAIYUN_TYPE.getCode());
        weatherAlertInfoPO.setPubTime(weatherAlertInfoDO.getPubTime());
        weatherAlertInfoPO.setTitle(weatherAlertInfoDO.getTitle());
        weatherAlertInfoPO.setContent(weatherAlertInfoDO.getContent());
        weatherAlertInfoPO.setAlertLevel(weatherAlertInfoDO.getAlertLevel());
        weatherAlertInfoPO.setAlertName(weatherAlertInfoDO.getAlertName());
        weatherAlertInfoPO.setCreateTime(new Date());
        weatherAlertInfoPO.setUpdateTime(weatherAlertInfoPO.getCreateTime());
        return weatherAlertInfoPO;
    }


    public static WeatherCurrentInfoPO getWeatherCurrentInfoPO(WeatherInfoDO realtime, PositionInfoDO positionInfoDO) {
        if (realtime == null) {
            return null;
        }
        WeatherCurrentInfoPO weatherCurrentInfoPO = new WeatherCurrentInfoPO();
        weatherCurrentInfoPO.setStationCode(positionInfoDO.getStationCode());
        weatherCurrentInfoPO.setStationName(positionInfoDO.getStationName());
        weatherCurrentInfoPO.setLat(positionInfoDO.getLat());
        weatherCurrentInfoPO.setLng(positionInfoDO.getLng());
        weatherCurrentInfoPO.setSupplierType(SupplerTypeEnum.CAIYUN_TYPE.getCode());
        weatherCurrentInfoPO.setObsTime(realtime.getObsTime());
        weatherCurrentInfoPO.setTemperature(realtime.getTemperature());
        weatherCurrentInfoPO.setHumidity(realtime.getHumidity());
        weatherCurrentInfoPO.setWindDegrees(realtime.getWindDegrees());
        weatherCurrentInfoPO.setWindDir(realtime.getWindDir());
        weatherCurrentInfoPO.setWindLevel(realtime.getWindLevel());
        weatherCurrentInfoPO.setPrecipitation(realtime.getPrecipitation());
        weatherCurrentInfoPO.setWeather(realtime.getSkyconName());
        weatherCurrentInfoPO.setCreateTime(new Date());
        weatherCurrentInfoPO.setUpdateTime(weatherCurrentInfoPO.getCreateTime());
        return weatherCurrentInfoPO;
    }

    public static List<WeatherFutureInfoPO> getWeatherFutureInfoPO(List<WeatherInfoDO> weatherInfoDOList, PositionInfoDO positionInfoDO, Integer dateType) {
        if (CollectionUtils.isEmpty(weatherInfoDOList)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return weatherInfoDOList.stream().map(weatherInfoDO -> getWeatherFutureInfoPO(weatherInfoDO, positionInfoDO, dateType)).collect(Collectors.toList());
    }


    public static WeatherFutureInfoPO getWeatherFutureInfoPO(WeatherInfoDO weatherInfoDO, PositionInfoDO positionInfoDO, Integer dateType) {
        if (weatherInfoDO == null) {
            return null;
        }
        WeatherFutureInfoPO weatherFutureInfoPO = new WeatherFutureInfoPO();
        BeanUtils.copyProperties(weatherInfoDO, weatherFutureInfoPO);
        weatherFutureInfoPO.setSubjectType(positionInfoDO.getSubjectType());
        weatherFutureInfoPO.setDateType(dateType);
        weatherFutureInfoPO.setCityId(positionInfoDO.getCityId());
        weatherFutureInfoPO.setStationCode(positionInfoDO.getStationCode());
        weatherFutureInfoPO.setStationName(positionInfoDO.getStationName());
        weatherFutureInfoPO.setLng(positionInfoDO.getLng());
        weatherFutureInfoPO.setLat(positionInfoDO.getLat());
        return weatherFutureInfoPO;
    }


    public static WeatherInfoDO getWeatherInfoDO(WeatherFutureInfoPO weatherFutureInfoPO) {
        if (weatherFutureInfoPO == null) {
            return null;
        }
        WeatherInfoDO weatherInfoDO = new WeatherInfoDO();
        BeanUtils.copyProperties(weatherFutureInfoPO, weatherInfoDO);
        return weatherInfoDO;
    }

    /**
     * 统一转换为天气传输对象
     *
     * @param weatherInfoDO
     * @return
     * @see DateTypeEnum
     */
    public static WeatherInfoDTO getWeatherInfoDTO(WeatherInfoDO weatherInfoDO) {
        if (weatherInfoDO == null) {
            return null;
        }
        WeatherInfoDTO weatherInfoDTO = new WeatherInfoDTO();
        BeanUtils.copyProperties(weatherInfoDO, weatherInfoDTO);
        Date forecastTime = weatherInfoDO.getForecastTime();
        if (weatherInfoDO != null) {
            weatherInfoDTO.setDate(DateUtils.format(forecastTime, DateUtils.YYYY_MM_DD_HH_MM_SS));
        }
        weatherInfoDTO.setVisibility(weatherInfoDO.getVisibility().doubleValue());
        weatherInfoDTO.setWindDegrees(weatherInfoDO.getWindDegrees().doubleValue());
        weatherInfoDTO.setWindSpeed(weatherInfoDO.getWindSpeed().doubleValue());
        weatherInfoDTO.setTemperature(weatherInfoDO.getTemperature().doubleValue());
        weatherInfoDTO.setPrecipitation(weatherInfoDO.getPrecipitation().doubleValue());

        return weatherInfoDTO;
    }
}
