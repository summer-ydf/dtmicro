package com.cms.workflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ydf Created by 2021/12/1 11:28
 */
@Data
@TableName("t_flow_instance")
public class FlowInstanceEntity implements Serializable {

    private static final long serialVersionUID = 1475144106114388528L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;
}
