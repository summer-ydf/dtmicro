package com.cms.oauth.security.model.wechat;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author ydf Created by 2022/4/27 9:43
 */
public class WechatAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 550L;
    private final Object principal;
    @Getter
    private String encryptedData;
    @Getter
    private String iv;

    public WechatAuthenticationToken(Object principal, String encryptedData,String iv) {
        super(null);
        this.principal = principal;
        this.encryptedData = encryptedData;
        this.iv = iv;
        setAuthenticated(false);
    }

    public WechatAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
