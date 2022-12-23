package com.example.springbootvue3.common.security;

import cn.hutool.json.JSONUtil;
import com.example.springbootvue3.entity.Result;

import com.example.springbootvue3.utils.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理
 * @author java1234_小锋 （公众号：java1234）
 * @site www.java1234.vip
 * @company 南通小锋网络科技有限公司
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        String username=authentication.getName(); // 获取用户名
       // System.out.println("获取的用户名是"+username);
         // 生成jwt token
        String token= JwtUtils.getJwtToken(username);
        outputStream.write(JSONUtil.toJsonStr(Result.success("登录成功",token)).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
