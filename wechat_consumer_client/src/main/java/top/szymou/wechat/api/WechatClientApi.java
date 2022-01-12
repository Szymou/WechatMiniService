package top.szymou.wechat.api;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.szymou.wechat.dto.BaseResult;
import top.szymou.wechat.dto.JssdkConfigDTO;
import top.szymou.wechat.dto.WechatMsgWithColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 解析Feign正常/异常
 * @author: 熟知宇某
 * @date: 2022/1/11 15:02
 * @Blog: https://blog.csdn.net/weixin_43548748
 */
@Component
public class WechatClientApi {
    Logger logger = LoggerFactory.getLogger(WechatClientApi.class);

    @Autowired
    private WechatClientFeign feign;

    /**
     * 获取accesstoken
     * @return
     */
    public String getGlobalAccessToken() {
        BaseResult<String> result = feign.getGlobalAccessToken();

        if (null == result){
            return null;
        }

        if (result.getRes()){
            return result.getData();
        }

        logger.error(JSONObject.toJSONString(result));
        throw new RuntimeException(result.getMsg());
    }


    /**
     * 获取jssdk配置信息
     * @param url
     * @return
     */
    public JssdkConfigDTO getWxJsConfigParam(String url) {
        BaseResult<JssdkConfigDTO> result = feign.getWxJsConfigParam(url);

        if (null == result){
            return null;
        }

        if (result.getRes()) {
            String json = JSONObject.toJSONString(result.getData());
            JssdkConfigDTO dto = JSONObject.parseObject(json, JssdkConfigDTO.class);
            return dto;
        }

        logger.error(JSONObject.toJSONString(result));
        throw new RuntimeException(result.getMsg());
    }


    public boolean pushMsgToOpenId(WechatMsgWithColor v){return pushMsgToOpenId(v, false);}
    public boolean pushMsgToOpenId(WechatMsgWithColor v, boolean sanbox) {
        BaseResult result = feign.pushMsgToOpenId(v, sanbox);

        if (null == result){
            return false;
        }

        if (result.getRes()) {
            return result.getRes();
        }
        if (sanbox){
            logger.error(JSONObject.toJSONString(result));
            throw new RuntimeException(result.getMsg());
        }else {
            return false;
        }

    }

    public boolean pushMsgWithColor(WechatMsgWithColor v){return pushMsgWithColor(v, false);}
    public boolean pushMsgWithColor(WechatMsgWithColor v, boolean sanbox) {
        BaseResult result = feign.pushMsgWithColor(v, sanbox);

        if (null == result){
            return false;
        }

        if (result.getRes()) {
            return result.getRes();
        }

        if (sanbox){
            logger.error(JSONObject.toJSONString(result));
            throw new RuntimeException(result.getMsg());
        }else {
            return false;
        }
    }

    public List<String> pushMsgToOpenIds(WechatMsgWithColor v){return pushMsgToOpenIds(v, false);}
    public List<String> pushMsgToOpenIds(WechatMsgWithColor v, boolean sanbox) {
        BaseResult<List<String>> result = feign.pushMsgToOpenIds(v, sanbox);

        if (null == result){
            return null;
        }

        if (result.getRes()){
            return new ArrayList<>();
        }
        if (sanbox){
            logger.error(JSONObject.toJSONString(result));
            throw new RuntimeException(result.getMsg());
        }else {
            return result.getData();
        }
    }

    public List<String> pushMsgWithColorToOpenIds(WechatMsgWithColor v){return pushMsgWithColorToOpenIds(v, false);}
    public List<String> pushMsgWithColorToOpenIds(WechatMsgWithColor v, boolean sanbox) {
        BaseResult<List<String>> result = feign.pushMsgWithColorToOpenIds(v, sanbox);

        if (null == result){
            return null;
        }

        if (result.getRes()){
            return new ArrayList<>();
        }

        if (sanbox){
            logger.error(JSONObject.toJSONString(result));
            throw new RuntimeException(result.getMsg());
        }else {
            return result.getData();
        }
    }
}
