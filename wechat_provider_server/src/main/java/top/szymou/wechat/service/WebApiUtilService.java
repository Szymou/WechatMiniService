package top.szymou.wechat.service;


import top.szymou.wechat.entity.WechatUserInfo;

/**
 * @author 熟知宇某
 * @date 2021/6/17 10:42
 * 说明：网页接口；主要是先获取code，再使用code获取网页授权access_token(无数次)
 *
 * 注意：通过网页授权获得的access_token，只能获取到对应的微信用户信息，与微信用户是一对一关系；而普通的access_token在有效期内可以使用，可以获取所有用户信息，并使用微信公众号提供的接口。
 */
public interface WebApiUtilService {

    /**
     * 第一步：用户同意授权，获取code
     */
    String getCodeAndRedirect(String params);

    /**
     * 第二步：通过code换取网页授权access_token：把access_token放redis，第三步有需要的话还能直接去拿需要的数据
     */
    String getAccessTokenByCode(String code);


    /**
     * 第三步：刷新access_token（如果需要）:由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，refresh_token有效期为30天，当refresh_token失效之后，需要用户重新授权。
     * 目的是为了在access_token过期的时候，用户不需要再次点击授权按钮
     */
    String refreshAccessToken(String refreshToken);


    /**
     * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
     */
    WechatUserInfo getUserInfo(String accessToken, String openId);


    /**
     * 检验授权凭证（access_token）是否有效
     */
    boolean checkAccessTokenOfOpenIdValid(String accessToken, String openId);

}
