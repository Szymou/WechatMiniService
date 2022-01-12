package top.szymou.wechat.dto;

import java.io.Serializable;

/**
 * @Description: jssdk配置信息实体类
 * @author: 熟知宇某
 * @date: 2022/1/11 15:08
 * @Blog: https://blog.csdn.net/weixin_43548748
 */

public class JssdkConfigDTO implements Serializable {

    private static final long serialVersionUID = 1047697114L;

    private String signature;

    private String appId;

    private String nonceStr;

    private String timestamp;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
