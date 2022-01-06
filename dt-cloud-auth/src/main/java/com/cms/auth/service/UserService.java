package com.cms.auth.service;

import com.cms.auth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ydf Created by 2021/12/14 14:47
 */
//@Service
//public class UserService implements UserDetailsService {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String user = "admin";
//        if( !user.equals(username) ) {
//            throw new UsernameNotFoundException("用户不存在");
//        }
//        return new User(username, passwordEncoder.encode("123456"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
//    }
//}
