package com.cms.auth.utils;

import com.alibaba.fastjson.JSON;
import com.cms.common.entity.SecurityClaimsUser;
import com.cms.common.result.ResultEnum;
import com.cms.common.result.ResultException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ydf Created by 2022/1/22 15:14
 */
public class ApiCallUtils {

    public static SecurityClaimsUser securityClaimsUser(HttpServletRequest request) throws  ResultException {
        String token = request.getHeader("Icc-Gateway-Authorization");
        if(StringUtils.isEmpty(token)) {
            throw new ResultException(ResultEnum.NO_AUTH);
        }
        SecurityClaimsUser claims = JSON.parseObject(new String(Base64.decodeBase64(token)),SecurityClaimsUser.class);
        if(claims == null) {
            throw new ResultException(ResultEnum.NO_AUTH);
        }
        return claims;
    }
}
