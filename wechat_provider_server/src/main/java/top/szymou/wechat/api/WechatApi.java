package top.szymou.wechat.api;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import top.szymou.wechat.entity.WechatUserInfo;
import top.szymou.wechat.entity.dto.WechatJssdkEntity;
import top.szymou.wechat.entity.dto.MsgValueColorDTO;
import top.szymou.wechat.entity.dto.WebTokenAndOpenIdDTO;
import top.szymou.wechat.entity.dto.WechatMsgDTO;
import top.szymou.wechat.entity.enums.WechatErrorEnum;
import top.szymou.wechat.exception.WechatApiException;
import top.szymou.wechat.service.WebApiUtilService;
import top.szymou.wechat.service.WechatApiUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 熟知宇某
 * @date 2021/6/18 15:56
 */
@Slf4j
@Component
public class WechatApi {

    @Autowired
    private WebApiUtilService webApiUtilService;

    @Autowired
    private WechatApiUtilService wechatApiUtilService;

    /**
     * 消息推送1.0：推送单个openId
     * @param openid
     * @param msgTemplate
     * @param url
     * @param first
     * @param remark
     * @param keywords
     * @return
     */
    public boolean pushMsg(String openid, String msgTemplate, String url, String first, String remark, List<String> keywords){
        return pushMsg(false, openid, msgTemplate, url, first, remark, keywords);
    }
    public boolean pushMsg(boolean sanbox, String openid, String msgTemplate, String url, String first, String remark, List<String> keywords){
        Assert.notEmpty(openid, "openid(用户openId)不允许为空");
        Assert.notEmpty(msgTemplate, "msgTemplate(消息模板)不允许为空");
        Assert.notEmpty(keywords, "msgValueColorDTOS(消息)不允许为空");

        WechatMsgDTO wechatMsgDTO = new WechatMsgDTO();
        wechatMsgDTO.setTouser(openid);
        wechatMsgDTO.setTemplate_id(msgTemplate);
        wechatMsgDTO.setUrl(url);

        wechatMsgDTO.defindContent(first, remark, keywords);
        return wechatApiUtilService.pushMsg(wechatMsgDTO, sanbox);
    }

    /**
     * 消息推送1.1：推送单个openId，消息带颜色
     * @param openid
     * @param msgTemplate
     * @param url
     * @param first
     * @param remark
     * @param msgValueColorDTOS
     * @return
     */
    public boolean pushMsgWithColor(String openid, String msgTemplate, String url, String first, String remark, List<MsgValueColorDTO> msgValueColorDTOS){
        return pushMsgWithColor(false, openid, msgTemplate, url, first, remark, msgValueColorDTOS);
    }
    public boolean pushMsgWithColor(boolean sanbox, String openid, String msgTemplate, String url, String first, String remark, List<MsgValueColorDTO> msgValueColorDTOS){
        Assert.notEmpty(openid, "openid(用户openId)不允许为空");
        Assert.notEmpty(msgTemplate, "msgTemplate(消息模板)不允许为空");
        Assert.notEmpty(msgValueColorDTOS, "msgValueColorDTOS(消息)不允许为空");

        WechatMsgDTO wechatMsgDTO = new WechatMsgDTO();
        wechatMsgDTO.setTouser(openid);
        wechatMsgDTO.setTemplate_id(msgTemplate);
        wechatMsgDTO.setUrl(url);

        wechatMsgDTO.defindContentWithColor(first, remark, msgValueColorDTOS);
        return wechatApiUtilService.pushMsg(wechatMsgDTO, sanbox);
    }


    /**
     * 推送消息2.0：推送多个openIds
     * @param openids
     * @param msgTemplate
     * @param url
     * @param first
     * @param remark
     * @param keywords
     * @return failedOpenIds 推送失败的openIds
     */
    public List<String> pushMsgToOpenIds(List<String> openids, String msgTemplate, String url, String first, String remark, List<String> keywords){
        return pushMsgToOpenIds(false, openids, msgTemplate, url, first, remark, keywords);
    }
    public List<String> pushMsgToOpenIds(boolean sanbox, List<String> openids, String msgTemplate, String url, String first, String remark, List<String> keywords){
        Assert.notEmpty(openids, "openids(用户openIds)不允许为空");
        Assert.notEmpty(msgTemplate, "msgTemplate(消息模板)不允许为空");
        Assert.notEmpty(keywords, "msgValueColorDTOS(消息)不允许为空");
        List<String> failedOpenIds = new ArrayList<>();
        for (String openid : openids) {
            if (StrUtil.isEmpty(openid)){ continue; }
            if (this.pushMsg(sanbox, openid, msgTemplate, url, first, remark, keywords)){
                log.info("《《推送一个消息》》{}", openid);
            }else {
                failedOpenIds.add(openid);
            }
        }
        return failedOpenIds;
    }


