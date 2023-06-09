package com.ark.weather.platform.app.command.query;

import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.api.ext.IQueryWeatherInfoExtPt;
import com.ark.weather.platform.app.common.util.CommonUtils;
import com.ark.weather.platform.dto.PositionInfoDTO;
import com.ark.weather.platform.dto.cmd.QueryWeatherInfoCmd;
import com.ark.weather.platform.dto.domainmodel.response.WeatherInfoDTO;
import com.ark.layer.dto.Result;
import com.ark.layer.extension.BizScenario;
import com.ark.layer.extension.ExtensionExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 天气查询服务执行器
 *
 */
@Slf4j
@Component
public class QueryWeatherInfoExe {
    @Resource
    private ExtensionExecutor extensionExecutor;

    /**
     * 查询天气信息
     *
     * @param cmd 执行命令
     * @return
     */
    public Result<List<WeatherInfoDTO>> query(QueryWeatherInfoCmd cmd) {
        // 场景
        BizScenario bizScenario = CommonUtils.defaultIfNull(cmd.getBizScenario(), BizScenario.newDefault());
        // 验证参数
        extensionExecutor.executeVoid(IQueryWeatherInfoExtPt.class, bizScenario, e -> e.validateCmd(cmd));
        // 处理请求参数
        PositionInfoDTO positionInfoDTO = extensionExecutor.execute(IQueryWeatherInfoExtPt.class, bizScenario, e -> e.handleCmd(cmd));
        // 调用远程服务获取天气信息
        List<WeatherInfoDTO> weatherInfoList = extensionExecutor.execute(IQueryWeatherInfoExtPt.class, bizScenario, e -> e.callRemote(positionInfoDTO));
        log.info("查询天气参数: {} , 结果: {}", JSON.toJSONString(cmd), JSON.toJSON(weatherInfoList));
        // 返回
        return Result.wrapSuccess(weatherInfoList);
    }
}
