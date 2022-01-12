package com.cms.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.auth.domain.OauthClientDetails;

/**
 * @author ydf Created by 2022/1/12 11:23
 */
public interface OauthClientService extends IService<OauthClientDetails> {

    void initOauthClientDetails();
}
