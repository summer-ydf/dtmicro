package com.cms.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultEnum;
import com.cms.common.tool.result.ResultException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static com.cms.common.tool.constant.ConstantCommonCode.GATEWAY_AUTHORIZATION;

/**
 * @author ydf Created by 2022/1/22 15:14
 */
public class ApiCallUtils {

    public static SecurityClaimsUserEntity securityClaimsUser(HttpServletRequest request) throws ResultException {
        String token = request.getHeader(GATEWAY_AUTHORIZATION);
        if(StringUtils.isEmpty(token)) {
            throw new ResultException(ResultEnum.NO_AUTH);
        }
        SecurityClaimsUserEntity claims = JSON.parseObject(new String(Base64.decodeBase64(token)), SecurityClaimsUserEntity.class);
        if(claims == null) {
            throw new ResultException(ResultEnum.NO_AUTH);
        }
        return claims;
    }
}
