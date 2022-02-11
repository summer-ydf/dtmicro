package com.cms.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.auth.domain.OauthClientDetails;
import com.cms.auth.mapper.OauthClientMapper;
import com.cms.auth.service.OauthClientService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static com.cms.common.tool.constant.ConstantCommonCode.OAUTH_CLIENT_ID;
import static com.cms.common.tool.constant.ConstantCommonCode.OAUTH_CLIENT_SECRET;
import static com.cms.common.tool.constant.ConstantCommonCode.OAUTH_GRANT_TYPE;
import static com.cms.common.tool.constant.ConstantCommonCode.OAUTH_SCOPE;

/**
 * @author ydf Created by 2022/1/12 11:50
 */
@Service
public class OauthClientServiceImpl extends ServiceImpl<OauthClientMapper, OauthClientDetails> implements OauthClientService {

    @Override
    public void initOauthClientDetails() {
        OauthClientDetails details = this.baseMapper.selectOne(new QueryWrapper<OauthClientDetails>().eq("client_id", OAUTH_CLIENT_ID).eq("scope", OAUTH_SCOPE));
        if(ObjectUtils.isEmpty(details)) {
            OauthClientDetails oauthClientDetails = OauthClientDetails.builder()
                    .clientId(OAUTH_CLIENT_ID)
                    .clientSecret(new BCryptPasswordEncoder().encode(OAUTH_CLIENT_SECRET))
                    .scope(OAUTH_SCOPE)
                    .authorizedGrantTypes(OAUTH_GRANT_TYPE)
                    .build();
            this.baseMapper.insert(oauthClientDetails);
        }
    }
}
