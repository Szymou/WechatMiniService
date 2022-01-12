package top.szymou.wechat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.szymou.wechat.exception.WechatApiException;

/**
 * @author 熟知宇某
 * @date 2021/12/24 10:14
 */
@RestController
@RequestMapping("default")
public class DemoController {

    @GetMapping("hello")
    public String hello(){
        return "Hello World!";
    }

    @GetMapping("loadFail")
    public void loadFail(){
        throw new WechatApiException("地对地导弹多大到沙发上地方");
//        return "Hello World!" + 1/0;
    }

//    @Autowired
//    private WechatConsumerFeign wechatConsumerFeign;

//    @GetMapping("teFeign")
//    public String teFeign(){
////        String s = wechatConsumerFeign.hello();
//        return s;
//    }
}
