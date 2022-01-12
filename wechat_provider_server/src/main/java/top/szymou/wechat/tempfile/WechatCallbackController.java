package top.szymou.wechat.tempfile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 熟知宇某
 * @date 2021/6/17 14:12
 */
@RestController
@RequestMapping("wechat")
public class WechatCallbackController {

    /**
     * 微信公众后台--设置与开发--公众号设置--功能设置---网页授权域名
     * @return
     */
    @GetMapping("MP_verify_AWGfKvBCtjwvKhZ7.txt")
    public String callback(){
        return "AWGfKvBCtjwvKhZ7";
    }

}
