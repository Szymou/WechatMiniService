package top.szymou.wechat.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.szymou.wechat.entity.dto.WechatJssdkEntity;
import top.szymou.wechat.exception.WechatApiException;
import top.szymou.wechat.service.WechatApiUtilService;
import top.szymou.wechat.entity.dto.WechatMsgDTO;
import top.szymou.wechat.entity.enums.WechatErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 熟知宇某
 * @date 2021/6/18 9:47
 */
@Service
@Slf4j
public class WechatApiUtilServiceImpl implements WechatApiUtilService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.appsecret}")
    private String appsecret;

    private String getWechatAccessTokenURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    private String pushMsgURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    /**
     * 获取普通access_token（全局）
     * @return res
     * {"access_token":"ACCESS_TOKEN","expires_in":7200}
     */
    private static int checkTimes = 10;//校验次数
    @Override
    public String getClobalAccessToken() {
        String accessToken = (String) redisTemplate.opsForValue().get("wAccessToken");
        if (StrUtil.isEmpty(accessToken)){
            String URL = getWechatAccessTokenURL;
            URL = URL.replace("APPID", appid);
            URL = URL.replace("APPSECRET", appsecret);
            String res = HttpUtil.get(URL, 3 * 1000);
            JSONObject json = JSONObject.parseObject(res);
            //校验是否失败
            if (json.containsKey("errcode")){
                String errcode = json.getString("errcode");
                String errmsg = json.getString("errmsg");
                log.error("获取普通access_token微信接口出错：{}", errmsg);
                throw new WechatApiException(WechatErrorEnum.getByErrcode(Integer.parseInt(errcode)));
            }
            accessToken = json.getString("access_token");
            String expires_in = json.getString("expires_in");
            redisTemplate.opsForValue().set("wAccessToken", accessToken, Integer.parseInt(expires_in), TimeUnit.SECONDS);
        }

        //校验AccessToken有效性
        if(checkTimes >= 0 && this.checkAccessTokenInvalid(accessToken)){
            //无效 则 删除缓存
            log.info("wAccessToken无效，重新获取。");
            redisTemplate.delete("wAccessToken");
            return getClobalAccessToken();
        }
        checkTimes = 10;
        return accessToken;
    }

    /**
     * 推送消息  默认非沙箱模式，如果推送成功失败，不返回错误信息，主要用于生产环境
     * @param dto
     * @return res
     * {"errcode":0,"errmsg":"ok","msgid":1919459598848081922}
     *
     */
    @Override
    public boolean pushMsg(WechatMsgDTO dto){
        return pushMsg(dto, false);
    }

    /**
     * 推送消息  可选沙箱，沙箱模式（sanbox=true），可以返回错误信息，主要用于调试
     * @param dto
     * @param sanbox
     * @return
     */
    @Override
    public boolean pushMsg(WechatMsgDTO dto, boolean sanbox) {
        Assert.notNull(dto, "消息数据dto为null，请检查");
        String URL = pushMsgURL;
        URL = URL.replace("ACCESS_TOKEN", this.getClobalAccessToken());
        String res = HttpUtil.post(URL, JSONObject.toJSONString(dto), 3 * 1000);
//        System.out.println(res);
        JSONObject json = JSONObject.parseObject(res);
        String errmsg = json.getString("errmsg");
        String errcode = json.getString("errcode");
        if ("ok".equals(errmsg)){
            return true;
        }else {
            log.error(WechatErrorEnum.getByErrcode(Integer.parseInt(errcode)).getDesc());
            if (sanbox){
                throw new WechatApiException(WechatErrorEnum.getByErrcode(Integer.parseInt(errcode)));
            }
            return false;
        }
    }

    /**
     * 微信 js-sdk接口所需配置参数
     * @param url
     * @return
     * {
     *   "signature": "4bdcb624933f5b9c1a00fa6c3868a044a1341f45",
     *   "appId": "wx8060f494f5ab2a6f",
     *   "nonceStr": "7dd05e35f74e441e",
     *   "timestamp": "1625639243"
     * }
     */
    public WechatJssdkEntity getWxJsConfigParam(String url){

        //1、获取AccessToken
        String accessToken = getClobalAccessToken();

        //2、获取Ticket
        String redisTicket = (String) redisTemplate.opsForValue().get("wx_jsapi_ticket");
        String jsapi_ticket = "";
        if (StrUtil.isNotEmpty(redisTicket)){
            jsapi_ticket = redisTicket;
        }else {
            jsapi_ticket = getTicket(accessToken);
            redisTemplate.opsForValue().set("wx_jsapi_ticket", jsapi_ticket, 7190, TimeUnit.SECONDS);//有效期7200s
        }

        //3、时间戳和随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳

        //5、将参数排序并拼接字符串
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;

        //6、将字符串进行sha1加密
        String signature = SHA1(str);

        WechatJssdkEntity dto = new WechatJssdkEntity();
        dto.setAppId(appid);
        dto.setTimestamp(timestamp);
        dto.setNonceStr(noncestr);
        dto.setSignature(signature);
        return dto;
    }

    /************************************************函数**********************************************/
    private static String getTicket(String access_token) {
        String ticket = null;
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token +"&type=jsapi";//这个url链接和参数不能变
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.parseObject(message);
            //System.out.println("JSON字符串："+demoJson);
            ticket = demoJson.getString("ticket");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }

    private static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 利用获取微信服务器ip地址的接口检测access_token有效性
     * checkAccessTokenInvalid：检查是否无效（invalid）
     * 返回true：无效；
     * 返回false：有效
     * @return
     */
    public static boolean checkAccessTokenInvalid(String accessToken){
//        log.info("使用微信api（校验ip）来校验accesstoken有效性。");
        String token = Optional.ofNullable(accessToken).orElse("TokenIsNull");
        String url = "https://api.weixin.qq.com/cgi-bin/get_api_domain_ip?access_token=ACCESS_TOKEN";
        url = url.replace("ACCESS_TOKEN", token);
        String result = HttpUtil.get(url, 3 * 1000);
        JSONObject res = JSON.parseObject(result);
        String errcode = res.getString("errcode");
        String errmsg = res.getString("errmsg");
        checkTimes--;
        //如果errcode、errmsg为空，说明accesstoken是有效的，因为只有无效的时候才会返回包含这两个key的json
        if(StrUtil.isNotBlank(errcode) || StrUtil.isNotBlank(errmsg)){
            log.info("由于测试原因，产生了多个access_token，导致其中一些服务无法使用，需要重新获取。",token);
            return true;
        }
        return false;
    }

}
