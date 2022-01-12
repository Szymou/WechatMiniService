package top.szymou.wechat.entity.dto;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * @author 熟知宇某
 * @date 2021/6/16 23:33
 */
@Data
public class WechatMsgParamDTO {

    /**
     * 字段消息
     */
    private String value;

    /**
     * 消息颜色
     */
    private String color;

    WechatMsgParamDTO(){}

    WechatMsgParamDTO(String value, String color){
        if (StrUtil.isEmpty(color) || !StrUtil.startWith(color, "#")){
            this.value = value;
        }else {
            this.value = value;
            this.color = color;
        }
    }

    WechatMsgParamDTO(String value){
        this(value, "#173177");
    }


}
