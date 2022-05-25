package com.mall.sku.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author DT辰白 Created by 2021/12/24 10:21
 */
@Data
@TableName("t_mall_sku")
public class MallSku {

    /**
     * 商品ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 库存
     */
    private Integer stock;
}
