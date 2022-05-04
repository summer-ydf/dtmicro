package com.cms.oauth.security.model.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.api.manage.feign.OauthFeignClientService;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.oauth.service.impl.WechatUserDetailsServiceImpl;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;

/**
 * 微信认证授权提供者
 * @author ydf Created by 2022/4/27 9:47
 */
@Slf4j
@Data
public class WechatAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    private WxMaService wxMaService;
    private OauthFeignClientService oauthFeignClientService;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WechatAuthenticationToken authenticationToken = (WechatAuthenticationToken) authentication;
        // 获取微信授权码
        String code = (String) authenticationToken.getPrincipal();

        // 获取openId和sessionKey
        WxMaJscode2SessionResult sessionResult =  wxMaService.getUserService().getSessionInfo(code);
        String openid = sessionResult.getOpenid();
        String sessionKey = sessionResult.getSessionKey();

        log.info("openid:{}", openid);
        log.info("sessionKey:{}", sessionKey);

        // 查询用户是否存在
        ResultUtil<SecurityClaimsUserEntity> claimsUserEntityResultUtil = oauthFeignClientService.loadUserByOpenId(openid);
        if (!claimsUserEntityResultUtil.isSuccess()) {
            if (ObjectUtils.isEmpty(claimsUserEntityResultUtil.getData())) {
                // TODO 添加为新的用户
                log.info("添加为新的微信用户");
                String encryptedData = authenticationToken.getEncryptedData();
                String iv = authenticationToken.getIv();
                log.info("encryptedData：【{}】",encryptedData);
                log.info("iv：【{}】",iv);
                // 解密获取用户信息
                WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
                log.info("userInfo：【{}】",userInfo);
            }
        }
        UserDetails userDetails = ((WechatUserDetailsServiceImpl) userDetailsService).loadUserByOpenId(openid);
        WechatAuthenticationToken result = new WechatAuthenticationToken(userDetails, new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
