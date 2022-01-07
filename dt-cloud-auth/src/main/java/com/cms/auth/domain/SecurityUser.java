package com.cms.auth.domain;

import com.cms.common.entity.SecurityClaimsUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author ydf Created by 2022/1/7 16:55
 */
public class SecurityUser extends SecurityClaimsUser implements UserDetails {

    private static final long serialVersionUID = -4179028978487613398L;

    public static SecurityUser from(SecurityClaimsUser user) {
        SecurityUser securityUser=new SecurityUser();
        securityUser.setUserid(user.getUserid());
        securityUser.setJti(user.getJti());
        securityUser.setUsername(user.getUsername());
        securityUser.setPassword(user.getPassword());
        securityUser.setScope(user.getScope());
        return securityUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
