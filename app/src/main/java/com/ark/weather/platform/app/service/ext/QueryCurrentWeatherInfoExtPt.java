package com.ark.weather.platform.app.service.ext;

import com.ark.weather.platform.api.ext.IQueryWeatherInfoExtPt;
import com.ark.weather.platform.app.common.WeatherExtPointConstant;
import com.ark.weather.platform.app.common.util.CommonUtils;
import com.ark.weather.platform.dto.PositionInfoDTO;
import com.ark.weather.platform.dto.cmd.QueryWeatherInfoCmd;
import com.ark.weather.platform.dto.domainmodel.response.WeatherInfoDTO;
import com.ark.weather.platform.infrastructure.common.ErrorCodeEnum;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherAllInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherInfoDO;
import com.google.common.collect.Lists;
import com.ark.layer.exception.BizException;
import com.ark.layer.extension.Extension;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 彩云天气查询方法中的扩展点实现
 *
 */
@Slf4j
@Service
@Extension(bizId = WeatherExtPointConstant.BIZ_ID_CAI_YUN, useCase = WeatherExtPointConstant.USE_CASE_WEATHER, scenario = WeatherExtPointConstant.SCENARIO_WEATHER_QUERY_CURRENT)
public class QueryCurrentWeatherInfoExtPt extends BaseQueryCaiYunWeatherInfoExtPt implements
    IQueryWeatherInfoExtPt {

    /**
     * 校验参数
     *
     * @param cmd 参数
     */
    @Override
    public void validateCmd(QueryWeatherInfoCmd cmd) {
        super.validateCmd(cmd);
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
        WeatherInfoDO realtime = weatherAllInfoDO.getRealtime();
        if (realtime == null) {
            throw new BizException(ErrorCodeEnum.B_WEATHER_INFO_IS_EMPTY);
        }
        return Lists.newArrayList(CommonUtils.getWeatherInfoDTO(realtime));
    }
}
