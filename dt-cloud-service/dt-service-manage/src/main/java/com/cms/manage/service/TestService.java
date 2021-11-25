package com.cms.manage.service;

import com.cms.common.result.ResultUtil;

/**
 * @author ydf Created by 2021/11/23 16:32
 */
public interface TestService {
    ResultUtil<?> getTest(String userId);

    String save();
}
