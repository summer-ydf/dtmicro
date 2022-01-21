package com.cms.auth.domain;

import com.cms.common.entity.SecurityClaimsUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author ydf Created by 2022/1/7 16:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecurityUser extends SecurityClaimsUser implements UserDetails {

    private static final long serialVersionUID = -4179028978487613398L;

    public static SecurityUser from(SecurityClaimsUser user) {
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUserid(user.getUserid());
        securityUser.setJti(user.getJti());
        securityUser.setUsername(user.getUsername());
        securityUser.setPassword(user.getPassword());
        securityUser.setScope(user.getScope());
        securityUser.setAccountNonExpired(user.isAccountNonExpired());
        securityUser.setAccountNonLocked(user.isAccountNonLocked());
        securityUser.setCredentialsNonExpired(user.isCredentialsNonExpired());
        securityUser.setEnabled(user.isEnabled());
        return securityUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;
}