    /**
     * 推送消息2.1：推送多个openIds，消息带颜色
     * @param openids
     * @param msgTemplate
     * @param url
     * @param first
     * @param remark
     * @param msgValueColorDTOS
     * @return failedOpenIds 推送失败的openIds
     */
    public List<String> pushMsgWithColorToOpenIds(List<String> openids, String msgTemplate, String url, String first, String remark, List<MsgValueColorDTO> msgValueColorDTOS){
        return pushMsgWithColorToOpenIds(false, openids, msgTemplate, url, first, remark, msgValueColorDTOS);
    }
    public List<String> pushMsgWithColorToOpenIds(boolean sanbox, List<String> openids, String msgTemplate, String url, String first, String remark, List<MsgValueColorDTO> msgValueColorDTOS){
        Assert.notEmpty(openids, "openids(用户openIds)不允许为空");
        Assert.notEmpty(msgTemplate, "msgTemplate(消息模板)不允许为空");
        Assert.notEmpty(msgValueColorDTOS, "msgValueColorDTOS(消息)不允许为空");
        List<String> failedOpenIds = new ArrayList<>();
        for (String openid : openids) {
            if (StrUtil.isEmpty(openid)){ continue; }
            if (this.pushMsgWithColor(sanbox, openid, msgTemplate, url, first, remark, msgValueColorDTOS)){
                log.info("《《推送一个消息》》{}", openid);
            }else {
                failedOpenIds.add(openid);
            }
        }
        return failedOpenIds;
    }

    /**
     * 获取网页授权access_token用和户openID
     * @param request
     * @param response
     * @return
     */
    public WebTokenAndOpenIdDTO getWebAccessTokenAndOpenId(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        String params = request.getQueryString();
        if (StrUtil.isEmpty(code)){
            try {
                response.sendRedirect(webApiUtilService.getCodeAndRedirect(params));
            } finally {
                throw new WechatApiException(WechatErrorEnum.ERROR_SELF);
//                return new WebTokenAndOpenIdDTO();
            }
        }
        String res = webApiUtilService.getAccessTokenByCode(code);
        WebTokenAndOpenIdDTO webTokenAndOpenIdDTO = JSONObject.parseObject(res, WebTokenAndOpenIdDTO.class);
        return webTokenAndOpenIdDTO;
    }

    /**
     * 获取用户openId
     * @param request
     * @return
     */
    public String getOpenId(HttpServletRequest request, HttpServletResponse response){
        WebTokenAndOpenIdDTO webTokenAndOpenIdDTO = getWebAccessTokenAndOpenId(request, response);
        String openId = webTokenAndOpenIdDTO.getOpenid();
        return openId;
    }

    /**
     * 获取用户基本信息
     * @param request
     * @return
     */
    public WechatUserInfo getUserInfo(HttpServletRequest request, HttpServletResponse response){
        WebTokenAndOpenIdDTO webTokenAndOpenIdDTO = getWebAccessTokenAndOpenId(request, response);
        String openId = webTokenAndOpenIdDTO.getOpenid();
        String accessToken = webTokenAndOpenIdDTO.getAccess_token();
        if (null != openId && null != accessToken){
            WechatUserInfo wechatUserInfo = webApiUtilService.getUserInfo(accessToken, openId);
            return wechatUserInfo;
        }
        return null;
    }

    /**
     * 获取全局AccessToken
     * @return
     */
    public String getGlobalAccessToken(){
        return wechatApiUtilService.getClobalAccessToken();
    }

    /**
     * 微信 js-sdk接口所需配置参数
     * @param url
     * @return
     *{
     *   "signature": "4bdcb624933f5b9c1a00fa6c3868a044a1341f45",
     *   "appId": "wx8060f494f5ab2a6f",
     *   "nonceStr": "7dd05e35f74e441e",
     *   "timestamp": "1625639243"
     * }
     */
    public WechatJssdkEntity getWxJsConfigParam(String url){
        log.info("获取jssdk的URL：{}" , url);
        return wechatApiUtilService.getWxJsConfigParam(url);
    }


}
