package com.ark.weather.platform.app.service.ext;

import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.app.common.SupplerTypeEnum;
import com.ark.weather.platform.app.common.WeatherConstant;
import com.ark.weather.platform.app.common.WeatherExtPointConstant;
import com.ark.weather.platform.app.common.util.CommonUtils;
import com.ark.weather.platform.domain.domain.WeatherAlertInfoPO;
import com.ark.weather.platform.domain.domain.WeatherCurrentInfoPO;
import com.ark.weather.platform.domain.domain.WeatherFutureInfoPO;
import com.ark.weather.platform.domain.enums.DateTypeEnum;
import com.ark.weather.platform.domain.enums.SubjectTypeEnum;
import com.ark.weather.platform.infrastructure.common.ErrorCodeEnum;
import com.ark.weather.platform.infrastructure.repository.dataobject.CaiYunWeatherAllInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.PositionInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherAllInfoDO;
import com.ark.weather.platform.infrastructure.repository.http.CaiYunWeatherProxy;
import com.google.common.collect.Lists;
import com.ark.layer.exception.BizException;
import com.ark.layer.extension.Extension;
import com.ark.weather.platform.domain.repository.db.IWeatherAlertInfoRepository;
import com.ark.weather.platform.domain.repository.db.IWeatherCurrentInfoRepository;
import com.ark.weather.platform.domain.repository.db.IWeatherFutureInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 天气任务的彩云天气扩展点
 *
 */
@Slf4j
@Service
@Extension(bizId = WeatherExtPointConstant.BIZ_ID_CAI_YUN, useCase = WeatherExtPointConstant.USE_CASE_WEATHER, scenario = WeatherExtPointConstant.SCENARIO_WEATHER_JOB)
public class CaiYunWeatherInfoJobExtPt implements IWeatherInfoJobExtPt {
    @Resource
    private IWeatherAlertInfoRepository weatherAlertInfoRepository;
    @Resource
    private IWeatherCurrentInfoRepository weatherCurrentInfoRepository;
    @Resource
    private IWeatherFutureInfoRepository weatherFutureInfoRepository;
    @Resource
    private CaiYunWeatherProxy caiYunWeatherProxy;

    /**
     * 调用远程
     *
     * @param positionInfoDO 绑定参数
     * @return
     */
    @Override
    public WeatherAllInfoDO callRemote(PositionInfoDO positionInfoDO) {
        // 获取天气信息
        CaiYunWeatherAllInfoDO caiYunWeatherAllInfo = caiYunWeatherProxy.getCaiYunWeatherAllInfo(positionInfoDO.getLat().doubleValue(), positionInfoDO.getLng().doubleValue());
        if (caiYunWeatherAllInfo == null
                || !WeatherConstant.CAI_YUN_SUCC_STATUS.equals(caiYunWeatherAllInfo.getStatus())
                || caiYunWeatherAllInfo.getResult() == null) {
            log.error("彩云天气服务查询失败。param:{}, result:{}", JSON.toJSONString(positionInfoDO), JSON.toJSONString(caiYunWeatherAllInfo));
            throw new BizException(ErrorCodeEnum.B_WEATHER_INFO_QUERY_FAIL);
        }
        Long serverTime = caiYunWeatherAllInfo.getServer_time();
        Date obsTime = new Date(serverTime * 1000L);
        // 转换对象
        return CommonUtils.getWeatherAllInfoDO(caiYunWeatherAllInfo.getResult(), obsTime);
    }


    @Override
    public void store(WeatherAllInfoDO weatherAllInfoDO, PositionInfoDO positionInfoDO) {
        if (Objects.isNull(weatherAllInfoDO)) {
            log.error("保存的天气数据为空，position:{}", JSON.toJSONString(positionInfoDO));
            throw new BizException(ErrorCodeEnum.B_WEATHER_INFO_IS_EMPTY);
        }
        // 保存门店天气预警信息
        if (CollectionUtils.isNotEmpty(weatherAllInfoDO.getAlert()) && SubjectTypeEnum.STATION.getCode().equals(positionInfoDO.getSubjectType())) {
            List<WeatherAlertInfoPO> weatherAlertInfoPOList = CommonUtils.getWeatherAlertInfoPO(weatherAllInfoDO.getAlert(), positionInfoDO);
            weatherAlertInfoPOList.forEach(weatherAlertInfoDO -> {
                weatherAlertInfoDO.setSupplierType(SupplerTypeEnum.CAIYUN_TYPE.getCode());
                weatherAlertInfoRepository.save(weatherAlertInfoDO);
            });
        }
        // 保存门店天气实时信息
        if (weatherAllInfoDO.getRealtime() != null && SubjectTypeEnum.STATION.getCode().equals(positionInfoDO.getSubjectType())) {
            WeatherCurrentInfoPO weatherCurrentInfoPO = CommonUtils.getWeatherCurrentInfoPO(weatherAllInfoDO.getRealtime(), positionInfoDO);
            weatherCurrentInfoPO.setSupplierType(SupplerTypeEnum.CAIYUN_TYPE.getCode());
            weatherCurrentInfoRepository.save(weatherCurrentInfoPO);
        }

        // 逐分钟降雨有门店和城市都要保存
        List<WeatherFutureInfoPO> minutelyWeatherFutureInfoList = CommonUtils.getWeatherFutureInfoPO(weatherAllInfoDO.getMinutely(), positionInfoDO, DateTypeEnum.MINUTELY.getCode());
        // 逐小时有门店和城市都要保存
        List<WeatherFutureInfoPO> hourlyWeatherFutureInfoList = CommonUtils.getWeatherFutureInfoPO(weatherAllInfoDO.getHourly(), positionInfoDO, DateTypeEnum.HOURLY.getCode());
        // 逐天有门店和城市都要保存
        List<WeatherFutureInfoPO> dailyWeatherFutureInfoList = CommonUtils.getWeatherFutureInfoPO(weatherAllInfoDO.getDaily(), positionInfoDO, DateTypeEnum.DAILY.getCode());

        List<WeatherFutureInfoPO> weatherFutureInfoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(minutelyWeatherFutureInfoList)) {
            weatherFutureInfoList.addAll(minutelyWeatherFutureInfoList);
        }
        if (CollectionUtils.isNotEmpty(hourlyWeatherFutureInfoList)) {
            weatherFutureInfoList.addAll(hourlyWeatherFutureInfoList);
        }
        if (CollectionUtils.isNotEmpty(dailyWeatherFutureInfoList)) {
            weatherFutureInfoList.addAll(dailyWeatherFutureInfoList);
        }
        weatherFutureInfoRepository.updateOrInsert(weatherFutureInfoList);
    }
}
