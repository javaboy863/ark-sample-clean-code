package com.ark.weather.platform.infrastructure.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.ark.layer.dto.IErrorCode;
import com.ark.layer.exception.BizException;
import com.ark.layer.exception.BusinessException;
import com.ark.layer.exception.SysException;
import com.ark.layer.exception.framework.BasicErrorCode;
import com.ark.layer.exception.framework.LayerException;
import lombok.extern.slf4j.Slf4j;

/**
 * 通用统一异常处理
 */
@Activate(group = {Constants.PROVIDER},order = -1)
@Slf4j
public class DubboExceptionFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;
        try {
            result = invoker.invoke(invocation);
            if (result.hasException()) {
                Throwable throwable = result.getException();
                if (throwable instanceof BizException) {
                    IErrorCode errCode = ((BizException) throwable).getErrCode();
                    log.warn("BizException:{}" , result,throwable);
                    return new RpcResult(com.ark.layer.dto.Result.wrapError(errCode.getErrCode(),errCode.getErrDesc()));
                }else if (throwable instanceof BusinessException) {
                    BusinessException businessException = (BusinessException) throwable;
                    IErrorCode errCode = businessException.getErrCode();
                    log.warn("BusinessException:{}" , result,throwable);
                    return new RpcResult(com.ark.layer.dto.Result.wrapError(errCode.getErrCode(),businessException.getMessage()));
                }else if (throwable instanceof SysException) {
                    IErrorCode errCode = ((SysException) throwable).getErrCode();
                    log.error("SysException:{}" , result,throwable);
                    return new RpcResult(com.ark.layer.dto.Result.wrapError(errCode.getErrCode(),errCode.getErrDesc()));
                }else if (throwable instanceof LayerException) {
                    IErrorCode errCode = ((LayerException) throwable).getErrCode();
                    log.error("LayerException:{}" , result,throwable);
                    return new RpcResult(com.ark.layer.dto.Result.wrapError(errCode.getErrCode(),errCode.getErrDesc()));
                }else {
                    log.error("unknowException:{}" , result,throwable);
                    return new RpcResult(com.ark.layer.dto.Result.wrapError(BasicErrorCode.SYS_ERROR.getErrCode(),throwable.getMessage()));
                }
            }
        } catch (Exception e) {
            log.error("dubbo调用过程异常失败:", e);
        }
        return result;
    }

}