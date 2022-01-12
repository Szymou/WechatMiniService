package top.szymou.wechat.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 公共结果实体类
 * @author: 熟知宇某
 * @date: 2022/1/7 18:03
 * @Blog: https://blog.csdn.net/weixin_43548748
 */
@Data
public class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code = 0;

    private String msg = "请求成功!";

    private Boolean res = true;

    private T data;

    private long timestamp = System.currentTimeMillis();

    public BaseResult<T> success(String msg){
        this.code = 200;
        this.msg = msg;
        return this;
    }

    public static<T> BaseResult<T> OK(){
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setCode(200);
        baseResult.setRes(true);
        baseResult.setMsg("请求成功");
        return baseResult;
    }

    public static<T> BaseResult<T> error500(){
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setCode(500);
        baseResult.setRes(false);
        baseResult.setMsg("请求失败");
        return baseResult;
    }
    public static<T> BaseResult<T> error500(T t){
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setCode(500);
        baseResult.setRes(false);
        baseResult.setMsg("请求失败");
        baseResult.setData(t);
        return baseResult;
    }

    public static<T> BaseResult<T> OK(T t){
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setCode(200);
        baseResult.setRes(true);
        baseResult.setData(t);
        baseResult.setMsg("请求成功");
        return baseResult;
    }

    public static<T> BaseResult<T> OK(T t, String msg){
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setCode(200);
        baseResult.setRes(true);
        baseResult.setData(t);
        baseResult.setMsg(msg);
        return baseResult;
    }

    public BaseResult<T> error(String msg){
        return error(500, msg);
    }

    public BaseResult<T> error(int code, String msg){
        this.code = code;
        this.msg = msg;
        this.res = false;
        return this;
    }




}
