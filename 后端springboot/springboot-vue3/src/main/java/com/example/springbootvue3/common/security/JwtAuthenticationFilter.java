package com.example.springbootvue3.common.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootvue3.entity.User;
import com.example.springbootvue3.service.impl.UserServiceImpl;
import com.example.springbootvue3.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader("token");
        String username = JwtUtils.getMemberIdByJwtToken(token);
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            PrintWriter out = response.getWriter();
            String msg ="{\"msg:login\",\"code:\"200,\"data:''\"}";
            out.print(msg);
        } else {
            chain.doFilter(request, response);
        }
    }
}
