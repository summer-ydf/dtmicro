package com.cms.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ydf Created by 2021/12/22 13:53
 */
@Data
@TableName("sys_product")
public class TestProductEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer c;
}
