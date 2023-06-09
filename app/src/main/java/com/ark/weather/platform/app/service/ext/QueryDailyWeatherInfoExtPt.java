package com.ark.weather.platform.app.service.ext;

import com.ark.weather.platform.api.ext.IQueryWeatherInfoExtPt;
import com.ark.weather.platform.app.common.WeatherExtPointConstant;
import com.ark.weather.platform.app.common.util.CommonUtils;
import com.ark.weather.platform.app.common.util.DateUtils;
import com.ark.weather.platform.domain.enums.DateTypeEnum;
import com.ark.weather.platform.domain.enums.SubjectTypeEnum;
import com.ark.weather.platform.dto.PositionInfoDTO;
import com.ark.weather.platform.dto.cmd.QueryWeatherInfoCmd;
import com.ark.weather.platform.dto.domainmodel.response.WeatherInfoDTO;
import com.ark.weather.platform.infrastructure.common.ErrorCodeEnum;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherAllInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherInfoDO;
import com.ark.layer.exception.BizException;
import com.ark.layer.extension.Extension;
import com.ark.weather.platform.app.service.WeatherFutureInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 彩云天气查询方法中的扩展点实现
 *
 */
@Slf4j
@Service
@Extension(bizId = WeatherExtPointConstant.BIZ_ID_CAI_YUN, useCase = WeatherExtPointConstant.USE_CASE_WEATHER, scenario = WeatherExtPointConstant.SCENARIO_WEATHER_QUERY_DAILY)
public class QueryDailyWeatherInfoExtPt extends BaseQueryCaiYunWeatherInfoExtPt implements
    IQueryWeatherInfoExtPt {
    /**
     * 最往前查14天的天气数据
     */
    private static final int MIN_START = -14;
    /**
     * 未来能往后查7天的天气数据
     */
    private static final int MAX_END = 7;
    @Resource
    private WeatherFutureInfoService weatherFutureInfoService;

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
        if (StringUtils.isNotBlank(positionInfoDTO.getCityId())) {
            // 存在查询历史数据，则所有数据直接从数据库出
            return getWeatherInfoFromDb(positionInfoDTO);
        }

        // 只查预报天气数据，直接调用第三方彩云天气获取
        return getWeatherInfoFromRemote(positionInfoDTO);
    }

    /**
     * 从远程供应商获取天气信息
     *
     * @param positionInfoDTO
     * @return
     */
    private List<WeatherInfoDTO> getWeatherInfoFromRemote(PositionInfoDTO positionInfoDTO) {
        // 获取天气信息
        WeatherAllInfoDO weatherAllInfoDO = super.callCaiYunRemote(positionInfoDTO);
        // 获取天气中的天预报信息
        List<WeatherInfoDO> daily = getDayWeatherInfo(weatherAllInfoDO);
        // 转换为DTO
        List<WeatherInfoDTO> weatherInfoDTOList = getWeatherInfoDTOS(daily);
        // 按照参数裁剪天气信息
        return subWeatherInfo(positionInfoDTO, weatherInfoDTOList);
    }

    /**
     * 获取固定天的数据
     *
     * @param positionInfoDTO
     * @param weatherInfoDTOList
     * @return
     */
    private List<WeatherInfoDTO> subWeatherInfo(PositionInfoDTO positionInfoDTO, List<WeatherInfoDTO> weatherInfoDTOList) {
        if (weatherInfoDTOList.size() > positionInfoDTO.getEnd() && positionInfoDTO.getEnd() >= 0) {
            weatherInfoDTOList = weatherInfoDTOList.subList(0, positionInfoDTO.getEnd() + 1);
        }
        return weatherInfoDTOList;
    }

    /**
     * 从天气信息中获取天预报信息
     *
     * @param weatherAllInfoDO
     * @return
     */
    private List<WeatherInfoDO> getDayWeatherInfo(WeatherAllInfoDO weatherAllInfoDO) {
        List<WeatherInfoDO> daily = weatherAllInfoDO.getDaily();
        if (CollectionUtils.isEmpty(daily)) {
            throw new BizException(ErrorCodeEnum.B_WEATHER_INFO_IS_EMPTY);
        }
        return daily;
    }

    /**
     * 从数据库获取天气信息
     *
     * @param positionInfoDTO
     * @return
     */
    private List<WeatherInfoDTO> getWeatherInfoFromDb(PositionInfoDTO positionInfoDTO) {
        Date today = DateUtils.parse(DateUtils.format(new Date(), DateUtils.YYYY_MM_DD), DateUtils.YYYY_MM_DD);
        Date startDate = DateUtils.plusDay(today, positionInfoDTO.getStart());
        Date endDate = DateUtils.plusDay(today, positionInfoDTO.getEnd());
        List<WeatherInfoDO> list = weatherFutureInfoService.findWeatherFutureInfos(SubjectTypeEnum.CITY.getCode(), DateTypeEnum.DAILY.getCode(), positionInfoDTO.getCityId(), null, startDate, endDate);
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException(ErrorCodeEnum.B_WEATHER_INFO_IS_EMPTY);
        }
        // 转换，返回
        return getWeatherInfoDTOS(list);
    }


    /**
     * 对象转换
     *
     * @param list
     * @return
     */
    private List<WeatherInfoDTO> getWeatherInfoDTOS(List<WeatherInfoDO> list) {
        return list.stream().map(weatherInfoDO -> {
            WeatherInfoDTO weatherInfoDTO = CommonUtils.getWeatherInfoDTO(weatherInfoDO);
            Date forecastTime = weatherInfoDO.getForecastTime();
            if (weatherInfoDO != null) {
                weatherInfoDTO.setDate(DateUtils.format(forecastTime, DateUtils.YYYY_MM_DD));
            }
            return weatherInfoDTO;
        }).collect(Collectors.toList());
    }
}
