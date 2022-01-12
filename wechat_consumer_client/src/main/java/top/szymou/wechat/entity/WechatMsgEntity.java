package top.szymou.wechat.entity;


import java.util.List;

/**
 * @Description: 有颜色的微信消息模板
 * @author: 熟知宇某
 * @date: 2022/1/7 15:42
 * @Blog: https://blog.csdn.net/weixin_43548748
 */

public class WechatMsgEntity {

    /**
     * 接收者的openid
     */
    private String openId;

    /**
     * 接收者的openids
     */
    private List<String> openIds;

    /**
     * 消息模板
     */
    private String msgTemplate;

    /**
     * 点击消息卡片的跳转链接
     */
    private String redirectUrl;

    /**
     * 卡片头部信息
     */
    private String first;

    /**
     * 卡片尾部信息
     */
    private String remark;

    /**
     * 卡片中间的主要信息1（含颜色）
     */
    private List<WechatMsgColorEntity> wechatMsgColorEntities;

    /**
     * 卡片中间的主要信息2(不含颜色)
     */
    private List<String> keywords;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public List<String> getOpenIds() {
        return openIds;
    }

    public void setOpenIds(List<String> openIds) {
        this.openIds = openIds;
    }

    public String getMsgTemplate() {
        return msgTemplate;
    }

    public void setMsgTemplate(String msgTemplate) {
        this.msgTemplate = msgTemplate;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<WechatMsgColorEntity> getMsgValueColorDTOS() {
        return wechatMsgColorEntities;
    }

    public void setMsgValueColorDTOS(List<WechatMsgColorEntity> wechatMsgColorEntities) {
        this.wechatMsgColorEntities = wechatMsgColorEntities;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
