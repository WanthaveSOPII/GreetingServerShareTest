package com.example.greetingserver.config;

import com.example.greetingserver.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * WebMvcSecurityConfig 页面访问安全配置
 *
 * @Author Yuan Jingshan
 * @Date 2018-05-29
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//


    @Bean
    UserDetailsService customUserService() {
        return new CustomUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**指明loginpage的位置*/
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and().formLogin().loginPage("/user/login").failureUrl("/user/login?error").permitAll().and()
//                .logout().permitAll();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")   //login页面
                .usernameParameter("userName")
                .passwordParameter("password")//告诉系统，login页面上的什么参数对应用户名和密码
                .successForwardUrl("/hello")    //暂且没有使用
                .defaultSuccessUrl("/user/postLogin")   //登录成功后，去向哪里
                .failureUrl("/user/login?error=true")   //登录失败后，去向哪里
                .permitAll()
                .and().exceptionHandling().accessDeniedPage("/accessdenied")
                .and().logout().permitAll()
                .and().csrf().disable();
    }


}