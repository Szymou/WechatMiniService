package top.szymou.wechat.api.feign;

import org.springframework.stereotype.Component;
import top.szymou.wechat.entity.defalut.BaseResult;
import top.szymou.wechat.entity.WechatMsgEntity;


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
    public BaseResult pushMsgToOpenId(WechatMsgEntity v, boolean sanbox) {
        return null;
    }

    @Override
    public BaseResult pushMsgWithColor(WechatMsgEntity v, boolean sanbox) {
        return null;
    }

    @Override
    public BaseResult pushMsgToOpenIds(WechatMsgEntity v, boolean sanbox) {
        return null;
    }

    @Override
    public BaseResult pushMsgWithColorToOpenIds(WechatMsgEntity v, boolean sanbox) {
        return null;
    }

}
