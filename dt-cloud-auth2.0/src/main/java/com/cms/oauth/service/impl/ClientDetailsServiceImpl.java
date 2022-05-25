package com.cms.oauth.service.impl;

import com.api.manage.feign.OauthClientFeignClientService;
import com.cms.common.tool.domain.SysOauthClientVoEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.SysCmsUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

/**
 * 获取客户端信息【MySQL中】
 * @author DT辰白 Created by 2022/4/25 18:59
 */
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final OauthClientFeignClientService oauthClientFeignClientService;

    public ClientDetailsServiceImpl(OauthClientFeignClientService oauthClientFeignClientService) {
        this.oauthClientFeignClientService = oauthClientFeignClientService;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        try {
            SysCmsUtils.log.info("远程调用客户端获取信息->>>" + clientId);
            ResultUtil<SysOauthClientVoEntity> re = oauthClientFeignClientService.getOauthClientByClientId(clientId);
            SysCmsUtils.log.info("远程调用客户端回调结果->>>" + re);
            if (re.isSuccess()) {
                SysOauthClientVoEntity client = re.getData();
                BaseClientDetails clientDetails = new BaseClientDetails(
                        client.getClientId(),
                        client.getResourceIds(),
                        client.getScope(),
                        client.getAuthorizedGrantTypes(),
                        client.getAuthorities(),
                        client.getWebServerRedirectUri());
                clientDetails.setClientSecret(client.getClientSecret());
                clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
                clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
                return clientDetails;
            } else {
                throw new NoSuchClientException("No client with requested id: " + clientId);
            }
        } catch (EmptyResultDataAccessException var4) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
    }
}

