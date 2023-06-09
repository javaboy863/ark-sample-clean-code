package com.ark.weather.platform.app.service.ext;

import com.ark.weather.platform.api.ext.IQueryWeatherInfoExtPt;
import com.ark.weather.platform.app.common.WeatherExtPointConstant;
import com.ark.weather.platform.app.common.util.CommonUtils;
import com.ark.weather.platform.app.common.util.DateUtils;
import com.ark.weather.platform.dto.PositionInfoDTO;
import com.ark.weather.platform.dto.cmd.QueryWeatherInfoCmd;
import com.ark.weather.platform.dto.domainmodel.response.WeatherInfoDTO;
import com.ark.weather.platform.infrastructure.common.ErrorCodeEnum;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherAllInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherInfoDO;
import com.ark.layer.exception.BizException;
import com.ark.layer.extension.Extension;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 彩云天气查询方法中的扩展点实现
 *
 */
@Slf4j
@Service
@Extension(bizId = WeatherExtPointConstant.BIZ_ID_CAI_YUN, useCase = WeatherExtPointConstant.USE_CASE_WEATHER, scenario = WeatherExtPointConstant.SCENARIO_WEATHER_QUERY_HOUR)
public class QueryHourlyWeatherInfoExtPt extends BaseQueryCaiYunWeatherInfoExtPt implements
    IQueryWeatherInfoExtPt {
    /**
     * 最往前查当前小时的天气数据
     */
    private static final int MIN_START = 0;
    /**
     * 未来能往后查7小时的天气数据
     */
    private static final int MAX_END = 7;

    /**
     * 校验参数
     *
     * @param cmd 参数
     */
    @Override
    public void validateCmd(QueryWeatherInfoCmd cmd) {
        super.validateCmd(cmd);
        super.checkStartEndParam(cmd, MIN_START, MAX_END);
    }


    /**
     * 调用远程
     *
     * @param positionInfoDTO 位置参数
     * @return
     */
    @Override
    public List<WeatherInfoDTO> callRemote(PositionInfoDTO positionInfoDTO) {
        WeatherAllInfoDO weatherAllInfoDO = super.callCaiYunRemote(positionInfoDTO);
        List<WeatherInfoDO> hourly = weatherAllInfoDO.getHourly();
        if (CollectionUtils.isEmpty(hourly)) {
            throw new BizException(ErrorCodeEnum.B_WEATHER_INFO_IS_EMPTY);
        }

        return getWeatherInfoDTOS(positionInfoDTO, hourly);
    }

    /**
     * 封装结果对象
     *
     * @param positionInfoDTO
     * @param hourly
     * @return
     */
    private List<WeatherInfoDTO> getWeatherInfoDTOS(PositionInfoDTO positionInfoDTO, List<WeatherInfoDO> hourly) {
        // 转换对象
        List<WeatherInfoDTO> weatherInfoDTOList = getWeatherInfoDTOS(hourly);
        // 裁剪小时天气信息
        return subWeatherInfoDTOS(positionInfoDTO, weatherInfoDTOList);
    }

    /**
     * 裁剪结果数据
     *
     * @param positionInfoDTO
     * @param weatherInfoDTOList
     * @return
     */
    private List<WeatherInfoDTO> subWeatherInfoDTOS(PositionInfoDTO positionInfoDTO, List<WeatherInfoDTO> weatherInfoDTOList) {
        if (weatherInfoDTOList.size() > positionInfoDTO.getEnd() && positionInfoDTO.getEnd() >= 0) {
            weatherInfoDTOList = weatherInfoDTOList.subList(0, positionInfoDTO.getEnd() + 1);
        }
        return weatherInfoDTOList;
    }

    /**
     * 转换对象为DTO
     *
     * @param hourly
     * @return
     */
    private List<WeatherInfoDTO> getWeatherInfoDTOS(List<WeatherInfoDO> hourly) {
        return hourly.stream().map(weatherInfoDO -> {
            WeatherInfoDTO weatherInfoDTO = CommonUtils.getWeatherInfoDTO(weatherInfoDO);
            Date forecastTime = weatherInfoDO.getForecastTime();
            if (weatherInfoDO != null) {
                weatherInfoDTO.setDate(DateUtils.format(forecastTime, DateUtils.YYYY_MM_DD_HH));
            }
            return weatherInfoDTO;
        }).collect(Collectors.toList());
    }
}
