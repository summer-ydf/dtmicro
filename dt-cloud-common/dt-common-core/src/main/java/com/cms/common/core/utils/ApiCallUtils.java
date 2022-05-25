package com.cms.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultEnum;
import com.cms.common.tool.result.ResultException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

import static com.cms.common.tool.constant.ConstantCode.GATEWAY_AUTHORIZATION;

/**
 * @author DT辰白 Created by 2022/1/22 15:14
 */
public class ApiCallUtils {

    public static SecurityClaimsUserEntity securityClaimsUser(HttpServletRequest request) throws ResultException {
        String token = request.getHeader(GATEWAY_AUTHORIZATION);
        if(StringUtils.isEmpty(token)) {
            throw new ResultException(ResultEnum.NO_AUTH);
        }
        SecurityClaimsUserEntity claims = JSON.parseObject(token, SecurityClaimsUserEntity.class);
        if(ObjectUtils.isEmpty(claims)) {
            throw new ResultException(ResultEnum.NO_AUTH);
        }
        return claims;
    }
}
