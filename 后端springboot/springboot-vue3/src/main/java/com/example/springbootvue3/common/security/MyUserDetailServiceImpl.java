package com.example.springbootvue3.common.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootvue3.entity.User;
import com.example.springbootvue3.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author java1234_小锋 （公众号：java1234）
 * @site www.java1234.vip
 * @company 南通小锋网络科技有限公司
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        List<GrantedAuthority> list = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("admin");
        GrantedAuthority authority1 = new SimpleGrantedAuthority("user");
        list.add(authority);
        list.add(authority1);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword()
                , list);
    }
}
