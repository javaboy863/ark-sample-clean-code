package com.ark.weather.platform.app.service.ext;

import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.api.ext.IQueryWeatherInfoExtPt;
import com.ark.weather.platform.app.common.WeatherConstant;
import com.ark.weather.platform.app.common.util.CommonUtils;
import com.ark.weather.platform.dto.PositionInfoDTO;
import com.ark.weather.platform.dto.cmd.QueryWeatherInfoCmd;
import com.ark.weather.platform.infrastructure.common.ErrorCodeEnum;
import com.ark.weather.platform.infrastructure.repository.dataobject.CaiYunWeatherAllInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.CityInfoDO;
import com.ark.weather.platform.infrastructure.repository.dataobject.WeatherAllInfoDO;
import com.ark.weather.platform.infrastructure.repository.http.CaiYunWeatherProxy;
import com.ark.layer.exception.BizException;
import com.ark.weather.platform.app.service.CityInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 彩云天气查询方法中的扩展点实现
 *
 */
@Slf4j
public abstract class BaseQueryCaiYunWeatherInfoExtPt implements IQueryWeatherInfoExtPt {
    /**
     * 经度最大值
     */
    protected static final double MAX_LNG = 180;
    /**
     * 经度最小值
     */
    protected static final double MIN_LNG = -180;
    /**
     * 纬度最大值
     */
    protected static final double MAX_LAT = 90;
    /**
     * 纬度最小值
     */
    protected static final double MIN_LAT = -90;
    @Resource
    private CaiYunWeatherProxy caiYunWeatherProxy;
    @Resource
    private CityInfoService cityInfoService;

    /**
     * 校验参数
     *
     * @param cmd 参数
     */
    @Override
    public void validateCmd(QueryWeatherInfoCmd cmd) {
        if (StringUtils.isBlank(cmd.getAppCode())) {
            throw new BizException(ErrorCodeEnum.P_ERR_APP_CODE);
        }

        if (StringUtils.isNotBlank(cmd.getCityId()) || StringUtils.isNotBlank(cmd.getCityName())) {
            CityInfoDO cityInfoDO = cityInfoService.findByCityIdOrCityName(cmd.getCityId(), cmd.getCityName());
            if (cityInfoDO == null) {
                throw new BizException(ErrorCodeEnum.P_ERR_CITY_ERROR);
            }
            return;
        }
        if (cmd.getLat() == null || cmd.getLat() > MAX_LAT || cmd.getLat() < MIN_LAT) {
            throw new BizException(ErrorCodeEnum.P_ERR_LAT);
        }
        if (cmd.getLng() == null || cmd.getLng() > MAX_LNG || cmd.getLng() < MIN_LNG) {
            throw new BizException(ErrorCodeEnum.P_ERR_LNG);
        }
    }

    public void checkStartEndParam(QueryWeatherInfoCmd cmd, Integer minStart, Integer maxEnd) {
        Integer start = cmd.getStart();
        Integer end = cmd.getEnd();
        //check start
        if (start == null) {
            start = 0;
            cmd.setStart(0);
        }
        //check end
        if (end == null) {
            end = 0;
            cmd.setEnd(0);
        }
        //check value
        if (start > end) {
            throw new BizException(ErrorCodeEnum.P_ERR_START);
        }
        //check start border
        if (start < minStart) {
            throw new BizException(ErrorCodeEnum.P_ERR_START);
        }
        //check end border
        if (end > maxEnd) {
            throw new BizException(ErrorCodeEnum.P_ERR_END);
        }
    }

    @Override
    public PositionInfoDTO handleCmd(QueryWeatherInfoCmd cmd) {
        // 根据参数构造位置对象
        return buildPositionInfoDTO(cmd);
    }

    private PositionInfoDTO buildPositionInfoDTO(QueryWeatherInfoCmd cmd) {
        PositionInfoDTO positionInfoDTO = new PositionInfoDTO();
        positionInfoDTO.setStart(cmd.getStart());
        positionInfoDTO.setEnd(cmd.getEnd());
        positionInfoDTO.setLat(cmd.getLat());
        positionInfoDTO.setLng(cmd.getLng());
        if (StringUtils.isBlank(cmd.getCityId()) && StringUtils.isBlank(cmd.getCityName())) {
            return positionInfoDTO;
        }

        CityInfoDO cityInfoDO = cityInfoService.findByCityIdOrCityName(cmd.getCityId(), cmd.getCityName());
        if (cityInfoDO == null) {
            return positionInfoDTO;
        }
        positionInfoDTO.setCityId(cityInfoDO.getCityId());
        positionInfoDTO.setLat(cityInfoDO.getLat().doubleValue());
        positionInfoDTO.setLng(cityInfoDO.getLng().doubleValue());
        return positionInfoDTO;
    }

    /**
     * 调用彩云天气服务，转换为通用天气领域对象返回
     *
     * @param positionInfoDTO
     * @return
     */
    protected WeatherAllInfoDO callCaiYunRemote(PositionInfoDTO positionInfoDTO) {
        // 获取天气信息
        CaiYunWeatherAllInfoDO caiYunWeatherAllInfo = caiYunWeatherProxy.getCaiYunWeatherAllInfo(positionInfoDTO.getLat(), positionInfoDTO.getLng());
        if (caiYunWeatherAllInfo == null
                || !WeatherConstant.CAI_YUN_SUCC_STATUS.equals(caiYunWeatherAllInfo.getStatus())
                || caiYunWeatherAllInfo.getResult() == null) {
            log.error("彩云天气服务查询失败。param:{}, result:{}", JSON.toJSONString(positionInfoDTO), JSON.toJSONString(caiYunWeatherAllInfo));
            throw new BizException(ErrorCodeEnum.B_WEATHER_INFO_QUERY_FAIL);
        }
        Long serverTime = caiYunWeatherAllInfo.getServer_time();
        Date obsTime = new Date(serverTime * 1000L);
        // 转换对象
        return CommonUtils.getWeatherAllInfoDO(caiYunWeatherAllInfo.getResult(), obsTime);
    }


}
