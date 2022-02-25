package com.cms.manage.vo;

import com.cms.common.jdbc.domain.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页、查询条件参数
 * @author ydf Created by 2022/1/13 13:56
 */
@Data
@ApiModel(value="操作员分页传输对象")
@EqualsAndHashCode(callSuper = true)
public class SysOperatorPage extends PageEntity {

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "部门ID")
    private String deptId;
}
