package com.cms.modular.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页查询基础实体类
 * @author DT
 * @date 2021/6/12 11:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageEntity implements Serializable {

    private static final long serialVersionUID = 7143289007025794445L;

    @ApiModelProperty(value = "当前页")
    private Integer current = 1;

    @ApiModelProperty(value = "每页容量")
    private Integer size = 10;

}
