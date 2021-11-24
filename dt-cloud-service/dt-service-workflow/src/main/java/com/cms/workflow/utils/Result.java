package com.cms.workflow.utils;

import lombok.Data;

/**
 * @author ydf Created by 2021/11/24 16:46
 */
@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    public static Result error(int code, String s) {
        return Result.error(code,s);
    }

    // 省略
}
