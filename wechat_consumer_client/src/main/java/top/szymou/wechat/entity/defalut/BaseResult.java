package top.szymou.wechat.entity.defalut;


import java.io.Serializable;

/**
 * @Description: 公共结果实体类
 * @author: 熟知宇某
 * @date: 2022/1/7 18:03
 * @Blog: https://blog.csdn.net/weixin_43548748
 */
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


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getRes() {
        return res;
    }

    public void setRes(Boolean res) {
        this.res = res;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
