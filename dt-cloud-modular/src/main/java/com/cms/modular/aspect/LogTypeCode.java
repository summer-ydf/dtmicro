package com.cms.modular.aspect;

/**
 * 操作日志处理类型常量
 * @author ydf Created by 2022/2/11 13:53
 */
public class LogTypeCode {

    /**
     * 新增
     */
    public static final String INSERT = "insert";

    /**
     * 修改
     */
    public static final String UPDATE = "update";

    /**
     * 删除
     */
    public static final String DELETE = "delete";

    /**
     * 批量新增
     */
    public static final String BATCH_INSERT = "batch_insert";

    /**
     * 批量修改
     */
    public static final String BATCH_UPDATE = "batch_update";

    /**
     * 批量删除
     */
    public static final String BATCH_DELETE = "batch_delete";

    /**
     * 其它
     */
    public static final String OTHER = "other";
}
