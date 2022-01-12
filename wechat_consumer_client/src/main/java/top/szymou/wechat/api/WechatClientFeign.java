package top.szymou.wechat.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import top.szymou.wechat.dto.BaseResult;
import top.szymou.wechat.dto.WechatMsgWithColor;


/**
 * @Description: 微信中转数据服务--消费者
 * @author: 熟知宇某
 * @date: 2022/1/5 16:03
 * @Blog: https://blog.csdn.net/weixin_43548748
 */
@Primary
@Service
@FeignClient(
        name = "wechatbyszymou",
        url = "${wechat.server.ip:127.0.0.1}:${wechat.server.port:7570}/${wechat.server.base-path:wechatProvider}",
        fallbackFactory = TestServiceFallback.class
)
public interface WechatClientFeign {

    /**
     * 获取全局accesstoken：可用于调用微信接口
     * @return
     */
    @RequestMapping(value = "getGlobalAccessToken", method = RequestMethod.GET)
    BaseResult getGlobalAccessToken();

    /**
     * 获取jssdk配置信息
     * @param url
     * @return
     *      {
     *         "signature": "4bdcb624933f5b9c1a00fa6c3868a044a1341f45",
     *         "appId": "wx8060f494f5ab2a6f",
     *         "nonceStr": "7dd05e35f74e441e",
     *         "timestamp": "1625639243"
     *      }
     */
    @RequestMapping(value = "getWxJsConfigParam", method = RequestMethod.GET)
    BaseResult getWxJsConfigParam(@RequestParam("url") String url);

    @PostMapping("pushMsgToOpenId")
    BaseResult pushMsgToOpenId(@RequestBody WechatMsgWithColor v, @RequestParam("sanbox") boolean sanbox);

    @PostMapping("pushMsgWithColor")
    BaseResult pushMsgWithColor(@RequestBody WechatMsgWithColor v, @RequestParam("sanbox") boolean sanbox);

    @PostMapping("pushMsgToOpenIds")
    BaseResult pushMsgToOpenIds(@RequestBody WechatMsgWithColor v, @RequestParam("sanbox") boolean sanbox);

    @PostMapping("pushMsgWithColorToOpenIds")
    BaseResult pushMsgWithColorToOpenIds(@RequestBody WechatMsgWithColor v, @RequestParam("sanbox") boolean sanbox);
}
