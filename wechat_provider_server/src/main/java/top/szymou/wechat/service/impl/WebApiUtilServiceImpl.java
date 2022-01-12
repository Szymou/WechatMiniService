package top.szymou.wechat.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import top.szymou.wechat.entity.WechatUserInfo;
import top.szymou.wechat.exception.WechatApiException;
import top.szymou.wechat.service.WebApiUtilService;
import top.szymou.wechat.entity.enums.WechatErrorEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 熟知宇某
 * @date 2021/6/17 11:04
 */
@Service
public class WebApiUtilServiceImpl implements WebApiUtilService {

//    @Value("${domain}")
    private String domain = "暂时未使用到";

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.appsecret}")
    private String appsecret;

//    @Value("${wechat.web_api.getWebApiCodeURL}")
    private String getWebApiCodeURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

//    @Value("${wechat.web_api.getAccessTokenByCodeURL}")
    private String getAccessTokenByCodeURL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

//    @Value("${wechat.web_api.refreshAccessTokenURL}")
    private String refreshAccessTokenURL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

//    @Value("${wechat.web_api.getUserInfoURL}")
    private String getUserInfoURL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

//    @Value("${wechat.web_api.checkAccessTokenValidURL}")
    private String checkAccessTokenValidURL = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";


    /**
     * 获取code并重定向
     * @return 携带code重定向至原接口：REDIRECT_URI/?code=CODE&state=STATE。
     */
    @Override
    public String getCodeAndRedirect(String params) {
        String URL = getWebApiCodeURL;
        URL = URL.replace("APPID", appid);
        if (StrUtil.isEmpty(params)){
            URL = URL.replace("REDIRECT_URI", getCurrentUrl());
        }else {
            URL = URL.replace("REDIRECT_URI", getCurrentUrl() + "?" + params);
        }
        URL = URL.replace("SCOPE", "snsapi_base");//snsapi_base是静默只获取openID，snsapi_userinfo是需要用户点击授权，才能获取用户信息
        URL = URL.replace("STATE", "");
        return URL;
    }

    /**
     * 获取openID和access_token
     * @param code
     * @return res
     * {
     *   "access_token":"ACCESS_TOKEN",
     *   "expires_in":7200,
     *   "refresh_token":"REFRESH_TOKEN",
     *   "openid":"OPENID",
     *   "scope":"SCOPE"
     * }
     */
    @Override
    public String getAccessTokenByCode(String code) {
        Assert.notEmpty(code, "code为空，请检查");
        String URL = getAccessTokenByCodeURL;
        URL = URL.replace("APPID", appid);
        URL = URL.replace("SECRET", appsecret);
        URL = URL.replace("CODE", code);
        String res = HttpUtil.get(URL, 3 * 1000);
        JSONObject json = JSONObject.parseObject(res);
        //校验是否失败
        if (json.containsKey("errcode")){
            String errcode = json.getString("errcode");
            throw new WechatApiException(WechatErrorEnum.getByErrcode(Integer.parseInt(errcode)));
        }
        return res;
    }

    /**
     * 刷新access_token
     * @param refreshToken
     * @return res
     * {
     *   "access_token":"ACCESS_TOKEN",
     *   "expires_in":7200,
     *   "refresh_token":"REFRESH_TOKEN",
     *   "openid":"OPENID",
     *   "scope":"SCOPE"
     * }
     */
    @Override
    public String refreshAccessToken(String refreshToken) {
        Assert.notEmpty(refreshToken, "refreshToken为空，请检查");
        /**
         * todo 在缓存里面获取refreshToken也行，前提是第二步骤的时候开发者是否已经缓存
         */
        String URL = refreshAccessTokenURL;
        URL = URL.replace("APPID", appid);
        URL = URL.replace("REFRESH_TOKEN", refreshToken);
        String res = HttpUtil.get(URL, 3 * 1000);
        JSONObject json = JSONObject.parseObject(res);
        //校验是否失败
        if (json.containsKey("errcode")){
            String errcode = json.getString("errcode");
            throw new WechatApiException(WechatErrorEnum.getByErrcode(Integer.parseInt(errcode)));
        }
        /**
         * todo 缓存操作，开发者自定义。res拿到access_token进行缓存
         */
        return res;
    }

    /**
     * 获取用户信息
     * @param accessToken
     * @param openId
     * @return res
     * {
     *   "openid": "OPENID",
     *   "nickname": NICKNAME,
     *   "sex": 1,
     *   "province":"PROVINCE",
     *   "city":"CITY",
     *   "country":"COUNTRY",
     *   "headimgurl":"https://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
     *   "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
     *   "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }
     */
    @Override
    public WechatUserInfo getUserInfo(String accessToken, String openId) {
        Assert.notEmpty(accessToken, "accessToken为空，请检查");
        Assert.notEmpty(openId, "openId为空，请检查");
        String URL = getUserInfoURL;
        URL = URL.replace("ACCESS_TOKEN", accessToken);
        URL = URL.replace("OPENID", openId);
        String res = HttpUtil.get(URL, 3 * 1000);
        JSONObject json = JSONObject.parseObject(res);
        //校验是否失败
        if (json.containsKey("errcode")){
            String errcode = json.getString("errcode");
            throw new WechatApiException(WechatErrorEnum.getByErrcode(Integer.parseInt(errcode)));
        }

        WechatUserInfo wechatUserInfo = JSONObject.parseObject(json.toJSONString(), WechatUserInfo.class);
        return wechatUserInfo;
    }

    /**
     *
     * @param accessToken
     * @param openId
     * @return res
     * { "errcode":0,"errmsg":"ok"}
     * { "errcode":40003,"errmsg":"invalid openid"}
     */
    @Override
    public boolean checkAccessTokenOfOpenIdValid(String accessToken, String openId) {
        Assert.notEmpty(accessToken, "accessToken为空，请检查");
        Assert.notEmpty(openId, "openId为空，请检查");
        String URL = checkAccessTokenValidURL;
        URL = URL.replace("ACCESS_TOKEN", accessToken);
        URL = URL.replace("OPENID", openId);
        String res = HttpUtil.get(URL, 3 * 1000);
        JSONObject json = JSONObject.parseObject(res);
        String errmsg = json.getString("errmsg");
        String errcode = json.getString("errcode");
        if ("ok".equals(errmsg)){
            return true;
        }else {
            throw new WechatApiException(WechatErrorEnum.getByErrcode(Integer.parseInt(errcode)));
        }
    }



    /**************************************************工具***************************************************/

    public String getCurrentUrl(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String currentUrl = request.getRequestURI();
        if (StrUtil.isEmpty(currentUrl)){
            return null;
        }
        return domain + currentUrl;
    }

}
