package top.szymou.wechat.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.szymou.wechat.entity.dto.BaseResult;

import java.util.Optional;

/**
 * @Description: 捕获全局异常
 * @author: 熟知宇某
 * @date: 2022/1/7 18:01
 * @Blog: https://blog.csdn.net/weixin_43548748
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionCatchAdvice {

    @ExceptionHandler(WechatApiException.class)
    public BaseResult<?> handleWechatApiException(WechatApiException e){
        String msg = Optional.ofNullable(e.getMessage()).orElse("");
        log.error(msg);
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(e.getWechatErrorEnum().getErrcode());
        baseResult.setMsg(msg);
        baseResult.setRes(false);
        return baseResult;
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResult<?> handleRuntimeException(RuntimeException e){
        String msg = Optional.ofNullable(e.getMessage()).orElse("");
        log.error(msg);
        BaseResult baseResult = new BaseResult();
        return baseResult.error("操作失败：" + msg);
    }

    @ExceptionHandler(Exception.class)
    public BaseResult<?> handleException(Exception e){
        String msg = Optional.ofNullable(e.getMessage()).orElse("");
        log.error(msg);
        BaseResult baseResult = new BaseResult();
        return baseResult.error("操作失败：" + msg);
    }




}
