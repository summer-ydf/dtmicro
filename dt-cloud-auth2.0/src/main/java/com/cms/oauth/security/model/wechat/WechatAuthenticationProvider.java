package com.cms.oauth.security.model.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.cms.oauth.service.impl.MemberUserDetailsServiceImpl;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;

/**
 * 微信认证授权提供者
 * @author ydf Created by 2022/4/27 9:47
 */
@Data
public class WechatAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    private WxMaService wxMaService;
//    private MemberFeignClient memberFeignClient;

    /**
     * 微信认证
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WechatAuthenticationToken authenticationToken = (WechatAuthenticationToken) authentication;
        String code = (String) authenticationToken.getPrincipal();

        // 解析code获取微信用户基础信息
        WxMaJscode2SessionResult sessionInfo =  wxMaService.getUserService().getSessionInfo(code);
        System.out.println("获取微信用户基础信息："+sessionInfo);
        // 获取openId和sessionKey
        String openid = sessionInfo.getOpenid();
//        Result<MemberAuthInfoDTO> memberAuthResult = memberFeignClient.loadUserByOpenId(openid);
//        // 微信用户不存在，注册成为新会员
//        if (memberAuthResult != null && ResultCode.USER_NOT_EXIST.getCode().equals(memberAuthResult.getCode())) {
//
//            String sessionKey = sessionInfo.getSessionKey();
//            String encryptedData = authenticationToken.getEncryptedData();
//            String iv = authenticationToken.getIv();
//            // 解密 encryptedData 获取用户信息
//            WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
//
//            MemberDTO memberDTO = new MemberDTO();
//            BeanUtil.copyProperties(userInfo, memberDTO);
//            memberDTO.setOpenid(openid);
//            memberFeignClient.addMember(memberDTO);
//        }
        UserDetails userDetails = ((MemberUserDetailsServiceImpl) userDetailsService).loadUserByOpenId(openid);
        WechatAuthenticationToken result = new WechatAuthenticationToken(userDetails, new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
