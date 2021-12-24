package com.mall.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ydf Created by 2021/12/24 10:21
 */
@Data
@TableName("t_mall_order")
public class MallOrder {

    /**
     * 订单ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 商品ID
     */
    private Integer goodId;

    /**
     * 订单状态（1：已经支付 0：待支付）
     */
    private Integer status;
}
