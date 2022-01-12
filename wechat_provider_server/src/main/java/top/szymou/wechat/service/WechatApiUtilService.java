package top.szymou.wechat.service;


import top.szymou.wechat.entity.dto.WechatJssdkEntity;
import top.szymou.wechat.entity.dto.WechatMsgDTO;

/**
 * @author 熟知宇某
 * @date 2021/6/17 10:41
 * 【微信接口】指的是推送消息、获取ip、上传图片、地图等接口
 * 说明：微信接口；使用【微信接口】主要是使用普通access_token，即全局access_token（有上限，一天2000条）
 */
public interface WechatApiUtilService {

    /**
     * 获取普通access_token（全局）
     * @return
     */
    String getClobalAccessToken();


    /**
     * 推送消息     默认非沙箱模式，如果推送成功失败，不返回错误信息，主要用于生产环境
     * @param dto
     * @return
     */
    boolean pushMsg(WechatMsgDTO dto);

    /**
     * 推送消息   可选沙箱，沙箱模式（sanbox=true），可以返回错误信息，主要用于调试
     * @param dto
     * @param sanbox
     * @return
     */
    boolean pushMsg(WechatMsgDTO dto, boolean sanbox);


    /**
     * 获取js-sdk参数
     * @param url
     * @return
     */
    WechatJssdkEntity getWxJsConfigParam(String url);
}
