package com.ark.weather.platform.infrastructure.repository.rpc;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ark.basic.base.AppKeyModel;
import com.ark.basic.station.api.MidWarehouseCascadeRpcService;
import com.ark.basic.station.model.enums.StationEnum;
import com.ark.basic.station.model.response.MidBaseStationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 微仓服务代理类
 *
 */
@Slf4j
@Component
public class WarehouseProxy {
    @Value("${project.name}")
    private String appCode;
    /**
     * 远程微仓服务类
     */
    @Resource
    private MidWarehouseCascadeRpcService midWarehouseCascadeRpcService;

    /**
     * 获取所有开站状态下的微仓数据
     *
     * @return
     */
    public List<MidBaseStationDTO> listAllOpenStation() {
        AppKeyModel appKeyModel = new AppKeyModel();
        appKeyModel.setFromApp(appCode);
        try {
            List<MidBaseStationDTO> midBaseStationDTOS = midWarehouseCascadeRpcService.listStationByCondition(appKeyModel, null, null,
                    StationEnum.StationSateEnum.OPEN.getState(), null);
            return midBaseStationDTOS;
        } catch (Exception ex) {
            log.error("获取所有开站门店数据失败", ex);
        }
        return Lists.newArrayListWithExpectedSize(0);
    }
}
