package com.ark.weather.platform.app.command.query;

import com.ark.weather.platform.infrastructure.repository.rpc.WarehouseProxy;
import com.ark.basic.station.model.response.MidBaseStationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询门店信息命令执行器
 *
 */
@Component
@Slf4j
public class QueryStationInfoExe {
    @Resource
    private WarehouseProxy warehouseProxy;

    /**
     * 获取所有开站状态的门店
     */
    public List<MidBaseStationDTO> getAllOpenStation() {
        return warehouseProxy.listAllOpenStation();
    }
}
