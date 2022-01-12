package top.szymou.wechat.api;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.szymou.wechat.api.feign.WechatClientFeign;
import top.szymou.wechat.entity.WechatMsgEntity;
import top.szymou.wechat.entity.defalut.BaseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> getWxJsConfigParam(String url) {
        BaseResult<Map<String, Object>> result = feign.getWxJsConfigParam(url);

        if (null == result){
            return null;
        }

        if (result.getRes()) {
            Map<String, Object> map = result.getData();
            return map;
        }

        logger.error(JSONObject.toJSONString(result));
        throw new RuntimeException(result.getMsg());
    }


    public boolean pushMsgToOpenId(WechatMsgEntity v){return pushMsgToOpenId(v, false);}
    public boolean pushMsgToOpenId(WechatMsgEntity v, boolean sanbox) {
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

    public boolean pushMsgWithColor(WechatMsgEntity v){return pushMsgWithColor(v, false);}
    public boolean pushMsgWithColor(WechatMsgEntity v, boolean sanbox) {
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

    public List<String> pushMsgToOpenIds(WechatMsgEntity v){return pushMsgToOpenIds(v, false);}
    public List<String> pushMsgToOpenIds(WechatMsgEntity v, boolean sanbox) {
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

    public List<String> pushMsgWithColorToOpenIds(WechatMsgEntity v){return pushMsgWithColorToOpenIds(v, false);}
    public List<String> pushMsgWithColorToOpenIds(WechatMsgEntity v, boolean sanbox) {
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
