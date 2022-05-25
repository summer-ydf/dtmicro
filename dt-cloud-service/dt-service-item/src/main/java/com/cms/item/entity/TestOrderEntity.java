package com.cms.item.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author DT辰白 Created by 2021/12/22 12:40
 */
@Data
@TableName("sys_order")
public class TestOrderEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer a;
    private Integer b;
}
