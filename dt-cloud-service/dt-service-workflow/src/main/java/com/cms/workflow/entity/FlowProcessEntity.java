package com.cms.workflow.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 流程定义实例对象
 * @author ydf Created by 2022/3/25 10:40
 */
@Data
public class FlowProcessEntity implements Serializable {

    private String id;

    private String category;

    private String name;
}
