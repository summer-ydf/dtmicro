package com.cms.common.core.domain;

import lombok.Data;

/**
 * @author DT辰白
 * @date 2022/4/17 18:30
 */
@Data
public class SysConfig {

    private Long id;

    private String k;

    private String v;

    // 未加密值
    private String val;
}
