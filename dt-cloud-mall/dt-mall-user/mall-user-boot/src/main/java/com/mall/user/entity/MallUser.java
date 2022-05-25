package com.mall.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author DT辰白 Created by 2021/12/24 10:21
 */
@Data
@TableName("t_mall_user")
public class MallUser {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private double money;
}
