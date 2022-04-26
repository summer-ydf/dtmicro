package com.cms.oauth.controller;

import cn.hutool.json.JSONUtil;
import com.cms.common.core.utils.ApiCallUtils;
import com.cms.common.jdbc.config.IdGeneratorConfig;
import com.cms.common.jdbc.utils.RedisUtils;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultException;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.common.tool.utils.VerifyCodeUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import static com.cms.common.tool.constant.ConstantCode.CACHE_CODE_KEY;
import static com.cms.common.tool.constant.ConstantCode.GATEWAY_AUTHORIZATION;
import static com.cms.common.tool.constant.ConstantCode.HEIGHT;
import static com.cms.common.tool.constant.ConstantCode.IMG_JPG;
import static com.cms.common.tool.constant.ConstantCode.WIDTH;

/**
 * @author DT
 * @date 2022/4/25 19:48
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @ApiOperation(value = "OAuth2认证", notes = "登录入口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", defaultValue = "client", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", defaultValue = "admin", value = "用户名"),
            @ApiImplicitParam(name = "password", defaultValue = "123456", value = "用户密码")
    })
    @PostMapping("/token")
    public Object postAccessToken(
            @ApiIgnore Principal principal,
            @ApiIgnore @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {

        log.info("OAuth认证授权，请求参数：{}", JSONUtil.toJsonStr(parameters));

        /**
         * 获取登录认证的客户端ID
         *
         * 兼容两种方式获取Oauth2客户端信息（client_id、client_secret）
         * 方式一：client_id、client_secret放在请求路径中(注：当前版本已废弃)
         * 方式二：放在请求头（Request Headers）中的Authorization字段，且经过加密，例如 Basic Y2xpZW50OnNlY3JldA== 明文等于 client:secret
         */
//        String clientId = RequestUtils.getOAuth2ClientId();
//        log.info("OAuth认证授权 客户端ID:{}，请求参数：{}", clientId, JSONUtil.toJsonStr(parameters));
//
//        /**
//         * knife4j接口文档测试使用
//         *
//         * 请求头自动填充，token必须原生返回，不能有任何包装，否则显示 undefined undefined
//         * 账号/密码:  client_id/client_secret : client/123456
//         */
//        if (SecurityConstants.TEST_CLIENT_ID.equals(clientId)) {
//            return tokenEndpoint.postAccessToken(principal, parameters).getBody();
//        }

        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return ResultUtil.success(accessToken);
    }
}
