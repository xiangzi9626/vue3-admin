package com.example.springbootvue3.config;

import com.example.springbootvue3.common.security.JwtLogoutSuccessHandler;
import com.example.springbootvue3.common.security.LoginFailureHandler;
import com.example.springbootvue3.common.security.LoginSuccessHandler;
import com.example.springbootvue3.common.security.MyUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security配置
 *
 * @author java1234_小锋 （公众号：java1234）
 * @site www.java1234.vip
 * @company 南通小锋网络科技有限公司
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    /*@Autowired
    private CaptchaFilter captchaFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;*/

    @Autowired
    private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    @Autowired
    MyUserDetailServiceImpl myUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter=new JwtAuthenticationFilter(authenticationManager());
        return jwtAuthenticationFilter;
    }*/

    // 请求白名单
    private static final String[] URL_WHITELIST = {
            "/admin/login",
            "/admin/**",
            "/upload/*/**",
            "/test/**",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 跨域 csrf攻击关闭
        http.cors().and().csrf().disable();
        // 登录配置
        /*http.formLogin().loginProcessingUrl("/admin/login").usernameParameter("username").passwordParameter("password")
                .successHandler(loginSuccessHandler).failureHandler(loginFailureHandler);*/
        //用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
        /* http.addFilterAt(myUsernamePasswordAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);*/
        //退出处理
        //http.logout().logoutSuccessHandler(jwtLogoutSuccessHandler);

        // 禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 配置拦截规则
        http.authorizeHttpRequests().anyRequest().permitAll();
        //http.authorizeRequests().antMatchers(URL_WHITELIST).permitAll().anyRequest().authenticated();
        // 异常处理器
        /*.and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)*/


        // 配置自定义过滤器
        //http.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
       /* .and()
                .addFilter(jwtAuthenticationFilter())
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)*/
    }
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(myUserDetailService);
    }*/
}
