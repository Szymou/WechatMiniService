package top.szymou.wechat.entity;

import top.szymou.wechat.entity.enums.MsgColorEnum;

/**
 * @Description: 消息文字和颜色
 * @author: 熟知宇某
 * @date: 2022/1/7 15:10
 * @Blog: https://blog.csdn.net/weixin_43548748
 */
public class WechatMsgColorEntity {
    private String value;
    private String color;

    public WechatMsgColorEntity(String value, String color){
        this.value = value;
        this.color = color;
    }

    public WechatMsgColorEntity(String value, MsgColorEnum msgColorEnum){
        this.value = value;
        this.color = msgColorEnum.getCode();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
