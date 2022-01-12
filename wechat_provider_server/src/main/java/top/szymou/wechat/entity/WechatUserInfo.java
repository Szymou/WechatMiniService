package top.szymou.wechat.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 熟知宇某
 * @date 2021/6/17 11:00
 */
@Data
public class WechatUserInfo {
    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private List<String> privilege;
    private String unionid;
}
