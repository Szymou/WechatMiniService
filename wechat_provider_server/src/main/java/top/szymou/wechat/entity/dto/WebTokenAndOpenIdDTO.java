package top.szymou.wechat.entity.dto;

import lombok.Data;

/**
 * @author 熟知宇某
 * @date 2021/6/19 9:49
 */
@Data
public class WebTokenAndOpenIdDTO {
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
}
