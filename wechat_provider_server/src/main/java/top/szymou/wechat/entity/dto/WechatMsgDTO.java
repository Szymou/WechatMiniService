package top.szymou.wechat.entity.dto;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 熟知宇某
 * @date 2021/6/16 23:31
 */
@Data
public class WechatMsgDTO {

    private String touser;
    private List<String> touserList;
    private String template_id;
    private String url;
    private String topcolor;
    private Map<String, WechatMsgParamDTO> data;

    public WechatMsgDTO(){}

    /**
     * 消息字段内容--方法1
     * keyword 顺序要按模板顺序
     * @param first
     * @param remark
     * @param keyword
     */
    public void defindContent(String first, String remark, String... keyword){
        data = new HashMap<>();
        this.data.put("first", new WechatMsgParamDTO(first));
        this.data.put("remark", new WechatMsgParamDTO(remark));
        for (int i = 0; i < keyword.length; i++) {
            this.data.put("keyword" + (i + 1), new WechatMsgParamDTO(keyword[i]));
        }
    }

    /**
     * 消息字段内容--方法1.1
     * keyword 顺序要按模板顺序
     * @param first
     * @param remark
     * @param keywords list
     */
    public void defindContent(String first, String remark, List<String> keywords){
        data = new HashMap<>();
        this.data.put("first", new WechatMsgParamDTO(first));
        this.data.put("remark", new WechatMsgParamDTO(remark));
        for (int i = 0; i < keywords.size(); i++) {
            this.data.put("keyword" + (i + 1), new WechatMsgParamDTO(keywords.get(i)));
        }
    }

    /**
     * 消息字段内容--方法1.2---可设置字体颜色
     * keyword 顺序要按模板顺序
     * @param first
     * @param remark
     * @param keywords list
     */
    public void defindContentWithColor(String first, String remark, List<MsgValueColorDTO> keywords){
        data = new HashMap<>();
        this.data.put("first", new WechatMsgParamDTO(first));
        this.data.put("remark", new WechatMsgParamDTO(remark));
        for (int i = 0; i < keywords.size(); i++) {
            MsgValueColorDTO dto = keywords.get(i);
            if (StrUtil.isEmpty(dto.getValue())){
                continue;
            }
            this.data.put("keyword" + (i + 1), new WechatMsgParamDTO(dto.getValue(), dto.getColor()));
        }
    }

    /**
     * 消息字段内容--方法2
     * @param data
     */
    public void defindContent(Map<String, WechatMsgParamDTO> data){
        this.data = data;
    }

}
