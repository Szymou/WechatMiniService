# wechat_mini_service(微信公共数据服务)

## 前言 
>> **诞生之因**：
>>
>> 项目历程中，不免会遇到多个项目需要用到微信公众号接口功能，如获取用户信息、所在地址位置、消息推送等功能。
> 如果每个项目都接入同一个微信公众号，连接同一个外部存储空间，获取相同的数据，使用相同的接口功能，代码中堆着相同的代码，将会产生以下几点非常：
>> 
>> 1. 存储空间连接过多，如果某一项目系统影响该存储空间，将会波及相关的项目系统
>> 2. 并发获取唯一数据，会出现卡线程，前后线程获取数据不一致，造成前线程数据失效
>> 3. 代码冗余（能抽为啥不抽，抽代码如此快乐~） 
>> 
>> 综上所述，为减弱或消除这些影响，写下了本项目，统一微信公众号服务。




## 项目说明
>
>> **目的**：
>> 
>> 1. 解决Accesstoken在并发下失效  
>> 2. 便利项目试用微信公众号接口功能
> 
>> **架构**：
>> 1. 基于springboot框架，java语言 
>> 1. 服务端模块：wechat_provider_server
>> 2. 客户端模块：wechat_consumer_client
>> 3. 接口通信：feign




## 项目使用
>
>> **重点：调用微信公众号提供的接口至少需要满足一下几个条件**：
>>
>> 1. 唯一的微信公众号AppID和AppSecret
>> 2. 获取微信公众号提供的鉴权串AccessToken
>> 3. 配置授权回调页面域名：主要用于微信网页中用户鉴权
>> 4. 配置JS接口安全域名：主要用于试用微信开放的JS接口
> 
>> **<span id="server">服务端</span>**：
>> 1. 配置AppID和AppSecret
>> 2. 配置redis
>> 3. 部署(记得在您的服务器安全组，开放服务端口)
> 
>> **客户端**：
>> 1. **项目中，引入依赖 wechat_consumer_client-版本号.jar**
>> 2. **在pom文件引入依赖**
>>  ```java
>>     <dependency>
>>         <groupId>org.springframework.cloud</groupId>
>>         <artifactId>spring-cloud-starter-openfeign</artifactId>
>>         <version>x.x.x版本</version>
>>     </dependency>
>>     <dependency>
>>         <groupId>com.alibaba</groupId>
>>         <artifactId>fastjson</artifactId>
>>         <version>x.x.x版本</version>
>>     </dependency>
>>```
>> 3. **配置服务端参数**
>> 
>> ```java
>>    wechat:
>>      server:
>>        ip: 192.168.1.89
>>        port: 7080
>>        base-path: /
>>``` 
>> * ip：[服务端](#server)的ip地址或者域名
>> * port：[服务端](#server)的端口
>> * base-path：如果[服务端](#server)配置了上下文路径（如server.servlet.context-path: /basexxx），则在此base-path加上/basexxx。
>> 
>> 4. **启动类配置**
>> @EnableFeignClients(basePackages = "top.szymou") 
>> 
>> 5. **[接口使用](#interface-use)**
>>
>>
>>
>>
>>
>

## <span id="interface-use">接口使用<span>(持续更新)
> 
> ```java
> 自动注入：
> @Autowired
> private WechatClientApi wechatClientApi;
> ```
> ```java
> 1. 获取AccessToken。
> String accesstoken = wechatClientApi.getGlobalAccessToken();
> 
> 2. 获取jssdk配置信息
> Map<String, Object> entity = wechatClientApi.getWxJsConfigParam("url");
>
> 3.推送消息（四种方式：推送单人/多人、推送单人/多人[消息文字有颜色]）
>   ①推送单人//wechatClientApi.pushMsgToOpenId(msgEntity);
>       WechatMsgEntity msgEntity = new WechatMsgEntity();
>       msgEntity.setMsgTemplate("aGF337SdFcPuGwogB_2VJREgBGwIb61341awxY1Fte0"); //消息模板
>       msgEntity.setFirst("你好");                                              //消息头部
>       msgEntity.setRemark("这是底部");                                         //消息尾部
>       msgEntity.setRedirectUrl("https://blog.csdn.net/weixin_43548748");      //点击消息跳转的链接
>       
>       //设置消息主体
>       List<String> keywords = new ArrayList<>();                              
>       keywords.add("消息1"); keywords.add("消息2"); keywords.add("消息3"); keywords.add("消息4");
>       msgEntity.setKeywords(keywords);                                        //消息主体
> 
>       msgEntity.setOpenId("oIYjF6LZ-5lq_Whf-Sy7Xb1pvbC8");                     //用户openID
>
>       boolean b = wechatClientApi.pushMsgToOpenId(msgEntity);                 //推送消息
> 
>   ②推送多人//wechatClientApi.pushMsgToOpenIds(msgEntity);
>       WechatMsgEntity msgEntity = new WechatMsgEntity();
>       msgEntity.setMsgTemplate("aGF337SdFcPuGwogB_2VJREgBGwIb61341awxY1Fte0"); //消息模板
>       msgEntity.setFirst("你好");                                              //消息头部
>       msgEntity.setRemark("这是底部");                                         //消息尾部
>       msgEntity.setRedirectUrl("https://blog.csdn.net/weixin_43548748");      //点击消息跳转的链接
>       
>       //设置消息主体
>       List<String> keywords = new ArrayList<>();                              
>       keywords.add("消息1"); keywords.add("消息2"); keywords.add("消息3"); keywords.add("消息4");
>       msgEntity.setKeywords(keywords);                                        //消息主体
> 
>       //设置用户openIds
>       /************************msgEntity.setOpenIds()设置多个用户openIds********************/
>       List<String> openids = new ArrayList<>();
>       openids.add("oIYjF6LZ-5lq_Whf-Sy7Xb1pvbC8");
>       openids.add("oIYjF6OGzYoBHgFqULPjgrkUW82s");
>       openids.add("oIYjF6Jv-nsXRGq5kGhgyGLSkrEI");
>       msgEntity.setOpenIds(openids);                                          //用户openIds
>       /*************************msgEntity.setOpenIds()设置多个用户openIds********************/
>       boolean b = wechatClientApi.pushMsgToOpenIds(msgEntity);                 //推送消息
> 
> 
>   ③推送单人[消息文字有颜色]//wechatClientApi.pushMsgWithColor(msgEntity);
>       WechatMsgEntity msgEntity = new WechatMsgEntity();
>       msgEntity.setMsgTemplate("aGF337SdFcPuGwogB_2VJREgBGwIb61341awxY1Fte0"); //消息模板
>       msgEntity.setFirst("你好");                                              //消息头部
>       msgEntity.setRemark("这是底部");                                         //消息尾部
>       msgEntity.setRedirectUrl("https://blog.csdn.net/weixin_43548748");      //点击消息跳转的链接
> 
>       //设置消息主体
>       /*************************new WechatMsgColorEntity("消息", 消息颜色枚举);********************/
>       List<WechatMsgColorEntity> wechatMsgColorEntities = new ArrayList<>();
>       wechatMsgColorEntities.add(new WechatMsgColorEntity("消息1", MsgColorEnum.Blue));
>       wechatMsgColorEntities.add(new WechatMsgColorEntity("消息2", MsgColorEnum.Red));
>       wechatMsgColorEntities.add(new WechatMsgColorEntity("消息3", MsgColorEnum.Green));
>       wechatMsgColorEntities.add(new WechatMsgColorEntity("消息4", MsgColorEnum.Grey));
>       wechatMsgColorEntities.add(new WechatMsgColorEntity("消息5", MsgColorEnum.Pink));
>       msgEntity.setMsgValueColorDTOS(wechatMsgColorEntities);
>       /*************************msgEntity.setMsgValueColorDTOS()设置有颜色的消息********************/
> 
>       msgEntity.setOpenId("oIYjF6LZ-5lq_Whf-Sy7Xb1pvbC8");                     //用户openID
>
>       boolean b = wechatClientApi.pushMsgWithColor(msgEntity);                 //推送消息 
>   ④推送多人[消息文字有颜色]
>       WechatMsgEntity msgEntity = new WechatMsgEntity();
>       msgEntity.setMsgTemplate("aGF337SdFcPuGwogB_2VJREgBGwIb61341awxY1Fte0");
>       msgEntity.setFirst("你好");
>       msgEntity.setRemark("这是底部");
>       msgEntity.setRedirectUrl("https://blog.csdn.net/weixin_43548748");
>
>       //设置消息主体
>       /*************************new WechatMsgColorEntity("消息", 消息颜色枚举);********************/
>       List<WechatMsgColorEntity> wechatMsgColorEntities = new ArrayList<>();
>       wechatMsgColorEntities.add(new WechatMsgColorEntity("消息1", MsgColorEnum.Blue));
>       wechatMsgColorEntities.add(new WechatMsgColorEntity("消息2", MsgColorEnum.Red));
>       wechatMsgColorEntities.add(new WechatMsgColorEntity("消息3", MsgColorEnum.Green));
>       wechatMsgColorEntities.add(new WechatMsgColorEntity("消息4", MsgColorEnum.Grey));
>       wechatMsgColorEntities.add(new WechatMsgColorEntity("消息5", MsgColorEnum.Pink));
>       msgEntity.setMsgValueColorDTOS(wechatMsgColorEntities);
>       /*************************msgEntity.setMsgValueColorDTOS()设置有颜色的消息********************/
> 
>       //设置用户openIds
>       /************************msgEntity.setOpenIds()设置多个用户openIds********************/
>       List<String> openids = new ArrayList<>();
>       openids.add("oIYjF6LZ-5lq_Whf-Sy7Xb1pvbC8");
>       openids.add("oIYjF6OGzYoBHgFqULPjgrkUW82s");
>       openids.add("oIYjF6Jv-nsXRGq5kGhgyGLSkrEI");
>       msgEntity.setOpenIds(openids);
>       /*************************msgEntity.setOpenIds()设置多个用户openIds********************/
>       List<String> failedOpenIds = wechatClientApi.pushMsgWithColorToOpenIds(msgEntity);
>
>
>
>


##后期

* 熔断？

* 用户使用集成抽象类重写？可否降级服务
