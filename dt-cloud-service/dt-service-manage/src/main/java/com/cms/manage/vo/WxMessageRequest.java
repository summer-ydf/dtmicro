package com.cms.manage.vo;

import lombok.Data;

import java.util.List;

/**
 * @author DT
 * @date 2022/5/22 0:13
 */
@Data
public class WxMessageRequest {

    /**
     * 接收者ID集合
     */
    private List<Long> receiverIds;

    /**
     * 发送类型
     */
    private String category;

}
