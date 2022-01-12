# wechat_data_provide_server
微信公共数据服务



1.客户端必须配置ip\port\base-path
如：
>
> wechat:
> 
>server:
> 
>ip: 127.0.0.1
> 
>port: 8080
> 
>base-path: shYf/sh/checkCloth

pom添加依赖：

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
            <version>2.2.5.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.78</version>
        </dependency>

启动项记得添加enablexxxxx


熔断

用户使用集成抽象类重写？可否降级服务
