package com.cms.common.tool.domain;

import lombok.Data;

/**
 * @author DT辰白 Created by 2022/4/28 13:55
 */
@Data
public class SysOauthClientVoEntity {

    private String clientId;

    private String clientSecret;

    private String resourceIds;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;
}
