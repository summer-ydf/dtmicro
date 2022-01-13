package com.cms.manage.vo;

import com.cms.modular.entity.PageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页、查询条件参数
 * @author ydf Created by 2022/1/13 13:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOperatorPage extends PageEntity {

    private String username;
}
