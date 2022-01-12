package top.szymou.wechat.api.feign;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: 测试异常捕获
 * @author: 熟知宇某
 * @date: 2022/1/11 14:29
 * @Blog: https://blog.csdn.net/weixin_43548748
 */
@Component
public class TestServiceFallback implements FallbackFactory {
    Logger logger = LoggerFactory.getLogger(TestServiceFallback.class);
    @Override
    public Object create(Throwable throwable) {
        logger.error(throwable.toString());
        return null;
    }
}
