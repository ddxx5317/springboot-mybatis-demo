package com.winter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Value("${spring.security.user.name}")
    private String configuredUsername;

    @Value("${spring.security.user.password}")
    private String configuredPassword;

    /**
     * @param username - 前台传入的参数
     * @return
     * @throws UsernameNotFoundException
     * 返回一个实现UserDetails接口的User对象
     *
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("username:{}",username);
        if (!configuredUsername.equals(username)) {
            throw new UsernameNotFoundException("User not found");
        }

        // 假定创建有一个这样的权限组
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
        // 填入用户名 密码 以及权限组（权限组不能为null）
        return new User(configuredUsername, new BCryptPasswordEncoder().encode(configuredPassword), auths);
    }
}
