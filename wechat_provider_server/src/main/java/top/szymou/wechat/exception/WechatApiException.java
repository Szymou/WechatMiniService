package top.szymou.wechat.exception;

import cn.hutool.core.util.StrUtil;
import top.szymou.wechat.entity.enums.WechatErrorEnum;
import lombok.Getter;

/**
 * @author 熟知宇某
 * @date 2021/6/18 17:40
 */
public class WechatApiException extends RuntimeException{

    @Getter
    protected WechatErrorEnum wechatErrorEnum;

    public WechatApiException(){
        this(WechatErrorEnum.ERROR);
    }

    public WechatApiException(WechatErrorEnum wechatErrorEnum){
        this(wechatErrorEnum, null);
    }


    public WechatApiException(String msg){
        this(WechatErrorEnum.USUAL_ERROR, msg);
    }
    public WechatApiException(WechatErrorEnum wechatErrorEnum, String msg){
        super(msg);
        this.wechatErrorEnum = wechatErrorEnum;
    }

    @Override
    public String getMessage() {
        String result = null;
        result = "code:" + wechatErrorEnum.getErrcode() + ", msg:" + wechatErrorEnum.getDesc();
        if(StrUtil.isNotBlank(super.getMessage())) {
            result = result + "," + super.getMessage();
        }
        return result;
    }
}
