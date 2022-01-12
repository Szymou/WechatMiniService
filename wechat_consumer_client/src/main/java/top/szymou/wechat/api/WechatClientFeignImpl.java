package top.szymou.wechat.api;

import org.springframework.stereotype.Component;
import top.szymou.wechat.dto.BaseResult;
import top.szymou.wechat.dto.WechatMsgWithColor;


/**
 * @Description: 降级
 * @author: 熟知宇某
 * @date: 2022/1/6 11:47
 * @Blog: https://blog.csdn.net/weixin_43548748
 */
@Component
public class WechatClientFeignImpl implements WechatClientFeign {


    public static void main(String[] args) {
        System.out.println("欢迎使用微信中间数据插件。");
    }


    @Override
    public BaseResult<?> getGlobalAccessToken() {
        return null;
    }

    @Override
    public BaseResult getWxJsConfigParam(String url) {
        return null;
    }

    @Override
    public BaseResult pushMsgToOpenId(WechatMsgWithColor v, boolean sanbox) {
        return null;
    }

    @Override
    public BaseResult pushMsgWithColor(WechatMsgWithColor v, boolean sanbox) {
        return null;
    }

    @Override
    public BaseResult pushMsgToOpenIds(WechatMsgWithColor v, boolean sanbox) {
        return null;
    }

    @Override
    public BaseResult pushMsgWithColorToOpenIds(WechatMsgWithColor v, boolean sanbox) {
        return null;
    }

}
