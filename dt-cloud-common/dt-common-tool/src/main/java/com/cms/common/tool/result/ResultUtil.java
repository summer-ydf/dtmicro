package com.cms.common.tool.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 提返回值封装
 * @author DT辰白 Created by 2021/11/25 11:09
 */
@Data
public class ResultUtil<T> implements Serializable {

    private static final long serialVersionUID = -1168831279481453759L;

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 消息提示
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 时间戳
     */
    private long timestamp;

    public ResultUtil() {
    }

    public ResultUtil(Integer code, String message, T data, boolean success, long timestamp) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
        this.timestamp = timestamp;
    }

    /**
     * 操作成功
     * @param data 数据
     * @param <T> 泛型
     * @return 返回对象
     */
    public static <T> ResultUtil<T> success(T data) {
        return new ResultUtil<>(2000, "操作成功!", data, true, System.currentTimeMillis());
    }

    public static <T> ResultUtil<T> success() {
        return new ResultUtil<>(2000, "操作成功!", null, true, System.currentTimeMillis());
    }

    public static <T> ResultUtil<T> success(ResultEnum code, ResultEnum message, T data) {
        return new ResultUtil<>(code.getCode(), message.getMessage(), data,true, System.currentTimeMillis());
    }

    public static <T> ResultUtil<T> success(ResultEnum code, String message, T data) {
        return new ResultUtil<>(code.getCode(), message, data,true, System.currentTimeMillis());
    }

    /**
     * 操作失败
     * @param <T> 泛型
     * @return 返回对象
     */
    public static <T> ResultUtil<T> error() {
        return new ResultUtil<>(2001, "操作失败!", null,false, System.currentTimeMillis());
    }

    public static <T> ResultUtil<T> error(ResultEnum code, ResultEnum message) {
        return new ResultUtil<>(code.getCode(), message.getMessage() , null,false, System.currentTimeMillis());
    }

    public static <T> ResultUtil<T> error(int code, String message) {
        return new ResultUtil<>(code,message,null,false, System.currentTimeMillis());
    }

    public static <T> ResultUtil<T> error(String message) {
        return new ResultUtil<>(2001,message,null,false, System.currentTimeMillis());
    }

    public static <T> ResultUtil<T> failed(ResultEnum resultEnum) {
        return result(resultEnum.getCode(), resultEnum.getMessage(), null);
    }

    private static <T> ResultUtil<T> result(Integer code, String message, T data) {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        result.setSuccess(false);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }
}
