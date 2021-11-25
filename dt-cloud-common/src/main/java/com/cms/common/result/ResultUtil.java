package com.cms.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 提返回值封装
 * @author ydf Created by 2021/11/25 11:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultUtil<T> implements Serializable {

    private static final long serialVersionUID = -1168831279481453759L;

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 描述信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 操作成功
     * @param data 数据
     * @param <T> 泛型
     * @return 返回对象
     */
    public static <T> ResultUtil<T> success(T data) {
        return new ResultUtil<>(2000, "操作成功!", data);
    }

    public static <T> ResultUtil<T> success() {
        return new ResultUtil<>(2000, "操作成功!", null);
    }

    public static <T> ResultUtil<T> success(ResultEnum code, ResultEnum msg, T data) {
        return new ResultUtil<>(code.getCode(), msg.getMsg(), data);
    }

    /**
     * 操作失败
     * @param <T> 泛型
     * @return 返回对象
     */
    public static <T> ResultUtil<T> error() {
        return new ResultUtil<>(2001, "操作失败!", null);
    }

    public static <T> ResultUtil<T> error(ResultEnum code, ResultEnum msg) {
        return new ResultUtil<>(code.getCode(), msg.getMsg() , null);
    }

    public static <T> ResultUtil<T> error(int code, String msg) {
        return new ResultUtil<>(code,msg,null);
    }

    public static <T> ResultUtil<T> error(String msg) {
        return new ResultUtil<>(2001,msg,null);
    }
}
