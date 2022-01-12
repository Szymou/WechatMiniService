package top.szymou.wechat.entity.defalut;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: 初始化参数
 * @author: 熟知宇某
 * @date: 2022/1/5 16:11
 * @Blog: https://blog.csdn.net/weixin_43548748
 */
@ConfigurationProperties(prefix = "wechat.server")
@Component
public class InitWechatServerParams {

    private String ip;

    private String port;

    private String basePath;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
