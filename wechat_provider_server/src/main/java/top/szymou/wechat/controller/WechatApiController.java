package top.szymou.wechat.controller;

import org.springframework.web.bind.annotation.*;
import top.szymou.wechat.api.WechatApi;
import org.springframework.beans.factory.annotation.Autowired;
import top.szymou.wechat.entity.dto.BaseResult;
import top.szymou.wechat.entity.dto.JssdkConfigDTO;
import top.szymou.wechat.entity.params.WechatMsgWithColor;

import java.util.List;

/**
 * @Description: wechatApi interface
 * @author: 熟知宇某
 * @date: 2021/12/27 17:05
 * @Blog: https://blog.csdn.net/weixin_43548748
 */
@RestController
@RequestMapping("wechatProvider")
public class WechatApiController {

    @Autowired
    private WechatApi wechatApi;

    /**
     * 获取全局accessToken
     * @return
     */
    @GetMapping("getGlobalAccessToken")
    public BaseResult<?> getGlobalAccessToken(){
        String accesstoken = wechatApi.getGlobalAccessToken();
        return BaseResult.OK(accesstoken);
    }

    @GetMapping("getWxJsConfigParam")
    public BaseResult<?> getWxJsConfigParam(String url){
        JssdkConfigDTO jssdkConfigDTO = wechatApi.getWxJsConfigParam(url);
        return BaseResult.OK(jssdkConfigDTO);
    }

    @PostMapping("pushMsgToOpenId")
    public BaseResult<?> pushMsgToOpenId(@RequestBody WechatMsgWithColor v, boolean sanbox){
        boolean b = wechatApi.pushMsg(
                sanbox,
                v.getOpenId(),
                v.getMsgTemplate(),
                v.getRedirectUrl(),
                v.getFirst(),
                v.getRemark(),
                v.getKeywords()
                );

        if (b){
            return BaseResult.OK();
        }else {
            return BaseResult.error500();
        }
    }

    @PostMapping("pushMsgWithColor")
    public BaseResult<?> pushMsgWithColor(@RequestBody WechatMsgWithColor v, boolean sanbox){
        boolean b = wechatApi.pushMsgWithColor(
                sanbox,
                v.getOpenId(),
                v.getMsgTemplate(),
                v.getRedirectUrl(),
                v.getFirst(),
                v.getRemark(),
                v.getMsgValueColorDTOS()
        );

        if (b){
            return BaseResult.OK();
        }else {
            return BaseResult.error500();
        }
    }

    @PostMapping("pushMsgToOpenIds")
    public BaseResult<?> pushMsgToOpenIds(@RequestBody WechatMsgWithColor v, boolean sanbox){
        List<String> failedOpenIds = wechatApi.pushMsgToOpenIds(
                sanbox,
                v.getOpenIds(),
                v.getMsgTemplate(),
                v.getRedirectUrl(),
                v.getFirst(),
                v.getRemark(),
                v.getKeywords()
        );

        if (null != failedOpenIds && failedOpenIds.size() == 0){
            return BaseResult.OK();
        }

        return BaseResult.error500(failedOpenIds);
    }

    @PostMapping("pushMsgWithColorToOpenIds")
    public BaseResult<?> pushMsgWithColorToOpenIds(@RequestBody WechatMsgWithColor v, boolean sanbox){
        List<String> failedOpenIds = wechatApi.pushMsgWithColorToOpenIds(
                sanbox,
                v.getOpenIds(),
                v.getMsgTemplate(),
                v.getRedirectUrl(),
                v.getFirst(),
                v.getRemark(),
                v.getMsgValueColorDTOS()
        );

        if (null != failedOpenIds && failedOpenIds.size() == 0){
            return BaseResult.OK();
        }

        return BaseResult.error500(failedOpenIds);
    }

}
