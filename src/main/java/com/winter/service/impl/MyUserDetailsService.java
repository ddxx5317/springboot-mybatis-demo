package com.winter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
@Slf4j
public class MyUserDetailsService implements UserDetailsService {


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
        // 假定创建有一个这样的权限组
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
        // 填入用户名 密码 以及权限组（权限组不能为null）
        return new User("admin", new BCryptPasswordEncoder().encode("123456"), auths);
    }
}
